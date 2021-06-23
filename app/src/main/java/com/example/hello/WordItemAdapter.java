package com.example.hello;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class WordItemAdapter extends ArrayAdapter {
    public WordItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<WordItem> list) {
        super(context, resource, list);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        WordItem wordItem = (WordItem) getItem(position);
        TextView word = itemView.findViewById(R.id.itemWord);
        TextView mean = itemView.findViewById(R.id.itemMean);
        word.setText(wordItem.getWord());
        mean.setText(wordItem.getMean());
        return itemView;
    }
}


