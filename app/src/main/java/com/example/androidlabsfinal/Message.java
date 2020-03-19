package com.example.androidlabsfinal;

public class Message {

    //instance variables
    String message= new String();
    boolean isSend;


    //constructor
    public Message(String t, boolean isSendType){
        this.message = t;
        this.isSend = isSendType;
    }

    //getters
    public String getMessage(){return this.message;}
    public boolean isSendType(){return this.isSend;}

    //setters
    public void setMessage(String t){this.message = t;}
    public void setSendType(boolean b){this.isSend = b;}

}
