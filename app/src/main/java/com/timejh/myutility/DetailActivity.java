package com.timejh.myutility;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        Glide.with(this).load(intent.getStringExtra("imageUri")).placeholder(android.R.drawable.stat_notify_error).into(imageView);
    }
}
