package com.nuwa.robot.r2022.emotionalabilityrobot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amrdeveloper.lottiedialog.LottieDialog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.nuwa.robot.r2022.emotionalabilityrobot.networking.RobotServer;
import com.nuwa.robot.r2022.emotionalabilityrobot.networking.RobotSocketServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ShowIPAddressActivity extends AppCompatActivity {

    ImageView imageCode;
    TextView txtStatsConnection;
    RobotSocketServer server;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ipaddress);
        txtStatsConnection = findViewById(R.id.txtStatsConnection);
        txtStatsConnection.setText("");

//        String host = "192.168.88.105";
        String host = getIpAddress();
        Log.d("TAG1", "onCreate: "+host);
        int port = 8887;
        RobotSocketServer server = new RobotSocketServer(new InetSocketAddress(host, port));

        Log.d("TAG", "onCreate: ");
        new Thread(new Runnable() {
            @Override
            public void run() {

                server.setReuseAddr(true);
                server.run();


            }

        }).start();

        getIpAddress();
        if (server != null) {
            List<String> allKey = new ArrayList<>();
            RobotSocketServer.getClientMap().observe(ShowIPAddressActivity.this, new Observer<HashMap<String, Integer>>() {
                @Override
                public void onChanged(HashMap<String, Integer> stringIntegerHashMap) {
                    allKey.addAll(stringIntegerHashMap.keySet());
                    if (allKey.contains("Student")) {
                        txtStatsConnection.setText("scan the QR code using student app ");
                    } else if (allKey.contains("Teacher")) {
                        txtStatsConnection.setText("scan the QR code using teacher app");

                    }
                    if (allKey.contains("Student") && allKey.contains("Teacher")) {
                        showDialog();
                    }


                }
            });

        } else {
            Log.d("TAG", "onChanged:  null server");

        }


        imageCode = findViewById(R.id.imageCode);
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            BitMatrix mMatrix = mWriter.encode(getIpAddress(), BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix); //creating bitmap of code
            imageCode.setImageBitmap(mBitmap);//Setting generated QR code to imageView
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

    public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        Log.d("TAG", "getIpAddress:  " + ip);
        return ip;
    }

    private void showDialog() {

        LottieDialog dialog = new LottieDialog(this)
                .setAnimation(R.raw.successfully_connected)
                .setAnimationRepeatCount(LottieDialog.INFINITE)
                .setAutoPlayAnimation(true)
                .setMessageColor(Color.BLACK)
                .setMessage("Successfully connected")
                .setDialogBackground(Color.WHITE)
                .setCancelable(false)
                .setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                    public void run() {
                                Intent intent = new Intent(ShowIPAddressActivity.this, MainActivity.class);
                                        intent.putExtra("ip", getIpAddress());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                Log.d("TAG", "onChanged: allKey  Student and Teacher");
                                Toast.makeText(ShowIPAddressActivity.this, "Connect", Toast.LENGTH_SHORT).show();

                            }
                        }, 2000);
                    }
                });

        dialog.show();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 10) {
//            Log.d("TAG", "onActivityResult: "+requestCode);
//            moveTaskToBack(true);
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
//
//
//        }
//    }
}