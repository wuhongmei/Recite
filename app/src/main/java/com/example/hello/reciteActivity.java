package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class reciteActivity extends AppCompatActivity {

    TextView tex1, tex2;
    DBManager dbManager = new DBManager(reciteActivity.this);
    int i; //存储当前单词的ID
    WordItem item;

    //之后做按weight大小展示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);
        tex1 = findViewById(R.id.text_1);
        tex2 = findViewById(R.id.text_2);
        //获取背诵数据库加入控件
        i=1;
        item = dbManager.findById(1);
        tex1.setText(item.getWord());
    }

    //实现三个按钮功能
    //know
    public void know(View btn){
        item = dbManager.findById(i);
        tex2.setText(item.getMean());
        if(item.getWeight()>1){
            item.setWeight(item.getWeight()-1);
        }
        dbManager.update(item);
    }

    //unknow
    public void unknow(View btn){
        item = dbManager.findById(i);
        tex2.setText(item.getMean());
        item.setWeight(item.getWeight()+1);
        dbManager.update(item);
    }

    //next
    public void next(View btn){
        i++;
        try{
            item = dbManager.findById(i);
            tex1.setText(item.getWord());
            tex2.setText("");
        } catch (Exception e) {
            Toast.makeText(reciteActivity.this, "背完啦！", Toast.LENGTH_SHORT).show();
        }
    }

    //back
    public void back(View btn) {
        Intent MainPage = new Intent(this, MainActivity.class);
        startActivity(MainPage);
    }
}