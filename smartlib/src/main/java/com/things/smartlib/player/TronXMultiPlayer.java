package com.things.smartlib.player;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.things.smartlib.R;

import java.util.ArrayList;
import veg.mediaplayer.sdk.MediaPlayer;

public class TronXMultiPlayer extends AppCompatActivity {

    private static final String TAG = TronXMultiPlayer.class.getSimpleName();

    MediaPlayer player1, player2, player3;
    ProgressBar playerLoader1, playerLoader2, playerLoader3;
    PlayerCallBack3 playercallback1 = null,
            playercallback2 = null,
            playercallback3 = null;
    String url,
            url1 = "rtsp://admin:123456@192.168.50.79:5566/cam/realmonitor?channel=1&subtype=0&unicast=true&proto=Onvif",
            url2 = "rtsp://admin:123456@192.168.50.131:5544/cam/realmonitor?channel=1&subtype=0&unicast=true&proto=Onvif",
            url3 = "rtsp://admin:123456@192.168.50.116:5555/live/ch0";

    String userName = "";
    String password = "";
    String[] userNameArr, passwordArr;
    String[] profileList, streamList;
    int currentCount = 0;
    int deviceNum = 3;
    ArrayList<String> deviceURIs;
    String profileStr = "",
            streamUriStr = "";
    String[] secureUriArr;

    int countNum = 0;   //alertDialogs showtimes

    //connection
    int connectionProtocol = -1;            // 0 - udp, 1 - tcp, 2 - http, 3 - https, -1 - AUTO
    //int connectionProtocol = 0;
    int connectionDetectionTime = 5000;     // in milliseconds
    int connectionBufferingTime = 1000;     // in milliseconds

    // decoder
    int decoderType = 1;                    // 0 - soft, 1 - hard stagefright
    int decoderNumberOfCpuCores = 0;        // 0 - autodetect and use, >0 - manually set

    //render
    int rendererType = 1;                   // 0 - SDL, 1 - pure OpenGL
    int rendererEnableColorVideo = 1;       // grayscale, 1 - color
    int rendererAspectRatioMode = 1;        // 0 - resize, 1 - aspect

    // synchro
    int synchroEnable = 1;                  // enable audio video synchro
    int synchroNeedDropVideoFrames = 0;     // drop video frames if it older

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_players);
        findViews();

        checkDeviceURIs();
    }

    private void findViews(){
        player1 = (MediaPlayer)findViewById(R.id.player1);
        player2 = (MediaPlayer)findViewById(R.id.player2);
        player3 = (MediaPlayer)findViewById(R.id.player3);
        playercallback1 = new PlayerCallBack3(this, player1);
        playercallback2 = new PlayerCallBack3(this, player2);
        playercallback3 = new PlayerCallBack3(this, player3);
        playerLoader1 = (ProgressBar)findViewById(R.id.player1loaderIndicator);
        playerLoader2 = (ProgressBar)findViewById(R.id.player2loaderIndicator);
        playerLoader3 = (ProgressBar)findViewById(R.id.player3loaderIndicator);
    }

    private void checkDeviceURIs(){
        //url = "rtsp://192.168.0.2:554/onvif1";
        url = "rtsp://192.168.0.4:554/live/av0?user=admin&passwd=admin";
        play(player1);
        //url = secureUriArr[1];
        url = "rtsp://192.168.0.11:554/Onvif1";
        play(player2);
        //url = secureUriArr[2];
        url = "rtsp://192.168.0.4:554/live/av0?user=admin&passwd=admin";
        play(player3);
    }


    public void play(MediaPlayer currentPlayer){
        currentPlayer.Close();
        PlayerCallBack3 playercallback;
        if(currentPlayer == player1){
            playercallback = playercallback1;
        }else if(currentPlayer == player2){
            playercallback = playercallback2;
        }else{
            playercallback = playercallback3;
        }
        currentPlayer.Open(url,connectionProtocol, connectionDetectionTime, connectionBufferingTime,
                decoderType,
                rendererType,
                synchroEnable,
                synchroNeedDropVideoFrames,
                rendererEnableColorVideo,
                rendererAspectRatioMode,
                currentPlayer.getConfig().getDataReceiveTimeout(),
                decoderNumberOfCpuCores,
                playercallback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            player1.Close();
            player2.Close();
            player3.Close();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showProgressView(MediaPlayer currentPlayer){
        if(currentPlayer == player1){
            playerLoader1.setVisibility(View.VISIBLE);
        }else if(currentPlayer == player2){
            playerLoader2.setVisibility(View.VISIBLE);
        }else{
            playerLoader3.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressView(MediaPlayer currentPlayer){
        if(currentPlayer == player1){
            playerLoader1.setVisibility(View.GONE);
        }else if(currentPlayer == player2){
            playerLoader2.setVisibility(View.GONE);
        }else{
            playerLoader3.setVisibility(View.GONE);
        }
    }

    public boolean isPlayerBusy(MediaPlayer currentPlayer){
        if(currentPlayer == player1){
            if(player1 != null && (player1.getState() == MediaPlayer.PlayerState.Closing ||
                    player1.getState() == MediaPlayer.PlayerState.Opening)){
                return true;
            }else
                return false;
        }else if(currentPlayer == player2){
            if(player2 != null && (player2.getState() == MediaPlayer.PlayerState.Closing ||
                    player2.getState() == MediaPlayer.PlayerState.Opening)){
                return true;
            }else return false;
        }else{
            if(player3 != null && (player3.getState() == MediaPlayer.PlayerState.Closing ||
                    player3.getState() == MediaPlayer.PlayerState.Opening)){
                return true;
            }else return false;
        }
    }

    public int mOldMsg = 0;



}
