package com.example.hello;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//操作数据库的类
public class DBManager {
    private final DBHelper dbHelper;
    private final String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void add(WordItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", item.getWord());
        values.put("mean", item.getMean());
        values.put("weight", 0);
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAll(List<WordItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (WordItem item : list) {
            ContentValues values = new ContentValues();
            values.put("word", item.getWord());
            values.put("mean", item.getMean());
            values.put("weight", 0);
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(WordItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", item.getWord());
        values.put("mean", item.getMean());
        values.put("weight", item.getWeight());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public List<WordItem> listAll(){
        List<WordItem> wordList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            wordList = new ArrayList<>();
            while(cursor.moveToNext()){
                WordItem item = new WordItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setWord(cursor.getString(cursor.getColumnIndex("WORD")));
                item.setMean(cursor.getString(cursor.getColumnIndex("MEAN")));
                item.setWeight(0);

                wordList.add(item);
            }
            cursor.close();
        }
        db.close();
        return wordList;
    }

    public WordItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        WordItem wordItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            wordItem = new WordItem();
            wordItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            wordItem.setWord(cursor.getString(cursor.getColumnIndex("WORD")));
            wordItem.setMean(cursor.getString(cursor.getColumnIndex("MEAN")));
            wordItem.setWeight(0);
            cursor.close();
        }
        db.close();
        return wordItem;
    }
}
