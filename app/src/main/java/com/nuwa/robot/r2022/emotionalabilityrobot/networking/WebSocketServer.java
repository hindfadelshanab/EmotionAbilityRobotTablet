package com.nuwa.robot.r2022.emotionalabilityrobot.networking;

import android.util.Log;


import com.nuwa.robot.r2022.emotionalabilityrobot.listener.OnConnectedListener;
import com.nuwa.robot.r2022.emotionalabilityrobot.listener.OnMessageReciveListener;
import com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebSocketServer implements Runnable {

    ServerSocket serverSocket ;
    CommunicationThread commThread ;
    Socket socket;
    public String TAG =WebSocketServer.class.getSimpleName() ;

    private static volatile WebSocketServer INSTANCE = null;
   public   static  Thread thread ;
   OnConnectedListener onConnectedListener ;
   OnMessageReciveListener onMessageReciveListener ;

    private WebSocketServer() {

    }

    public static WebSocketServer getInstance() {
        if(INSTANCE == null ) {
            synchronized (WebSocketServer.class) {
                if (INSTANCE == null ) {
                    INSTANCE = new WebSocketServer();
                    if (thread == null){
                        new Thread(INSTANCE).start();
                    }else {
                        if (thread.isInterrupted()){
                            thread.start();
                        }else {
                            return INSTANCE ;
                        }
                    }
                }



            }
        }
        return INSTANCE;
    }
    public void init(OnConnectedListener connectedListener , OnMessageReciveListener onMessageReciveListener){
        this.onConnectedListener = connectedListener;
        this.onMessageReciveListener = onMessageReciveListener;
    }



    public void run() {

            try {
                serverSocket = new ServerSocket(Constants.SERVER_PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (null != serverSocket) {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Log.d(TAG, "run: currentThread");
                        socket = serverSocket.accept();
                           commThread = new CommunicationThread(socket, new OnConnectedListener() {
                               @Override
                               public void onConnect(boolean isConnect) {
                                   onConnectedListener.onConnect(isConnect);
                               }
                           }, new OnMessageReciveListener() {
                               @Override
                               public void OnMessageRecive(String Message) {
                                   if(Message != null)
                                   onMessageReciveListener.OnMessageRecive(Message);
                               }
                           });
                        new Thread(commThread).start();
                  } catch (IOException e) {
                        e.printStackTrace();
//                        showMessage("Error Communicating to Client :" + e.getMessage(), Color.RED);
                    }
                }
            }
        }

    public void sendMessageJson(String key , String value){
        Log.d(TAG, "sendMessageJson: ");
        commThread.sendMessageJson(key , value);
    }
    public void sendMessage(String value){
        Log.d(TAG, "sendMessageJson: ");
        commThread.sendMessage(  value);
    }


    }






