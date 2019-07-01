package com.example.myapplication;

import android.widget.EditText;

import java.net.Socket;

public class ReceiveMessage implements Runnable {
    private ConnectServer connectServer;
    private String text;
    private String name;
    private String reg = "::";
    private MainActivity.UpdateUI updateUI;
    public ReceiveMessage(ConnectServer connectServer, MainActivity.UpdateUI updateUI) {
        this.connectServer = connectServer;
        this.updateUI = updateUI;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true){
            text = connectServer.receiveMessage();
            if(text == null || text.equals("")) {
                continue;
            }
            name = text.split(reg)[0];
            text = text.substring(name.length() + reg.length());
            updateUI.update(name,text);
        }

    }
}
