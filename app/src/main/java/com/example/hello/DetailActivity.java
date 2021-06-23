package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Intent intent;
    String word,mean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView tex3 = findViewById(R.id.text_3);
        TextView tex4 = findViewById(R.id.text_4);
        //获取单词和释义
        intent = getIntent();
        word = intent.getStringExtra("word_key");
        mean = intent.getStringExtra("mean_key");

        tex3.setText(word);
        tex4.setText(mean);
    }
}