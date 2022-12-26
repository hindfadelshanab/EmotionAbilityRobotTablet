package com.nuwa.robot.r2022.emotionalabilityrobot.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class RobotSocketServer extends WebSocketServer {
    public static final int CONNECTED = 1;
    public static final int DISCONNECTED = 2;
    public static final int RECONNECTING = 2;

    public static  HashMap<String, Integer> clientMap = new HashMap<>();
     public static MutableLiveData<HashMap<String,Integer>> clientMapLiveData = new MutableLiveData<>();

    public RobotSocketServer(InetSocketAddress address) {
        super(address);
    }




    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!"); //This method sends a message to the new client
        broadcast("new connection: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
//        conn.getLocalSocketAddress();
//        String ip = String.valueOf(conn.getRemoteSocketAddress().getAddress());
//        ip.replace("/" ,"");
//        Log.d("hind", "onOpen1: "+conn.getRemoteSocketAddress().getAddress().getHostAddress());
//        Log.d("hind", "onOpen2: "+ ip.replace("/" ,""));
//
//        try {
//            sendPingRequest( ip.replace("/" ,""));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.d("ping", "onOpen:conn.getLocalSocketAddress()  "+conn.getLocalSocketAddress());
//        Log.d("ping", "onOpen:conn.getLocalSocketAddress()  "+conn.getRemoteSocketAddress().getAddress());
//        try{
//            Log.d("TAG", "onActivityResult: ");
//
//
//
//            InetAddress address = InetAddress.getByName(ip.replace("/" ,""));
//            boolean reachable = address.isReachable(10000);
//            Log.d("ping", "onActivityResult: isReachable " +address.isReachable(5000) );
//            Log.d("ping", "onActivityResult: " +reachable);
//            System.out.println("Is host reachable? " + reachable);
//        } catch (Exception e){
//            e.printStackTrace();
//            Log.d("ping", "onActivityResult: error " +e.getMessage());
//
//        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        broadcast(conn + " has left the room!" );

        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
        Log.d("RobotSocketServer", "closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if (message.contains(":;:")) {
            String[] arr = message.split(":;:");
            clientMap.put(arr[0], CONNECTED);
            clientMapLiveData.postValue(clientMap);

            broadcast(message);
        }

        broadcast(message);

    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        broadcast(message.array());
        System.out.println(conn + ": " + message);

        System.out.println("received ByteBuffer from " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            System.err.println("an error occurred on connection " + conn.getRemoteSocketAddress() + ":" + ex);

            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {
//        setConnectionLostTimeout(300);
        System.out.println("server started successfully");
    }

    public static MutableLiveData<HashMap<String,Integer>> getClientMap(){
        return  clientMapLiveData ;

    }
    public static void clearClientMap(){

        clientMap.clear();
          clientMapLiveData.postValue(clientMap);
        Log.d("TAG", "clearClientMap:clientMap "+clientMap.toString());
    }
//    public static void sendPingRequest(String ipAddress)
//            throws UnknownHostException, IOException
//    {
//        InetAddress geek = InetAddress.getByName(ipAddress);
//        System.out.println("Sending Ping Request to " + ipAddress);
//        if (geek.isReachable(5000))
//            System.out.println("Host is reachable");
//        else
//            System.out.println("Sorry ! We can't reach to this host");
//    }
}
