package com.example.hello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class addActivity extends AppCompatActivity implements Runnable {

    private static final String TAG = "addActivity";
    Handler handler;
    StringBuffer exp;
    EditText wordEdit = findViewById(R.id.edit_E);
    EditText expEdit = findViewById(R.id.edit_C);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    // 点击ok时开始爬取数据
    public void okToRun(View btn) {
        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                // 收到消息后的处理
                Log.i(TAG, "handleMessage: 收到消息" + msg.what);
                if (msg.what == 1) {  // 确认对象
                    String exps = (String) msg.obj;
                    expEdit.setText(exps);
                }
                super.handleMessage(msg);
            }
        };
        Thread t = new Thread(this);
        t.start();
    }

    //将用户添加的单词加入数据库
    public void addWord(View btn) {
        String word = wordEdit.getText().toString();
        String mean = expEdit.getText().toString();
        WordItem item = new WordItem(word, mean);
//        DBManager dbManager = new DBManager(addActivity.this);
//        dbManager.add(item);
    }

    @Override
    public void run() {
        try {
            String word = wordEdit.getText().toString();
            String preUrl = "http://www.baidu.com/s?wd=";
            String url = preUrl + word;
            Document doc = Jsoup.connect(url).get();
            Log.i(TAG, "run:" + doc.title());
            Elements spans = doc.select("span[class=op_dict_text2]"); //获取解释
            for (Element span : spans) {
                String str = span.text();
                exp.append(str);
                exp.append("\n");
            }
            Log.i(TAG, "run:" + exp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(1, exp);
        handler.sendMessage(msg);
    }
}

//            String url = "https://fanyi.baidu.com/translate?aldtype=16047&query=&keyfrom=baidu&smartresult=dict&lang=auto2zh#en/zh/" + "word";
//            Thread.sleep(3000);
//            Document doc = Jsoup.connect(url).get();
//            Log.i(TAG, "run:" + doc.title());
//            Elements strongs = doc.getElementsByTag("strong"); //获取解释
//
//            for(Element strong: strongs.nextAll()) {
//                Elements spans = strong.getElementsByTag("span");
//                for(Element span: spans){
//                    String str = span.text();    //解释
//                    exp += str;
//                }
//                exp += "\n";
//                        Log.i(TAG, "run:" + exp);
//            }
