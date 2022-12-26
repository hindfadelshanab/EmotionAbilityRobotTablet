package com.nuwa.robot.r2022.emotionalabilityrobot.networking;

import com.nuwa.robot.r2022.emotionalabilityrobot.listener.OnMessageReciveListener;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeoutException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

public class RobotSocketClient extends WebSocketClient  {

    public static final int CONNECTED = 1;
    public static final int DISCONNECTED = 2;
    public static final int RECONNECTING = 2;



    public static final String HANDSHAKE_MESSAGE = "HANDSHAKE_MESSAGE";
    private String serverUrl = "ws://localhost:8887";
    private String ID = "Robot";
    private  static int connectionState = 2;
    private int trialLimit = 5;
    private  long pingPeriod = 10000; // 10s
    private  boolean isConnectionOpened = false ;
    private  Thread connectionLiveThread ;
    private OnMessageReciveListener onMessageReciveListener ;


    int connectionTries = 0 ;
    int connectionTriesLimit = 3 ;

    public RobotSocketClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public RobotSocketClient(URI serverURI , OnMessageReciveListener onMessageReciveListener ) {
        super(serverURI);
        this.onMessageReciveListener = onMessageReciveListener;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Hello, it is me. Mario :)");
        System.out.println("new connection opened");

        connectionState = CONNECTED ;
        isConnectionOpened = true;
        send("Message");


    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        connectionState = DISCONNECTED ;
        if (connectionLiveThread != null){
            connectionLiveThread.interrupt();
        }
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
//        if (message.contains(":;:")){
//            String [] arr = message.split(":;:");
//            if (arr[0].equals(ID)){
//                // This is handshake message :)
//                connectionState = CONNECTED ;
//                connectionTries--;
//                System.out.println("CONNECTED");
//
//            }
//        }

        onMessageReciveListener.OnMessageRecive(message);
        System.out.println("received message server 88: " + message);
    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

    public int startConnection()  {
        for (int i = 0; i <= trialLimit; i++) {
            System.out.println("startConnection = " + connectionState );
            if (connectionState != CONNECTED) {

                        close();

//                    client = new EmptyClient(new URI(serverUrl));
                   connect();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                startConnectionLiveThread();
                System.out.println("CONNECTED __ __ __ ");
                return CONNECTED;
            }
        }
        if (connectionState == CONNECTED){
            startConnectionLiveThread();
            return CONNECTED ;
        }
        return DISCONNECTED ;
//        throw new TimeoutException();
    }



    private void startConnectionLiveThread(){
        connectionLiveThread =  new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                    send(ID + ":;:" + HANDSHAKE_MESSAGE);
                    connectionTries++;

                        Thread.sleep(pingPeriod);
                        System.out.println("connectionTries= " + connectionTries);
                        if (connectionTries >= connectionTriesLimit) {
                            connectionState = DISCONNECTED;
                            System.out.println("DISCONNECTED");
                        } else if (connectionState > 1 && connectionTries < connectionTriesLimit) {
                            connectionState = RECONNECTING;
                            System.out.println("RECONNECTING");
                        }
                    } catch (Exception e) {
                        close();
//                        e.printStackTrace();
                    }
                }
            }
        });
       connectionLiveThread.start();

    }



}
