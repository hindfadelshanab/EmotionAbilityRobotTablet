package com.nuwa.robot.r2022.emotionalabilityrobot.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;

public class RobotServer extends WebSocketServer {
    HashMap<String, Integer> clientMap = new HashMap<>();
    public static final int CONNECTED = 1;

    public RobotServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }


    public RobotServer(InetSocketAddress address) {
        super(address);
    }

    public RobotServer(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!"); //This method sends a message to the new client
        conn.send("{\"firstName\": \"John\"}" /* stick your JSON here */);

        broadcast("new connection: " + handshake
                .getResourceDescriptor()); //This method sends a message to all clients connected
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        broadcast(conn + " has left the room!");
        System.out.println(conn + " has left the room!");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        broadcast(message);

        if (message.contains(":;:")) {
            String[] arr = message.split(":;:");
            clientMap.put(arr[0], CONNECTED);
            broadcast(message);
        }
        System.out.println(conn + ": " + message);
        Log.d("TAG", "Server onMessage : conn" + conn + "message :" + message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        broadcast(message.array());
        System.out.println(conn + ": " + message);
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
//        setConnectionLostTimeout(20);
        setConnectionLostTimeout(100);
    }


}
