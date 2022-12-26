package com.nuwa.robot.r2022.emotionalabilityrobot.utils;

import com.nuwa.robot.r2022.emotionalabilityrobot.model.ClientInfo;

import java.util.HashMap;
import java.util.List;

public class ConnectionController<T> {


    ConnectionStatus connectionStatus ;

    List<ClientInfo > liveClient ;

    public  ConnectionStatus connection(){


        return this.connectionStatus =ConnectionStatus.CONNECTION;

    }
    public  void reConnection(){

    }
    public  void disConnection(){

    }

    public  ConnectionStatus  getStats(){

        return this.connectionStatus;
    }
    public enum ConnectionStatus {
        CONNECTION,
        DIS_CONNECTION,

    }
}
