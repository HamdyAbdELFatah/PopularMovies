package com.example.popularmovies;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.popularmovies.model.Data;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String ImageUrlBase="http://image.tmdb.org/t/p/w500/";
    RecyclerView recyclerView;
    MainActivityViewModel viewModel;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.rec);
        viewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.getData();
        LiveData();
    }
    void LiveData(){
        LiveData<List<Data>> listLiveData=viewModel.arr;
        listLiveData.observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                recyclerView.setAdapter(new MyAdapter(data));
            }
        });
        LiveData<Integer> pageLiveData=viewModel.page;
        pageLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                viewModel.getData();

            }
        });
        LiveData<String> sortLiveData=viewModel.sort;
        sortLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                viewModel.getData();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.next:
                viewModel.page.setValue(viewModel.page.getValue()+1);
                break;
            case R.id.rateItem:
                viewModel.sort.setValue("vote_average.desc");
                break;
            case R.id.popItem:
                viewModel.sort.setValue("popularity.desc");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{
        List<Data> arr;

        public MyAdapter(List<Data> arr) {
            this.arr = arr;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.movies,viewGroup,false));
        }
        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
            try {
                if (arr.get(i).getPoster_path()==null) {
        Picasso.get()
                .load("https://user-images.githubusercontent.com/24848110/33519396-7e56363c-d79d-11e7-969b-09782f5ccbab.png")
                .into(myHolder.img);
                } else
                    Picasso.get().load(ImageUrlBase + arr.get(i).getPoster_path()).into(myHolder.img);
            }catch(Exception e){
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            myHolder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this, MyMovie.class);
                    intent.putExtra("title",arr.get(i).getTitle());
                    intent.putExtra("disc",arr.get(i).getOverview());
                    if(arr.get(i).getPoster_path()!=null)
                    intent.putExtra("img",ImageUrlBase+arr.get(i).getPoster_path());
                    else
                    intent.putExtra("img","https://user-images.githubusercontent.com/24848110/33519396-7e56363c-d79d-11e7-969b-09782f5ccbab.png");
                    intent.putExtra("lang",arr.get(i).getOriginal_language());
                    intent.putExtra("rate",arr.get(i).getVote_average());
                    if(arr.get(i).getRelease_date().length()>=4)
                    intent.putExtra("date",arr.get(i).getRelease_date().substring(0,4));
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


}
