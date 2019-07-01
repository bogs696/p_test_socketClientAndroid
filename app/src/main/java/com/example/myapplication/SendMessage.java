package com.example.myapplication;

import android.util.Log;
import android.widget.EditText;

import java.io.IOException;

public class SendMessage implements Runnable {
    private static final String LOG_TAG = "SendMessage";
    ConnectServer connectServer;
    EditText name;
    EditText sendMessage;
    Thread thread;

    public SendMessage(ConnectServer connectServer, EditText name, EditText sendMessage) {
        this.connectServer = connectServer;
        this.name = name;
        this.sendMessage = sendMessage;
    }
    public void send(){
        if(connectServer!=null){
            thread = new Thread(this);
            thread.start();
        }
    }
    public void join(){
        try{
            if(thread!=null){
                thread.join();
            }
        } catch (InterruptedException e){
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    @Override
    public void run() {
        String text = name.getText().toString()+"::"+sendMessage.getText().toString();
        connectServer.sendData(text.getBytes());
    }
}
