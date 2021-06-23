package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void open(View btn) {
        Intent openPage;
        if (btn.getId() == R.id.button_1) {
            openPage = new Intent(this, addActivity.class);
            Log.i(TAG, "open: add" + "打开添加页面");
        }
        else if (btn.getId() == R.id.button_2) {
            openPage = new Intent(this, reciteActivity.class);
            Log.i(TAG, "open: recite" + "打开背诵页面");
        }
        else{
            openPage = new Intent(this, viewActivity.class);
            Log.i(TAG, "open: view" + "打开查看页面");
        }
        startActivity(openPage);
    }
}