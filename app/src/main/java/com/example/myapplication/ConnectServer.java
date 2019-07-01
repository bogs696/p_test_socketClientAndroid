package com.example.myapplication;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class ConnectServer {
    private static final String LOG_TAG = "myConnectServerApp";
    private String serverIP = "192.168.0.103";
    private int serverPort = 8080;
    private Socket socket;

    public ConnectServer() {
    }
    public void openConnection() {
        try{
            closeConnection();
        }catch (IOException e1){
            Log.e(LOG_TAG, e1.getMessage());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    socket = new Socket(serverIP, serverPort);
                } catch (IOException e){
                    Log.e(LOG_TAG, e.getMessage());
                    try{
                        closeConnection();
                    }catch (IOException e1){
                        Log.e(LOG_TAG, e1.getMessage());
                    }
                }

            }
        }).start();


    }
    public void closeConnection()throws IOException{
        if(socket!=null && !socket.isClosed()){
            socket.close();
        }
        socket = null;
    }
    public void sendData(byte[] data) {
        if(socket==null && socket.isClosed()){
            Log.e(LOG_TAG, "Socket not creating or close!");
        } else{
            try{
                socket.getOutputStream().write(data);
                socket.getOutputStream().flush();
            } catch (IOException e){
                Log.e(LOG_TAG, e.getMessage());
            }
        }
    }
    public String receiveMessage(){
        byte[] buffer = new byte[1024*4];
        if(socket!=null && !socket.isClosed()){

            try{
                int count = socket.getInputStream().read(buffer, 0, buffer.length);
                if(count==-1) {
                    Log.e(LOG_TAG,"Connect close");
                    finalize();
                    return  null;
                }
                return new String(buffer, 0, count);

            }catch (IOException e){
                Log.e(LOG_TAG, e.getMessage());
                return null;
            }
        } else{

            Log.e(LOG_TAG, "Socket not creating or close!");
            return null;
        }
    }
    protected void finalize() {
        try{
            super.finalize();
            closeConnection();
        } catch (Throwable e){
            Log.e(LOG_TAG, e.getMessage());
        }

    }
}
