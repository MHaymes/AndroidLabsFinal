package com.example.androidlabsfinal;

public class Message {

    //instance variables
    String message= new String();
    String sendType;  //could be an enum.  Should only be one of "SEND" OR "RECEIVE"
    long id;


    //constructor
    public Message(String t, String sendType, long id){
        this.message = t;
        this.sendType = sendType;
        this.id = id;
    }

    //getters
    public String getMessage(){return this.message;}
    public String getSendType(){return this.sendType;}
    public long getId(){return this.id;}

    //setters
    public void setMessage(String t){this.message = t;}
    public void setSendType(String s){this.sendType = s;}
    public void setID(long id){this.id = id;}

}
