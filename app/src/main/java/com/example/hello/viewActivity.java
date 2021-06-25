package com.example.hello;
//展示列表页面
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.Collections;
import java.util.List;

public class viewActivity extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String TAG = "viewActivity";
    Handler handler;
    ListView listView1;
    WordItemAdapter adapter;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

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
                    ArrayList<WordItem> wrdList = (ArrayList<WordItem>)msg.obj;
                    wrdList = Sort(wrdList);
                    adapter = new WordItemAdapter(viewActivity.this, R.layout.list_item, wrdList);
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
        Log.i(TAG, "run: 从数据库中获取数据");
        dbManager = new DBManager(viewActivity.this);
        List<WordItem> wrd = new ArrayList<>(dbManager.listAll());

        // 将消息返回给主线程
        Message msg = handler.obtainMessage(2,wrd);
        handler.sendMessage(msg);
    }

    // 快速排序
    public static ArrayList<WordItem> Sort(ArrayList<WordItem> wordList) {
        List<String> list = new ArrayList<>();
        ArrayList<WordItem> sortedList = new ArrayList<>();
        for(WordItem wordItem: wordList){
            list.add(wordItem.getWord());
        }
        Collections.sort(list);
        for(String str: list){
            for (WordItem wordItem: wordList){
                if(wordItem.getWord().equals(str)){
                    sortedList.add(wordItem);
                }
            }
        }
        return sortedList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position=" + position);
        Object itemAtPosition = listView1.getItemAtPosition(position);

        WordItem wordItem = (WordItem)itemAtPosition;
        int idNum = wordItem.getId();
        String wordStr = wordItem.getWord();
        String meanStr = wordItem.getMean();
        int weight = wordItem.getWeight();
        Log.i(TAG, "onItemClick: idNum=" + idNum);
        Log.i(TAG, "onItemClick: wordStr=" + wordStr);
        Log.i(TAG, "onItemClick: meanStr=" + meanStr);
        Log.i(TAG, "onItemClick: weight=" + weight);

        //打开窗口，进行参数传递
        Intent intent = new Intent(this, DetailActivity.class);
        //参数传递
        intent.putExtra("id_key", idNum);
        intent.putExtra("word_key", wordStr);
        intent.putExtra("mean_key", meanStr);
        intent.putExtra("weight", weight);
//        startActivity(intent);
        startActivityForResult(intent,4); //打开一个可以返回数据的窗口
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //返回后更新数据
        if(requestCode==4 && resultCode==4){
            dbManager = new DBManager(viewActivity.this);
            ArrayList<WordItem> wrd = new ArrayList<>(dbManager.listAll());
            adapter = new WordItemAdapter(viewActivity.this, R.layout.list_item, wrd);
            listView1.setAdapter(adapter); // 重新设置ListView的数据适配器
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //长按操作
        Log.i(TAG, "onItemLongClick：长按操作");
        WordItem item = (WordItem) listView1.getItemAtPosition(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("请确认是否删除该单词？")
                .setPositiveButton("是", (dialog, which) -> {
                    Log.i(TAG, "onClick：对话框事件处理");
                    adapter.remove(item);
                    dbManager.delete(item.getId());
                }).setNegativeButton("否", null);
        builder.create().show();
        return true; //不触发点击操作
    }

    public void deleteAll(View btn) {
        //删除全部单词
        Log.i(TAG, "deleteAll：全部删除操作");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("请确认是否删除所有单词？")
                .setPositiveButton("是", (dialog, which) -> {
                    Log.i(TAG, "onClick：对话框事件处理");
                    adapter.clear();
                    dbManager.deleteAll();
                }).setNegativeButton("否", null);
        builder.create().show();
    }

    public void back(View btn) {
        Intent MainPage = new Intent(this, MainActivity.class);
        startActivity(MainPage);
    }
}