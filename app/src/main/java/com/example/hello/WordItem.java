package com.example.hello;

//用于映射表中数据
public class WordItem {
    private int id;
    private String word;  //单词
    private String mean; //解释
    private int weight; //unknow的次数

    public WordItem() {
        super();
        word = "";
        mean = "";
        weight = 0;
    }
    public WordItem(String word, String mean) { // 添加时使用
        super();
        this.word = word;
        this.mean = mean;
        this.weight = 0;
    }
    public WordItem(int id, String word, String mean, int weight) {  // 修改时使用
        super();
        this.id = id;
        this.word = word;
        this.mean = mean;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
