package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    Intent intent;
    String word,mean,newMean;
    int idNum, weight;
    TextView tex3;
    EditText editText;
    WordItem item;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tex3 = findViewById(R.id.text_3);
        editText = findViewById(R.id.edit_mod);
        //获取单词和释义
        intent = getIntent();
        idNum = intent.getIntExtra("id_key", 0);
        word = intent.getStringExtra("word_key");
        mean = intent.getStringExtra("mean_key");
        weight = intent.getIntExtra("weight", 0);
        tex3.setText(word);
        editText.setText(mean);
        dbManager = new DBManager(DetailActivity.this);
    }


    public void modify(View view) {
        Log.i(TAG, "modify: ");
        editText = findViewById(R.id.edit_mod);
        newMean = editText.getText().toString();
        Log.i(TAG, "modify: id=" + idNum);
        Log.i(TAG, "modify: word=" + word);
        Log.i(TAG, "modify: mean=" + newMean);

        // 更新数据库数据
        item = new WordItem(idNum, word, newMean, weight);//更新不改变权重
        if(dbManager.update(item)) {
            Toast.makeText(DetailActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(DetailActivity.this, "保存失败！", Toast.LENGTH_SHORT).show();

        //返回数据到调用页面
        Intent intent = getIntent();
        Bundle bdl = new Bundle();
        intent.putExtras(bdl);
        setResult(4,intent);
        //关闭窗口
        finish();
    }
}