package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class reciteActivity extends AppCompatActivity {

    TextView tex1, tex2;
    DBManager dbManager;
    WordItem item;
    List<WordItem> wordList;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);

        dbManager = new DBManager(reciteActivity.this);
        wordList = dbManager.listAll();
        // 按weight排序
        Quicksort(wordList, 0, wordList.size() - 1);
        tex1 = findViewById(R.id.text_1);
        tex2 = findViewById(R.id.text_2);
        // 获取背诵数据库加入控件
        index = 0;
        try{
            item = wordList.get(index);
            tex1.setText(item.getWord());
        } catch (Exception ex){
            tex2.setText("还没有添加单词哦！");
            //禁用button
            findViewById(R.id.button_4).setEnabled(false);
            findViewById(R.id.button_5).setEnabled(false);
            findViewById(R.id.button_6).setEnabled(false);
        }
    }

    // 快速排序
    public static void Quicksort(List<WordItem> wordList, int p, int r) {
        int i = p;
        int j = r;
        if(i < j){
            WordItem stan;         // 基准单词
            int k = wordList.get(i).getWeight();  // 以第一个单词的weight作为基准
            while (i < j) {
                while (i < j && wordList.get(j).getWeight() < k) { // 从右往左找出大的数
                    j--;
                }
                while (i < j && wordList.get(i).getWeight() >= k) { //从左往右找出小的数
                    i++;
                }
                if (i >= j) break;

                // 交换
                WordItem wordItem = wordList.get(i);
                wordList.set(i, wordList.get(j));
                wordList.set(j, wordItem);
            }
            // 交换stan
            stan = wordList.get(i);
            wordList.set(i, wordList.get(p));
            wordList.set(p, stan);

            //对左边排序
            Quicksort(wordList, p, i-1);
            //对右边排序
            Quicksort(wordList, i+1, r);
        }
    }

    // 实现三个按钮功能
    // know
    public void know(View btn){
        item = wordList.get(index);
        tex2.setText(item.getMean());
        if(item.getWeight()>1){
            item.setWeight(item.getWeight()-1);
        }
        dbManager.update(item);
    }

    // unknown
    public void unknown(View btn){
        item = wordList.get(index);
        tex2.setText(item.getMean());
        item.setWeight(item.getWeight()+1);
        dbManager.update(item);
    }

    // next
    public void next(View btn){
        index++;
        try{
            item = wordList.get(index);
            tex1.setText(item.getWord());
            tex2.setText("");
        } catch (Exception e) {
            Toast.makeText(reciteActivity.this, "背完啦！", Toast.LENGTH_SHORT).show();
        }
    }

    // back
    public void back(View btn) {
        Intent MainPage = new Intent(this, MainActivity.class);
        startActivity(MainPage);
    }
}
