package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class MyMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__movie);
        TextView date=findViewById(R.id.date);
        TextView disc=findViewById(R.id.disc);
        TextView title=findViewById(R.id.title);
        ImageView img=findViewById(R.id.imageView);
        Intent intent=getIntent();
        date.setText(intent.getExtras().getString("date"));
        date.append("\n"+intent.getExtras().getString("rate")+"/10");
        date.append("\nLanguage: "+intent.getExtras().getString("lang"));
        disc.setText(intent.getExtras().getString("disc"));
        title.setText(intent.getExtras().getString("title"));
        Picasso.get().load(intent.getExtras().getString("img")).into(img);
    }
}
