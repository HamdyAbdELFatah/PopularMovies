package com.example.popularmovies;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {
String ImageUrlBase="http://image.tmdb.org/t/p/w500/";
String key="Enter Your key";
TextView text;
RecyclerView recyclerView;
ArrayList<data> arr;
int page=1;
String sort=null;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.rec);
        try{
        page=getIntent().getExtras().getInt("page");
        sort=getIntent().getExtras().getString("sort");
        }
        catch(Exception e){
            page=1;
            sort=null;
        }
        arr=new ArrayList<>();
        try {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/discover/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Connection connection=retrofit.create(Connection.class);
        Call<JsonModel> call=connection.getData(key,page,sort);
        call.enqueue(new Callback<JsonModel>() {
            @Override
            public void onResponse(Call<JsonModel> call, Response<JsonModel> response) {
                JsonModel jm = response.body();
                for(int i=0;i<jm.results.length;i++){
                    arr.add(new data(jm.results[i].vote_average
                            ,jm.results[i].title
                            ,jm.results[i].poster_path
                            ,jm.results[i].original_language
                            ,jm.results[i].overview,
                            jm.results[i].release_date));
                }
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                recyclerView.setAdapter(new MyAdapter());
            }
            @Override
            public void onFailure(Call<JsonModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        }catch (Exception e){
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i=new Intent(MainActivity.this,MainActivity.class);
        switch(item.getItemId()){
            case R.id.next:
                page++;
                break;
                case R.id.rateItem:
                sort="vote_average.desc";
                    break;
            case R.id.popItem:
                    sort="popularity.desc";
                break;
        }
        i.putExtra("page",page);
        i.putExtra("sort",sort);
        startActivity(i);
        finish();
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{
        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.movies,viewGroup,false));
        }
        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
            try {
                if (arr.get(i).poster_path==null) {
Picasso.get().load("https://user-images.githubusercontent.com/24848110/33519396-7e56363c-d79d-11e7-969b-09782f5ccbab.png")
        .into(myHolder.img);
                } else
                    Picasso.get().load(ImageUrlBase + arr.get(i).poster_path).into(myHolder.img);
            }catch(Exception e){
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            myHolder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this,My_Movie.class);
                    intent.putExtra("title",arr.get(i).title);
                    intent.putExtra("disc",arr.get(i).overview);
                    if(arr.get(i).poster_path!=null)
                    intent.putExtra("img",ImageUrlBase+arr.get(i).poster_path);
                    else
                    intent.putExtra("img","https://user-images.githubusercontent.com/24848110/33519396-7e56363c-d79d-11e7-969b-09782f5ccbab.png");
                    intent.putExtra("lang",arr.get(i).original_language);
                    intent.putExtra("rate",arr.get(i).vote_average);
                    if(arr.get(i).release_date.length()>=4)
                    intent.putExtra("date",arr.get(i).release_date.substring(0,4));
                    else
                    intent.putExtra("date","not found");
                    startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return arr.size();
        }
        class MyHolder extends RecyclerView.ViewHolder {
CardView card;
ImageView img;
            public MyHolder(@NonNull View itemView) {
                super(itemView);
                img=itemView.findViewById(R.id.image);
                card=itemView.findViewById(R.id.card);
            }
        }
    }

    class data{
        String vote_average;

        public data(String vote_average, String title, String poster_path, String original_language, String overview,String release_date) {
            this.vote_average = vote_average;
            this.title = title;
            this.poster_path = poster_path;
            this.original_language = original_language;
            this.overview = overview;
            this.release_date = release_date;
        }
        String title;
        String poster_path;
        String original_language;
        String overview;
        String release_date;
    }
}
