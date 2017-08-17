package com.example.chu.messager;

/**
 * Created by Chu on 2017/7/17.
 */

public class Msg {
    public static final int TYPE_RECIEVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private int type;
    public Msg(String content, int type){
        this.content = content;
        this.type = type;
    }
    public String getContent(){
        return content;
    }
    public int getType(){
        return type;
    }
}
