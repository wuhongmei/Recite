package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class reciteActivity extends AppCompatActivity {

    TextView tex1, tex2;
    DBManager dbManager = new DBManager(reciteActivity.this);
    int i; //当前单词的ID
    WordItem item = dbManager.findById(1);
    //之后做按weight大小展示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);
        tex1 = findViewById(R.id.text_1);
        tex2 = findViewById(R.id.text_2);
        //获取背诵数据库加入控件
        tex1.setText(item.getWord());
    }

    //实现三个按钮功能
    //know
    public void know(View btn){
        tex2.setText(item.getMean());
        if(item.getWeight()>1){
            item.setWeight(item.getWeight()-1);
        }
        dbManager.update(item);
    }

    //unknow
    public void unknow(View btn){
        tex2.setText(item.getMean());
        item.setWeight(item.getWeight()+1);
        dbManager.update(item);
    }

    public void next(View btn){
        i++;
        tex1.setText(item.getWord());
    }
}