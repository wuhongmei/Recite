package com.example.hello;
//展示列表页面
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class viewActivity extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String TAG = "viewActivity";
    Handler handler;
    ListView listView1;
    WordItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

//        SharedPreferences sp = getSharedPreferences("myword", Context.MODE_PRIVATE);
        listView1 = findViewById(R.id.myList1);
        ProgressBar progressBar1 = findViewById(R.id.progressBar1);
        listView1.setOnItemClickListener(this); //点击事件监听
        listView1.setOnItemLongClickListener(this); //长按事件监听

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                // 收到消息后的处理
                Log.i(TAG, "handleMessage: 收到消息" + msg.what);
                if (msg.what == 2) {  // 确认对象
                    //自定义适配器、自定义布局
                    ArrayList<WordItem> retList = (ArrayList<WordItem>)msg.obj;
                    adapter = new WordItemAdapter(viewActivity.this, R.layout.list_item, retList);
                    listView1.setAdapter(adapter);
                    Toast.makeText(viewActivity.this, "Update Over", Toast.LENGTH_SHORT).show();
                    //处理显示与隐藏
                    progressBar1.setVisibility(View.GONE);
                    listView1.setVisibility(View.VISIBLE);
                }
                super.handleMessage(msg);
            }
        };

        // 创建并开启子线程
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        Log.i(TAG, "run: ...");
        List<WordItem> wrd = new ArrayList<>();
        Log.i(TAG, "run: 从数据库中获取数据");
        DBManager dbManager = new DBManager(viewActivity.this);
        wrd.addAll(dbManager.listAll());

        // 将消息返回给主线程
        Message msg = handler.obtainMessage(10,wrd);
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position=" + position);
        Object itemAtPosition = listView1.getItemAtPosition(position);

        WordItem rateItem = (WordItem)itemAtPosition;
        String wordStr = rateItem.getWord();
        String meanStr = rateItem.getMean();
        Log.i(TAG, "onItemClick: titleStr=" + wordStr);
        Log.i(TAG, "onItemClick: detailStr=" + meanStr);

        //打开窗口，进行参数传递
        Intent detail = new Intent(this, DetailActivity.class);
        //参数传递
        detail.putExtra("word_key", wordStr);
        detail.putExtra("mean_key", meanStr);
        startActivity(detail);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //长按操作
        Log.i(TAG, "onItemLongClick：长按操作");
        WordItem wordItem = (WordItem) listView1.getItemAtPosition(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("请确认是否删除当前数据")
                .setPositiveButton("是", (dialog, which) -> {
                    Log.i(TAG, "onClick：对话框事件处理");
                    adapter.remove(wordItem);
                }).setNegativeButton("否", null);
        builder.create().show();
        return true; //不触发点击操作
    }
}