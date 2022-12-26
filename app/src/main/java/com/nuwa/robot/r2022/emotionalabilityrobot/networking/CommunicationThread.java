package com.nuwa.robot.r2022.emotionalabilityrobot.networking;

import android.util.Log;


import com.nuwa.robot.r2022.emotionalabilityrobot.listener.OnConnectedListener;
import com.nuwa.robot.r2022.emotionalabilityrobot.listener.OnMessageReciveListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationThread implements Runnable {

    private final Socket clientSocket;
    private final Socket tempClientSocket;
    OnConnectedListener onConnectedListener ;
    OnMessageReciveListener onMessageReciveListener;

    private BufferedReader input;

    public CommunicationThread(Socket clientSocket  , OnConnectedListener onConnectedListener , OnMessageReciveListener onMessageReciveListener) {
        this.clientSocket = clientSocket;
        tempClientSocket = clientSocket;
        this.onConnectedListener = onConnectedListener;
        this.onMessageReciveListener = onMessageReciveListener;
        try {
            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
//                showMessage("Error Connecting to Client!!", Color.RED);
            return;
        }
        onConnectedListener.onConnect(true);

        Log.d("TAG", "CommunicationThread: " + "Connected to Client!!");

    }

    public void run() {

        while (!Thread.currentThread().isInterrupted()) try {
            String read = input.readLine();
            Log.d("TAG", "CommunicationThread:= " + read );

//            if (read!=null){
//                onMessageReciveListener.OnMessageRecive(read);
//            }
            Log.d("TAG", "Client Thread Robot message : "+read);



            if (null == read || "Disconnect".contentEquals(read)) {

                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    void sendMessageJson(String key ,final String message){
        new Thread(() -> {
            try {
                if (null != tempClientSocket) {
                    JSONObject json = new JSONObject();
                    json.put(key, message);
                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(tempClientSocket.getOutputStream())),
                            true);
                    out.println(json.toString());


                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TAG e", "sendMessage: " +e.getMessage());

            }
        }).start();


    }
         public void sendMessage(final String message) {
        Log.d("TAG CommunicationThread", "sendMessage: ");
        try {
            if (null != tempClientSocket) {
                new Thread(() -> {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(tempClientSocket.getOutputStream())),
                                true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println(message);
                }).start();
            }
        } catch (Exception e) {
            Log.d("TAG e", "sendMessage: " +e.getMessage());
            e.printStackTrace();
        }
    }

}
