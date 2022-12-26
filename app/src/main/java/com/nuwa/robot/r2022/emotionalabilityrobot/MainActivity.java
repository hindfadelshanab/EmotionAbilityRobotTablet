package com.nuwa.robot.r2022.emotionalabilityrobot;

import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.ARABIC;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.ENGLISH;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LET_IS_BEGIN_ARABIC;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LET_IS_TRY_AGAIN;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL1_NAME;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL1_RESPONSE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL1_TITLE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL2_RESPONSE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL2_TITLE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL3_RESPONSE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL3_TITLE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL4_RESPONSE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL4_TITLE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL5_PHASE1_RESPONSE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL6_PHASE1_RESPONSE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL7_PHASE1_RESPONSE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL8_PHASE1_RESPONSE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL8_emotion_angry;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL8_emotion_confused;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL8_emotion_happy;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL8_emotion_proud;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL8_emotion_sad;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL8_emotion_scared;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL8_emotion_shy;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL8_emotion_surprised;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL8_emotion_tired;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.LEVEL8_emotion_worried;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.UNIT2_DESC;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.UNIT2_NAME;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.UNIT3_DESC;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.UNIT3_NAME;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.UNIT3_PHASE1_TITLE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.UNIT3_PHASE2_TITLE;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.UNIT4_DESC;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.UNIT4_NAME;
import static com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants.WELL_DONE_RESPONSE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalabilityrobot.listener.OnMessageReciveListener;
import com.nuwa.robot.r2022.emotionalabilityrobot.model.Message;
import com.nuwa.robot.r2022.emotionalabilityrobot.model.MessageExpression;
import com.nuwa.robot.r2022.emotionalabilityrobot.networking.RobotSocketClient;
import com.nuwa.robot.r2022.emotionalabilityrobot.networking.RobotSocketServer;
import com.nuwa.robot.r2022.emotionalabilityrobot.utils.Constants;
import com.nuwa.robot.r2022.emotionalabilityrobot.utils.PreferenceManager;
import com.nuwarobotics.service.IClientId;
import com.nuwarobotics.service.agent.NuwaRobotAPI;
import com.nuwarobotics.service.agent.RobotEventListener;
import com.nuwarobotics.service.agent.VoiceEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMessageReciveListener {

    private final String TAG = this.getClass().getSimpleName();
    NuwaRobotAPI mRobotAPI;
    IClientId mClientId;
    boolean mSDKinit = false;
    //    WebSocketServer webSocketServer;
    Context mContext;

    ImageView imageHappyFace;
    Button btnExit;
    //    Button button;
    VoiceEventListener.SpeakState mSpeakState = VoiceEventListener.SpeakState.NONE;
    RobotSocketClient client;
    RobotSocketServer server;
    VideoView videoView;
    Gson gson;
    private PreferenceManager preferenceManager;
    private static final long FACE_MOUTH_SPEED = 200;
    MediaPlayer mPlayer;
    public static int speakMessageId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mClientId = new IClientId(this.getPackageName());
        mRobotAPI = new NuwaRobotAPI(this, mClientId);

        videoView = findViewById(R.id.videoViewEmotion);
        imageHappyFace = findViewById(R.id.imageHappyFace);
        btnExit = findViewById(R.id.btnExit);
        gson = new Gson();
        mRobotAPI.getMotionList();
        Log.d("mRobotAPI", "onCreate:    mRobotAPI.getMotionList()" + mRobotAPI.getMotionList());

        preferenceManager = new PreferenceManager(this);
        mRobotAPI.registerRobotEventListener(robotEventListener);
        showRobotFace();
        exit();


        String ipp = getIntent().getStringExtra("ip");
        String urll = "ws://" + ipp + ":8887";
        try {
            client = new RobotSocketClient(new URI(urll), this);
            client.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        server = new RobotSocketServer(new InetSocketAddress(ipp, 8887));
        server.getPort();
        Log.d(TAG, "onCreate: server.getPort() " + server.getPort());


    }


    private void exit() {
        btnExit.setOnClickListener(view -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });
    }

    RobotEventListener robotEventListener = new RobotEventListener() {
        @Override
        public void onWikiServiceStart() {
            // Nuwa Robot SDK is ready now, you call call Nuwa SDK API now.
            Log.d(TAG, "onWikiServiceStart, robot ready to be control");

            mRobotAPI.registerVoiceEventListener(voiceEventListener);//listen callback of robot voice related event
            //Allow user start demo after service ready
            mRobotAPI.requestSensor(NuwaRobotAPI.SENSOR_DROP);
            mSDKinit = true;


        }

        @Override
        public void onWikiServiceStop() {

        }

        @Override
        public void onWikiServiceCrash() {

        }

        @Override
        public void onWikiServiceRecovery() {

        }

        @Override
        public void onStartOfMotionPlay(String s) {

        }

        @Override
        public void onPauseOfMotionPlay(String s) {

        }

        @Override
        public void onStopOfMotionPlay(String s) {

        }

        @Override
        public void onCompleteOfMotionPlay(String s) {

//            motion_complete = true;
            if (mRobotAPI != null) {
                mRobotAPI.hideWindow(true);
            }


        }

        @Override
        public void onPlayBackOfMotionPlay(String s) {

        }

        @Override
        public void onErrorOfMotionPlay(int i) {
            if (mRobotAPI != null) {
                mRobotAPI.hideWindow(true);
            }
//            motion_complete = true;
        }

        @Override
        public void onPrepareMotion(boolean b, String s, float v) {

        }

        @Override
        public void onCameraOfMotionPlay(String s) {

        }

        @Override
        public void onGetCameraPose(float v, float v1, float v2, float v3, float v4, float v5, float v6, float v7, float v8, float v9, float v10, float v11) {

        }

        @Override
        public void onTouchEvent(int i, int i1) {

        }

        @Override
        public void onPIREvent(int i) {

        }

        @Override
        public void onTap(int i) {

        }

        @Override
        public void onLongPress(int i) {

        }

        @Override
        public void onWindowSurfaceReady() {

        }

        @Override
        public void onWindowSurfaceDestroy() {

        }

        @Override
        public void onTouchEyes(int i, int i1) {

        }

        @Override
        public void onRawTouch(int i, int i1, int i2) {

        }

        @Override
        public void onFaceSpeaker(float v) {

        }

        @Override
        public void onActionEvent(int i, int i1) {

        }

        @Override
        public void onDropSensorEvent(int i) {

            if (i == 1) {
                Toast.makeText(mContext, "Drop in Robot ", Toast.LENGTH_SHORT).show();

            }
            int val = mRobotAPI.getDropSensorOfNumber();


        }

        @Override
        public void onMotorErrorEvent(int i, int i1) {

        }
    };
    VoiceEventListener voiceEventListener = new VoiceEventListener() {
        @Override
        public void onWakeup(boolean isError, String score, float direction) {

        }

        @Override
        public void onTTSComplete(boolean isError) {
            Log.d(TAG, "onTTSComplete:" + !isError);

        }

        @Override
        public void onSpeechRecognizeComplete(boolean isError, ResultType iFlyResult, String json) {

        }

        @Override
        public void onSpeech2TextComplete(boolean isError, String json) {

        }

        @Override
        public void onMixUnderstandComplete(boolean isError, ResultType resultType, String s) {

        }

        @Override
        public void onSpeechState(ListenType listenType, SpeechState speechState) {

        }

        @Override
        public void onSpeakState(SpeakType speakType, SpeakState speakState) {
            Log.d(TAG, "onSpeakState:" + speakType + ", state:" + speakState);
        }

        @Override
        public void onGrammarState(boolean isError, String s) {

        }

        @Override
        public void onListenVolumeChanged(ListenType listenType, int i) {

        }

        @Override
        public void onHotwordChange(HotwordState hotwordState, HotwordType hotwordType, String s) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void OnMessageRecive(String message) {

        try {
            Log.d("TAG", " new OnMessageRecive: message" + message);

            JSONObject jsonObject = new JSONObject(message);

            if (jsonObject.has("lang")) {
                preferenceManager.putString(Constants.LANGUAGE, jsonObject.getString("lang"));
            }

            if (jsonObject.has("close")) {
                handleCloseMessage(jsonObject);
            }
            if (jsonObject.has("message")) {
                JSONObject jsonObjectMessage = jsonObject.getJSONObject("message");
                Message message1 = gson.fromJson(jsonObjectMessage.toString(), Message.class);
                handleMessage(message1);
            }
            if (jsonObject.has("messageExpression")) {
                JSONObject jsonObjectMessage = jsonObject.getJSONObject("messageExpression");
                MessageExpression messageExpression = gson.fromJson(jsonObjectMessage.toString(), MessageExpression.class);
                if (messageExpression.getEmotionName() != null) {

//                    ttsSpeakAction(messageExpression.getMessageId(), messageExpression.getEmotionName());

                    if (preferenceManager.getString(Constants.LANGUAGE).equals(ENGLISH)) {
                        Log.d("TAG3", "OnMessageRecive: english " + preferenceManager.getString(Constants.LANGUAGE));
                        messageExpression.setResponse("you are " + messageExpression.getEmotionName());
                        sendMessageForTeacher(gson.toJson(messageExpression));
                    } else if (preferenceManager.getString(Constants.LANGUAGE).equals(ARABIC)) {
                        Log.d("TAG3", "OnMessageRecive: arabic " + preferenceManager.getString(Constants.LANGUAGE));

                        messageExpression.setResponse("أنت" + " " + messageExpression.getEmotionName());
                        sendMessageForTeacher(gson.toJson(messageExpression));
                    }
                }
            }


        } catch (Exception e) {
            Log.d(TAG, "OnMessageRecive: error" + e.getMessage());
        }

    }

    ArrayList<String> apps = new ArrayList();

    private void handleCloseMessage(JSONObject jsonObject) {
        Log.d("TAG2", "handleCloseMessage: jsonObject" + jsonObject);
        try {
            String ss = jsonObject.getString("close");
            apps.add(ss);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (apps.contains("Student") && apps.contains("Teacher")) {
            Intent intent = new Intent(this, ShowIPAddressActivity.class);
            startActivityForResult(intent, 10);
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);

            finish();
            ActivityCompat.finishAffinity(this);
            Log.d("TAG", "handleCloseMessage: " + RobotSocketServer.getClientMap().getValue());
            client.close();
        }

        Log.d("TAG2", "handleCloseMessage: apps  " + apps.toString());
    }


    private void handleMessage(Message message1) {

        switch (message1.getKey()) {
            case Constants.MESSAGE_FOR_SHOW_IMAGE_KEY:
                hideRobotFace();
                runOnUiThread(() -> {
                    imageHappyFace.setVisibility(View.VISIBLE);
                });
                Log.d("TAG2", "handleMessage: MESSAGE_FOR_SHOW_IMAGE_KEY" + message1.getMessageId());
                break;

            case Constants.MESSAGE_FOR_BODY_MOTION_KEY:
                handleBodyMotion(message1);
                break;
            case Constants.MESSAGE_FOR_FACE_EXPRESSION_KEY:
                handleFaceExpression(message1);
                Log.d("TAG2", "handleMessage: MESSAGE_FOR_FACE_EXPRESSION_KEY");
                break;
            case Constants.MESSAGE_FOR_KEBHI_SPECK:

                Log.d("TAG5", "handleMessage:MESSAGE_FOR_KEBHI_SPECK " + message1.getValue());
                Log.d("TAG5", "handleMessage:MESSAGE_FOR_KEBHI_SPECK id " + message1.getMessageId());
                runOnUiThread(() -> {
                    imageHappyFace.setVisibility(View.GONE);
                });

                String messageValue = message1.getValue();
                ttsSpeakAction(message1.getMessageId(), messageValue);

                handleAnsweredMessage(message1);

                break;


        }

    }

    private void handleArabicVoice(String messageValue) {
//        String messageValue = message1.getValue();


        switch (messageValue) {
            case LEVEL1_NAME:

                playAudio(R.raw.unit1_level1);
                break;
            case LEVEL1_TITLE:

                playAudio(R.raw.level1_title);


                break;
            case LET_IS_BEGIN_ARABIC:
                playAudio(R.raw.let_is_begin);

                break;

            case UNIT2_NAME:
                playAudio(R.raw.level2_name);

                break;
            case UNIT2_DESC:

                playAudio(R.raw.unit2_level_desc);


                break;

            case LEVEL2_TITLE:

                playAudio(R.raw.level2_title);

                break;

            case LEVEL1_RESPONSE:
                playAudio(R.raw.awesome);

                break;
            case LEVEL2_RESPONSE:
                playAudio(R.raw.level2_response);
                break;

            case LEVEL3_TITLE:
                playAudio(R.raw.level3_title);
                break;
            case LEVEL3_RESPONSE:
                playAudio(R.raw.level3_response);

                break;

            case LEVEL4_TITLE:
                playAudio(R.raw.level4_title);

                break;
            case LEVEL4_RESPONSE:
                playAudio(R.raw.level4_response);
                break;

            case UNIT3_NAME:
                playAudio(R.raw.unit3_level_name);

                break;
            case UNIT3_DESC:
                playAudio(R.raw.unit3_desc);
                break;
            case UNIT3_PHASE1_TITLE:
                playAudio(R.raw.unit3_phase1_title);
                break;
            case LEVEL5_PHASE1_RESPONSE:
                playAudio(R.raw.level5_phase1_response);
                break;
            case UNIT3_PHASE2_TITLE:
                playAudio(R.raw.unit3_phase2_title);
                break;
            case LEVEL6_PHASE1_RESPONSE:
                playAudio(R.raw.level3_response);

                break;
            case UNIT4_NAME:
                playAudio(R.raw.unit4_name);
                break;
            case UNIT4_DESC:
                playAudio(R.raw.unit3_desc);
                break;
            case LEVEL7_PHASE1_RESPONSE:
                playAudio(R.raw.level7_phase1);
                break;
            case LEVEL8_PHASE1_RESPONSE:
                playAudio(R.raw.awesome);
                break;
            case LEVEL8_emotion_sad:
                playAudio(R.raw.you_are_sad);
                break;
            case LEVEL8_emotion_happy:
                playAudio(R.raw.you_are_happy);
                break;
            case LEVEL8_emotion_scared:
                playAudio(R.raw.you_are_scared);
                break;
            case LEVEL8_emotion_angry:
                playAudio(R.raw.you_are_angry);
                break;
            case LEVEL8_emotion_shy:
                playAudio(R.raw.you_are_shy);
                break;
            case LEVEL8_emotion_proud:
                playAudio(R.raw.you_are_proud);
                break;
            case LEVEL8_emotion_confused:
                playAudio(R.raw.you_are_confused);
                break;
            case LEVEL8_emotion_worried:
                playAudio(R.raw.you_are_worried);
                break;
            case LEVEL8_emotion_surprised:
                playAudio(R.raw.you_are_surprised);
                break;
            case LEVEL8_emotion_tired:
                playAudio(R.raw.you_are_tired);
                break;
            case LET_IS_TRY_AGAIN:
                playAudio(R.raw.try_agin);
                break;
            case WELL_DONE_RESPONSE:
                playAudio(R.raw.well_done);
        }


    }

    private void playAudio(int audioId) {
        if (mPlayer == null) {
            mPlayer = MediaPlayer.create(getApplicationContext(), audioId);

        } else {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
            mPlayer = MediaPlayer.create(getApplicationContext(), audioId);
        }
        mPlayer.start();
    }

    private void handleAnsweredMessage(Message message1) {
//        666_RE_Ask
        if (message1.getMessageId() == 8) {
            if (message1.getValue().equals(LET_IS_TRY_AGAIN)) {
                Log.d("TAG3", "handleAnsweredMessage: LET_IS_TRY_AGAIN" + message1.getValue());
                try {
                    if (mSDKinit) {
                        showRobotFace();
                        mRobotAPI.motionPlay("666_RE_Baoquan", true);
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                Log.d("TAG3", "handleAnsweredMessage correct: " + message1.getValue());

                MediaPlayer.create(getApplicationContext(), R.raw.clap).start();
                try {
                    if (mSDKinit) {
                        showRobotFace();
                        mRobotAPI.motionPlay("666_WO_HitKeyboard", true);


                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (message1.getMessageId() == 11) {
            try {
                if (mSDKinit) {
                    showRobotFace();
                    mRobotAPI.motionPlay("666_WO_HitKeyboard", true);


                }
            } catch (Exception e) {
                Toast.makeText(mContext, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        //666_SP_Chest
        if (message1.getMessageId() == 15) {
//            MediaPlayer.create(getApplicationContext(), R.raw.clap).start();
            try {
                if (mSDKinit) {
                    showRobotFace();
                    mRobotAPI.motionPlay("666_SP_Chest", true);


                }
            } catch (Exception e) {
                Toast.makeText(mContext, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void handleBodyMotion(Message message1) {
        Log.d("TAG2", "handleMessage: MESSAGE_FOR_BODY_MOTION_KEY" + message1.getMessageId());
        String messageValueBody = message1.getValue();
        Log.d("TAG3", "handleBodyMotion: " + message1.getValue());

        switch (message1.getValue()) {
            case "666_EM_Mad02":
                runOnUiThread(() -> {
                    imageHappyFace.setVisibility(View.VISIBLE);
                  playVideo(R.raw.angry_vid);
                });
                break;

            case "666_DA_Applaud":
                runOnUiThread(() -> {

                    imageHappyFace.setVisibility(View.VISIBLE);
                   playVideo(R.raw.happy);
                });
                break;
        }


        try {
            if (mSDKinit) {
//            showRobotFace();
                mRobotAPI.motionPlay(messageValueBody, true);
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFaceExpression(Message message1) {
        String messageValueFace = message1.getValue();
        Log.d("TAG3", "handleFaceExpression: " + message1.getValue());

        switch (message1.getValue()) {
            case "666_EM_Fear02":
                Log.d("TAG3", "handleFaceExpression: " + message1.getValue());
                runOnUiThread(() -> {
                    imageHappyFace.setVisibility(View.VISIBLE);
                   playVideo(R.raw.scared);
                });
                break;

            case "666_EM_Happy01":
                runOnUiThread(() -> {
                    imageHappyFace.setVisibility(View.VISIBLE);
                    playVideo(R.raw.happy);

                });
                break;
        }

        try {
            if (mSDKinit) {
//                showRobotFace();
//                runOnUiThread(() -> {
//                    imageHappyFace.setVisibility(View.GONE);
//                });

                mRobotAPI.motionPlay(messageValueFace, true);
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public void sendMessageForTeacher(String message) {
        if (client.isOpen()) {
            message = "{\"messageExpressionWithResponse\":" + message + "}";
            client.send(message);
        }
    }


    public void ttsSpeakAction(int id, String value) {

        Log.d("TAG2", "ttsSpeakAction: message with ****" + value);
        if (speakMessageId == id) {
            Log.d("TAG2", "ttsSpeakAction: message Specked ");
        } else {
            Log.d("TAG2", "ttsSpeakAction: message not Specked " + id);

            if (preferenceManager.getString(Constants.LANGUAGE).equals(Constants.ARABIC)) {
                Log.d("TAG2", "handleArabicVoice: " + preferenceManager.getString(Constants.LANGUAGE));
                handleArabicVoice(value);
                hideRobotFace();
            } else if (preferenceManager.getString(Constants.LANGUAGE).equals(ENGLISH)) {
                try {
                    if (mSDKinit) {
                        showRobotFace();
                        value = value.replace("*", "").replace("?", "").replace("!", "");

                        mRobotAPI.startTTS(value, String.valueOf(Locale.ENGLISH));
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            speakMessageId = id;
        }
    }

    private void showRobotFace() {
        if (mRobotAPI != null) {
            mRobotAPI.UnityFaceManager().showUnity();
            mouthOn(FACE_MOUTH_SPEED);
        }
    }

    private void hideRobotFace() {
        if (mRobotAPI != null) {
            mRobotAPI.UnityFaceManager().hideFace();
            mouthOff();
        }
    }

    private void mouthOn(long speed) {
        if (mRobotAPI != null) {
            mRobotAPI.UnityFaceManager().mouthOn(speed);
        }
    }

    private void mouthOff() {
        if (mRobotAPI != null) {
            mRobotAPI.UnityFaceManager().mouthOff();
        }
    }

    private void playVideo(int video) {

        videoView.setVisibility(View.VISIBLE);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +video));
        videoView.start();
    }
}


