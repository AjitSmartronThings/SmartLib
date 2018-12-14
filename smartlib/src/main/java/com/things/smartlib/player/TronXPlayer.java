package com.things.smartlib.player;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.things.smartlib.OnvifManager;
import com.things.smartlib.R;
import com.things.smartlib.listeners.OnvifPTZListener;
import com.things.smartlib.models.DeviceMediaProfile;
import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.PTZMoveType;
import com.things.smartlib.models.PTZType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import veg.mediaplayer.sdk.MediaPlayer;


/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.player
 * @date :         11/2/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class TronXPlayer extends AppCompatActivity {
    MediaPlayer player1;
    MediaPlayer.MediaPlayerCallback Player1Callback;
    ProgressBar loaderIndicator1 = null;
    private PlayerCallBacks Player1CallBacks = null;

    //connection
    //int connectionProtocol = 1;            // 0 - udp, 1 - tcp, 2 - http, 3 - https, -1 - AUTO
    int connectionProtocol = 0;            // 0 - udp, 1 - tcp, 2 - http, 3 - https, -1 - AUTO
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

    //    String serverUri = "http://140.118.7.11/onvif_storage.php";
    String serverUri = "http://140.118.7.11:8080";
    double x, y, z;

    Button Top, Down, Left, Right, ZoomIn, ZoomOut;
    ImageView reddot;
    TextView time;
    ImageButton record, record30sec;
    Spinner resolution;

    private String path, securityUri;
    private String PTZUri, profileName;
    private String userName, userPassword;

    ProgressDialog mDialog;
    long startTime;
    private Handler timeHandler = new Handler();
    boolean stopRecording = false;
    boolean recording30sec = false;
    String[] streamUriArr, resolutionArr,profileArr;

    OnvifManager onvifManager;

    OnvifDevice onvifDevice;

    DeviceMediaProfile mediaProfile;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation){
            case Configuration.ORIENTATION_PORTRAIT:
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tronx__player);
        onvifManager = new OnvifManager();
        findViews();
        selectResolution();
        play();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Player1CallBacks.player_state_error = PlayerCallBacks.PlayerStatesError.None;
    }

    @Override
    protected void onResume() {
        super.onResume();
        play();
    }

    private void findViews(){
        player1 = (MediaPlayer)findViewById(R.id.playerView1);
        loaderIndicator1 = (ProgressBar)findViewById(R.id.loaderIndicator1);
        Player1CallBacks = new PlayerCallBacks(this, player1);
       //Top = (Button)findViewById(R.id.Top);
        //Down = (Button)findViewById(R.id.Down);
        //Left = (Button)findViewById(R.id.Left);
        //Right = (Button)findViewById(R.id.Right);
     /*   ZoomIn = (Button)findViewById(R.id.ZoomIn);
        ZoomOut = (Button)findViewById(R.id.ZoomOut);
        record = (ImageButton)findViewById(R.id.record);
        record.setColorFilter(getApplicationContext().getResources().getColor(R.color.white));
        time = (TextView)findViewById(R.id.time);
        time.setVisibility(View.GONE);
        reddot = (ImageView)findViewById(R.id.reddot);
        reddot.setColorFilter(getApplicationContext().getResources().getColor(R.color.colorAccent));
        reddot.setVisibility(View.GONE);
        record30sec = (ImageButton)findViewById(R.id.recpord30sec);
        resolution = (Spinner)findViewById(R.id.resolution);*/
    }

    private void selectResolution(){

        onvifDevice = (OnvifDevice) getIntent().getSerializableExtra("obj");
        mediaProfile = (DeviceMediaProfile) getIntent().getSerializableExtra("media");
        Bundle bundle = this.getIntent().getExtras();
        //streamUriArr = Arrays.copyOf(bundle.getStringArray("streamUriArr"), bundle.getStringArray("streamUriArr").length);
        //resolutionArr = Arrays.copyOf(bundle.getStringArray("resolutionArr"), bundle.getStringArray("resolutionArr").length);
        //profileArr = Arrays.copyOf(bundle.getStringArray("profileArr"), bundle.getStringArray("profileArr").length);
        //PTZUri=bundle.getString("ptzUri");

        String streamUri=bundle.getString("streamUri");
        String profile=bundle.getString("profile");

        path = streamUri;
        securityUri = path.replace("rtsp://", "rtsp://" + userName + ":" + userPassword + "@");
        Log.v("VXG securityUri", securityUri);

        profileName=profile;

       /* ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, resolutionArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resolution.setAdapter(adapter);

        resolution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)view).setText(null);
                String res = parent.getSelectedItem().toString();
                Toast.makeText(TronXPlayer.this, res, Toast.LENGTH_SHORT).show();
                path = streamUriArr[position];
                securityUri = path.replace("rtsp://", "rtsp://" + userName + ":" + userPassword + "@");
                profileName=profileArr[position];
                play();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            player1.Close();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void play(){
        player1.Close();
        player1.Open(securityUri, connectionProtocol, connectionDetectionTime, connectionBufferingTime,
                decoderType,
                rendererType,
                synchroEnable,
                synchroNeedDropVideoFrames,
                rendererEnableColorVideo,
                rendererAspectRatioMode,
                player1.getConfig().getDataReceiveTimeout(),
                decoderNumberOfCpuCores,
                Player1CallBacks);
    }

    public void showProgressView(MediaPlayer currentPlayer){
        loaderIndicator1.setVisibility(View.VISIBLE);
    }

    public void hideProgressView(MediaPlayer currentPlayer){
        loaderIndicator1.setVisibility(View.GONE);
    }

    public boolean isPlayerBusy(){
        if(player1 != null && (player1.getState() == MediaPlayer.PlayerState.Closing ||
                player1.getState() == MediaPlayer.PlayerState.Opening)){
            return true;
        }else return false;
    }

    public int mOldMsg = 0;

    Handler backgroundDetect = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.v("handleMwssage", "doing..");
                    //resolution.setVisibility(View.GONE);
                    startTime = System.currentTimeMillis();
                    timeHandler.removeCallbacks(updateTimer);
                    timeHandler.postDelayed(updateTimer, 1000);
                    //time.setVisibility(View.VISIBLE);
                    //time.setText("00:00");
                    //reddot.setVisibility(View.VISIBLE);
                    //record.setColorFilter(getApplicationContext().getResources().getColor(R.color.colorAccent));
                    //fadeOutAndHideImage(reddot);
                    break;
                case 1:
                    //time.setVisibility(View.GONE);
                    timeHandler.removeCallbacks(updateTimer);   //remove runnable timer
                    //reddot.setAnimation(null);
                    //record30sec.setColorFilter(getApplicationContext().getResources().getColor(R.color.white));
                    //record30sec.setAnimation(null);
                    //reddot.setVisibility(View.GONE);
                    //recording30sec = false;
                    //record.setVisibility(View.VISIBLE);
                    //resolution.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };



    private void btnAction(){
        Top.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Top.setBackgroundResource(R.drawable.circle2);
                    onvifManager.sendPTZRequest(PTZMoveType.PTZ_CONTINUOUS,onvifDevice, mediaProfile, PTZType.UP_MOVE, new OnvifPTZListener() {
                        @Override
                        public void onPTZReceived(OnvifDevice onvifDevice, boolean status) {
                            System.out.println(status);
                        }
                    });
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //Top.setBackgroundResource(R.drawable.circle);
                    //PTZ stop_continuousMove = new PTZ();
                    //stop_continuousMove.execute("stop");
                }
                return false;
            }
        });

        Down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Down.setBackgroundResource(R.drawable.circle2);
                    x = 0;
                    y = (-1);
                    z = 0;
                    //PTZ top_continuousMove = new PTZ();
                    //top_continuousMove.execute("continuousMove");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //Down.setBackgroundResource(R.drawable.circle);
                    //PTZ stop_continuousMove = new PTZ();
                    //stop_continuousMove.execute("stop");
                }
                return false;
            }
        });

        Left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Left.setBackgroundResource(R.drawable.circle2);
                    x = (-1);
                    y = 0;
                    z = 0;
                    //PTZ top_continuousMove = new PTZ();
                    //top_continuousMove.execute("continuousMove");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //Left.setBackgroundResource(R.drawable.circle);
                    //PTZ stop_continuousMove = new PTZ();
                    //stop_continuousMove.execute("stop");
                }
                return false;
            }
        });

        Right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Right.setBackgroundResource(R.drawable.circle2);
                    x = (1);
                    y = 0;
                    z = 0;
                    //PTZ top_continuousMove = new PTZ();
                    //top_continuousMove.execute("continuousMove");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //Right.setBackgroundResource(R.drawable.circle);
                    //PTZ stop_continuousMove = new PTZ();
                    //stop_continuousMove.execute("stop");
                }
                return false;
            }
        });

       /* ZoomIn.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //ZoomIn.setBackgroundResource(R.drawable.circle2);
                    x = 0;
                    y = 0;
                    z = (1);
                    PTZ Zoom = new PTZ();
                    Zoom.execute("Zoom");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //ZoomIn.setBackgroundResource(R.drawable.circle);
                    PTZ stopZoom = new PTZ();
                    stopZoom.execute("ZoomStop");
                }
                return false;
            }
        });

        ZoomOut.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //ZoomOut.setBackgroundResource(R.drawable.circle2);
                    x = 0;
                    y = 0;
                    z = (-1);
                    PTZ Zoom = new PTZ();
                    Zoom.execute("Zoom");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //ZoomOut.setBackgroundResource(R.drawable.circle);
                    PTZ stopZoom = new PTZ();
                    stopZoom.execute("ZoomStop");
                }
                return false;
            }
        });*/
    }

    private void fadeOutAndHideImage(final ImageView img){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(500);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                img.setImageResource(R.drawable.record_48);
                fadeInAndShowImage(img);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        img.startAnimation(fadeOut);
    }

    private void fadeInAndShowImage(final ImageView img){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(500);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fadeOutAndHideImage(img);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        img.startAnimation(fadeIn);
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            long spentTime = System.currentTimeMillis() -  startTime;
            long minius = (spentTime / 1000) / 60;
            long second = (spentTime / 1000) % 60;
            String temp ;
            if(minius < 10){
                if(second < 10){
                    time.setText("0" + minius + ":" + "0" + second);
                    temp = "0" + minius + ":" + "0" + second;
                }else {
                    time.setText("0" + minius + ":" + second);
                    temp = "0" + minius + ":" + second;
                }

            }else{
                if(second < 10){
                    time.setText(minius + ":" + "0" + second);
                    temp = minius + ":" + "0" + second;
                }else {
                    time.setText(minius + ":" + second);
                    temp = minius + ":" + second;
                }

            }
            Log.v("time ", temp);
            if(temp.equals("00:30") && recording30sec == true){
                backgroundDetect.sendEmptyMessage(1);
            }
            timeHandler.postDelayed(this, 1000);
        }
    };

   /* private class PTZ extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            switch(params[0]){

                case "continuousMove":
                    if(!PTZUri.equals(null)){
                        ContinuousMove continuousMove = new ContinuousMove(PTZUri, x, y, z, profileName, "", "");
                        //ContinuousMove continuousMove = new ContinuousMove("http://192.168.0.5:36000/onvif/device_service", x, y, z, profileName, "", "");
                        ContinuousMove_SOAP = continuousMove.getContinuousMoveResponse();
                    }
                    break;
                case "stop":
                    //if(!tinyDB.getString("PTZUri").equals(null)){
                    if(!PTZUri.equals(null)){
                        //Stop stop = new Stop("http://192.168.0.5:36000/onvif/device_service", "true", "false", profileName, "", "");
                        Stop stop = new Stop(PTZUri, "true", "false", profileName, "", "");
                        STOP_SOAP = stop.getStopResponse();
                    }
                    break;
                case "Zoom":
                    if(!PTZUri.equals(null)){
                        Zoom zoom = new Zoom(PTZUri, x, y, z, profileName, "", "");
                        ZoomSoap = zoom.getZoomResponse();
                    }
                    break;
                case "ZoomStop":
                    if(!PTZUri.equals(null)){
                        StopZoom stopZoom = new StopZoom(PTZUri, "true", "false", profileName, "", "");
                        ZoomStop_SOAP = stopZoom.getStopZoomResponse();
                    }
                    break;

            }
            return null;
        }
    }*/

    /*private class recordingAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setReadTimeout(1500);
                connection.setConnectTimeout(15000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
//                String data = "data=" + params[1] + "&streamUri=" + params[2];
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("data", params[1]);
                jsonObject.put("streamUri", params[2]);
                OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
                outputStream.write(jsonObject.toString().getBytes());
                outputStream.flush();

                int statusCode = connection.getResponseCode();

                if(statusCode == 200){
                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                    String responseStr = convertInputStreamToString(inputStream);
                    Log.v("recordingRes", responseStr);
                    return responseStr;
                }else{

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }*/

    /*public String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        String result = "";

        while((line = bufferedReader.readLine()) != null){
            result += line;
        }

        *//* close stream*//*
        if(inputStream != null){
            inputStream.close();
        }

        return result;
    }*/
}

