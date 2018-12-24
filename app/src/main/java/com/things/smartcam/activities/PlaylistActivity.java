package com.things.smartcam.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.things.smartcam.BuildConfig;
import com.things.smartcam.DbCamera;
import com.things.smartcam.Fragment.FullScreenDialog;
import com.things.smartcam.MainActivity;
import com.things.smartcam.R;
import com.things.smartcam.TronXCamera;
import com.things.smartcam.adapter.CameraAdapter;
import com.things.smartcam.adapter.CameraViewHolder;
import com.things.smartcam.adapter.ItemClickListener;
import com.things.smartlib.OnvifManager;
import com.things.smartlib.listeners.DeviceMediaProfileListener;
import com.things.smartlib.listeners.OnvifDeviceInformationListener;
import com.things.smartlib.listeners.OnvifResponseListener;
import com.things.smartlib.listeners.OnvifServiceListener;
import com.things.smartlib.listeners.OnvifStreamUriListener;
import com.things.smartlib.models.DeviceMediaProfile;
import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.OnvifDeviceInformation;
import com.things.smartlib.models.OnvifServices;
import com.things.smartlib.player.TronXPlayer;
import com.things.smartlib.responses.OnvifResponse;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PlaylistActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, OnvifResponseListener {

    private static final int REQUEST_PLAY = 1000;
    private static final String TAG = "TAG";
    private RecyclerView mRecyclerView;
    private int mPos;
    public static final String EXTRA_BOOLEAN_SELECT_ITEM_TO_PLAY = "extra-boolean-select-item-to-play";
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 111;

    Toolbar toolbar;
    ImageButton toolbarSetting,toolbarAdd,toolbarMultiPlay;
    SwipeRefreshLayout pullToRefresh;
    TextView emptyView;

    private DbCamera mDatabase;

    List<TronXCamera> allCameras = null;

    DeviceMediaProfile mediaProfile;
    OnvifManager onvifManager;
    OnvifDevice onvifDevice;
    String profile,streamuri;
    Spinner spinner;
    String devurl = null;
    int clickpos = -1;

    CameraViewHolder holdern;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.content_playlist);

       toolbar = (Toolbar)findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

       pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pull_to_refresh);

       toolbarAdd = (ImageButton) findViewById(R.id.toolbar_add);
       toolbarMultiPlay = (ImageButton) findViewById(R.id.toolbar_multiplay);
       toolbarSetting = (ImageButton) findViewById(R.id.toolbar_setting);

       mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
       emptyView = (TextView) findViewById(R.id.empty_view);
        mDatabase = new DbCamera(PlaylistActivity.this);
        allCameras = new ArrayList<>();
       allCameras = mDatabase.getallCameras();

       mRecyclerView.setHasFixedSize(true);
       mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_source_item, parent, false);
                return new CameraViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                CameraViewHolder plvh = (CameraViewHolder) holder;
                TronXCamera tronXCamera = allCameras.get(position);
                String name = tronXCamera.getName();
                String url = tronXCamera.getInternalHost();
                if (!TextUtils.isEmpty(name)) {
                    plvh.mTextView.setText(name);
                } else {
                    plvh.mTextView.setText(url);
                }
                File file = url2localPosterFile(PlaylistActivity.this, url);
                Glide.with(PlaylistActivity.this).load(file).signature(new StringSignature(UUID.randomUUID().toString())).placeholder(R.drawable.placeholder).centerCrop().into(plvh.mImageView);

                int audienceNumber = tronXCamera.getId();
                if (audienceNumber > 0) {
                    plvh.mAudienceNumber.setText(String.format("No.:%d", audienceNumber));
                    plvh.mAudienceNumber.setVisibility(View.VISIBLE);
                } else {
                    plvh.mAudienceNumber.setVisibility(View.GONE);
                }
            }

            @Override
            public int getItemCount() {
                return allCameras.size();
            }
        });

      /*  if (savedInstanceState == null) {
            if (!getIntent().getBooleanExtra(EXTRA_BOOLEAN_SELECT_ITEM_TO_PLAY, false)) {
                startActivity(new Intent(this, SplashActivity.class));
            }
        }*/


        if (!isPro()) {
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pullToRefresh.setRefreshing(false);
                }
            });

            toolbarSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(PlaylistActivity.this, SettingsActivity.class));
                }
            });

        } else {
            toolbarSetting.setVisibility(View.GONE);
            pullToRefresh.setEnabled(false);
        }

        showOrHideEmptyView();

        toolbarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FullScreenDialog dialog = new FullScreenDialog();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                dialog.show(ft, FullScreenDialog.TAG);

              /*  final EditText edit = new EditText(PlaylistActivity.this);
                edit.setHint(isPro() ? "RTSP/RTMP/HTTP/HLS地址" : "RTSP地址(格式为RTSP://...)");
                final int hori = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
                final int verti = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
                edit.setPadding(hori, verti, hori, verti);

//                edit.setFilters(new InputFilter[]{new InputFilter() {
//                    @Override
//                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                        if (end-start == 0){
//                            return null;
//                        }
//                        Log.d(TAG, String.format("source:%s,start:%d,end:%d;dest:%s,dstart:%d,dend:%d", source, start,end, dest, dstart,dend));
//                        char[] chs = new char[dest.length()-(dend - dstart) + end-start];
//                        int i =0;
//                        int idx = 0;
//                        for (;i<dstart;i++){
//                            chs[idx++] = dest.charAt(i);
//                        }
//
//                        for (i = start;i<end;i++){
//                            chs[idx++] = source.charAt(i);
//                        }
//                        for (i=dend;i<dest.length();i++){
//                            chs[idx++] = dest.charAt(i);
//                        }
//
//                        String dst = new String(chs);
//                        dst = dst.toLowerCase();
//                        if ("rtsp://".indexOf(dst) == 0){
//                            return null;
//                        }else if (dst.indexOf("rtsp://") == 0){
//                            return null;
//                        }else{
//                            return "";
//                        }
//                    }
//                }});
                final AlertDialog dlg = new AlertDialog.Builder(PlaylistActivity.this).setView(edit).setTitle("请输入播放地址").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mRTSPUrl = String.valueOf(edit.getText());
                        if (TextUtils.isEmpty(mRTSPUrl)) {
                            return;
                        }
//                        if (!isPro()){
//                            if (mRTSPUrl.toLowerCase().indexOf("rtsp://")!=0){
//                                Toast.makeText(PlaylistActivity.this,"不是合法的RTSP地址，请重新添加.",Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        }else{
////                            if (mRTSPUrl.toLowerCase().indexOf("rtsp://")!=0 && mRTSPUrl.toLowerCase().indexOf("rtmp://")!=0 && mRTSPUrl.toLowerCase().indexOf("http://")!=0 && mRTSPUrl.toLowerCase().indexOf("https://")!=0&& mRTSPUrl.toLowerCase().indexOf("hls://")!=0){
////                                Toast.makeText(PlaylistActivity.this,"不是合法的地址，请重新添加.",Toast.LENGTH_SHORT).show();
////                                return;
////                            }
//                        }
                        ContentValues cv = new ContentValues();
                        cv.put(VideoSource.URL, mRTSPUrl);
                        TheApp.sDB.insert(VideoSource.TABLE_NAME, null, cv);

                        mCursor.close();
                        mCursor = TheApp.sDB.query(VideoSource.TABLE_NAME, null, null, null, null, null, null);
                        mRecyclerView.getAdapter().notifyItemInserted(mCursor.getCount() - 1);
                        showOrHideEmptyView();
                    }
                }).setNegativeButton("取消", null).create();
                dlg.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
                dlg.show();*/
            }
        });
      /*  mBinding.toolbarAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlaylistActivity.this, AboutActivity.class));
            }
        });*/
      /*  String url;
        if (PlaylistActivity.isPro()) {
            url = "http://www.easydarwin.org/versions/easyplayer_pro/version.txt";
        } else {
            url = "http://www.easydarwin.org/versions/easyplayer/version.txt";
        }
        update = new UpdateMgr(this);
        update.checkUpdate(url);*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //update.doDownload();
                }
        }
    }

    public static boolean isPro() {
        return BuildConfig.FLAVOR.equals("pro");
    }

    public static File url2localPosterFile(Context context, String url) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] result = messageDigest.digest(url.getBytes());
            return new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), Base64.encodeToString(result, Base64.NO_WRAP | Base64.URL_SAFE));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onLongClick(View view) {
        /*CameraViewHolder holder = (CameraViewHolder) view.getTag();
        final int pos = holder.getAdapterPosition();
        if (pos != -1) {

            new AlertDialog.Builder(this).setItems(new CharSequence[]{"修改", "删除"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        mCursor.moveToPosition(pos);
                        String playUrl = mCursor.getString(mCursor.getColumnIndex(VideoSource.URL));
                        final int _id = mCursor.getInt(mCursor.getColumnIndex(VideoSource._ID));
                        final EditText edit = new EditText(PlaylistActivity.this);
                        edit.setHint(isPro() ? "RTSP/RTMP/HLS地址" : "RTSP地址");
                        final int hori = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
                        final int verti = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
                        edit.setPadding(hori, verti, hori, verti);
                        edit.setText(playUrl);
                        final AlertDialog alertDialog = new AlertDialog.Builder(PlaylistActivity.this).setView(edit).setTitle("请输入播放地址").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String mRTSPUrl = String.valueOf(edit.getText());
                                if (TextUtils.isEmpty(mRTSPUrl)) {
                                    return;
                                }
                                ContentValues cv = new ContentValues();
                                cv.put(VideoSource.URL, mRTSPUrl);
                                TheApp.sDB.update(VideoSource.TABLE_NAME, cv, VideoSource._ID + "=?", new String[]{String.valueOf(_id)});

                                mCursor.close();
                                mCursor = TheApp.sDB.query(VideoSource.TABLE_NAME, null, null, null, null, null, null);
                                mRecyclerView.getAdapter().notifyItemChanged(pos);
                            }
                        }).setNegativeButton("取消", null).create();
                        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
                            }
                        });
                        alertDialog.show();
                    } else {
                        new AlertDialog.Builder(PlaylistActivity.this).setMessage("确定要删除该地址吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mCursor.moveToPosition(pos);
                                TheApp.sDB.delete(VideoSource.TABLE_NAME, VideoSource._ID + "=?", new String[]{String.valueOf(mCursor.getInt(mCursor.getColumnIndex(VideoSource._ID)))});
                                mCursor.close();
                                mCursor = TheApp.sDB.query(VideoSource.TABLE_NAME, null, null, null, null, null, null);
                                mRecyclerView.getAdapter().notifyItemRemoved(pos);
                                showOrHideEmptyView();
                            }
                        }).setNegativeButton("取消", null).show();
                    }
                }
            }).show();
        }*/
        return true;
    }

    private void showOrHideEmptyView() {
        if (allCameras.size() > 0) {
           emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    public void onMultiplay(View view) {
        Intent intent = new Intent(this, MultiplayActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResponse(OnvifDevice onvifDevice, OnvifResponse onvifResponse) {

    }

    @Override
    public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {

    }


    //public class CameraViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public class CameraViewHolder extends RecyclerView.ViewHolder {

        //ItemClickListener itemClickListener;

        private final TextView mTextView;
        private final TextView mAudienceNumber;
        private final ImageView mImageView;


        public CameraViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.video_source_item_name);
            mImageView = (ImageView)itemView.findViewById(R.id.video_source_item_thumb);
            mAudienceNumber = (TextView)itemView.findViewById(R.id.video_source_item_audience_number);
            itemView.setOnClickListener(PlaylistActivity.this);
            itemView.setOnLongClickListener(PlaylistActivity.this);
            itemView.setTag(this);
        }


       /* @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition(),null);
        }

        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }*/
    }


    @Override
    public void onClick(View view) {
        holdern = (CameraViewHolder) view.getTag();
        clickpos = holdern.getAdapterPosition();
        if (clickpos != -1) {
            Toast.makeText(PlaylistActivity.this, ""+allCameras.get(clickpos).getInternalHost()+" "+allCameras.get(clickpos).getInternalHttp(), Toast.LENGTH_SHORT).show();
            devurl = allCameras.get(clickpos).getInternalHost()+":"+allCameras.get(clickpos).getInternalHttp();
            addDevice();
            /*mCursor.moveToPosition(pos);
            String playUrl = mCursor.getString(mCursor.getColumnIndex(VideoSource.URL));
            if (!TextUtils.isEmpty(playUrl)) {
                if (getIntent().getBooleanExtra(EXTRA_BOOLEAN_SELECT_ITEM_TO_PLAY, false)){
                    Intent data = new Intent();
                    data.putExtra("url", playUrl);
                    setResult(RESULT_OK, data);
                    finish();
                }else {
                   *//* if (BuildConfig.YUV_EXPORT) {
                        // YUV EXPORT DEMO..
                        Intent i = new Intent(PlaylistActivity.this, YUVExportActivity.class);
                        i.putExtra("play_url", playUrl);
                        mPos = pos;
                        startActivity(i);
                    }else{
                        Intent i = new Intent(PlaylistActivity.this, PlayActivity.class);
                        i.putExtra("play_url", playUrl);
                        mPos = pos;
                        ActivityCompat.startActivityForResult(this, i, REQUEST_PLAY, ActivityOptionsCompat.makeSceneTransitionAnimation(this, holder.mImageView, "video_animation").toBundle());
                    }*//*
                    Intent i = new Intent(PlaylistActivity.this, PlayActivity.class);
                    i.putExtra("play_url", playUrl);
                    mPos = pos;
                    ActivityCompat.startActivityForResult(this, i, REQUEST_PLAY, ActivityOptionsCompat.makeSceneTransitionAnimation(this, holder.mImageView, "video_animation").toBundle());
                }

//                Intent i = new Intent(PlaylistActivity.this, TwoWndPlayActivity.class);
//                i.putExtra("play_url", playUrl);
//                mPos = pos;
//                ActivityCompat.startActivityForResult(this, i, REQUEST_PLAY, ActivityOptionsCompat.makeSceneTransitionAnimation(this, holder.mImageView, "video_animation").toBundle());

            }*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRecyclerView.getAdapter().notifyItemChanged(mPos);
    }

    @Override
    protected void onDestroy() {
       mDatabase.close();
        super.onDestroy();
    }

    private void addDevice(){

        if(devurl==null)
        {
            Toast.makeText(PlaylistActivity.this, "Enter Device IP or Search for devices", Toast.LENGTH_SHORT).show();
            return;
        }

        onvifManager = new OnvifManager();
        onvifManager.setOnvifResponseListener(PlaylistActivity.this);

        onvifDevice = new OnvifDevice(devurl, "admin", "admin");
        getServices();
    }

    private void getServices() {

        if(onvifDevice == null)
        {
            Toast.makeText(PlaylistActivity.this, "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }

        onvifManager.getServices(onvifDevice, new OnvifServiceListener() {
            @Override
            public void onServicesReceived(OnvifDevice onvifDevice, OnvifServices onvifServices) {
                if (onvifServices != null) {
                          /*  Toast.makeText(getActivity(),"Service : "+onvifServices.getServicepath()+"\n"
                                    +"Device : "+onvifServices.getDeviceinfomationpath()+"\n"
                                    +"Profile : "+onvifServices.getProfilespath()+"\n"
                                    +"Stream : "+onvifServices.getStreamURIpath(), Toast.LENGTH_SHORT).show();*/
                    getDeviceInformation();
                }
                else
                {
                    Toast.makeText(PlaylistActivity.this, "No Servcies Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDeviceInformation() {
        if(onvifDevice == null)
        {
            Toast.makeText(PlaylistActivity.this, "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }
        onvifManager.getDeviceInformation(onvifDevice, new OnvifDeviceInformationListener() {
            @Override
            public void onDeviceInformationReceived(OnvifDevice onvifDevice, OnvifDeviceInformation onvifDeviceInformation) {

                if (onvifDeviceInformation != null) {

                           /* Toast.makeText(getActivity(), "Model :: "+onvifDeviceInformation.getModel()+"\n"
                                    +"Manufacturer :: "+onvifDeviceInformation.getManufacturer()+"\n"
                                    +"Serial Number :: "+onvifDeviceInformation.getSerialNumber()+"\n"
                                    +"Hardware ID :: "+onvifDeviceInformation.getHardwareId(), Toast.LENGTH_SHORT).show();*/
                    getProfiles();
                }
                else
                {
                    Toast.makeText(PlaylistActivity.this, "Device Info Not found", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getProfiles() {

        if(onvifDevice == null)
        {
            Toast.makeText(PlaylistActivity.this, "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }

        onvifManager.getMediaProfiles(onvifDevice, new DeviceMediaProfileListener() {
            @Override
            public void onMediaProfileReceived(OnvifDevice onvifDevice, List<DeviceMediaProfile> mediaProfiles) {
                if (mediaProfiles != null) {
                    mediaProfile=mediaProfiles.get(0);
                    //Toast.makeText(getActivity(),""+mediaProfiles.get(0).getToken()+"\n"+mediaProfiles.get(1).getToken(), Toast.LENGTH_SHORT).show();
                    getStreamUri();
                }
                else
                {

                    Toast.makeText(PlaylistActivity.this, "Profiles Not found", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void getStreamUri() {

        if(onvifDevice == null)
        {
            Toast.makeText(PlaylistActivity.this, "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }

        onvifManager.getMediaStreamURI(onvifDevice, mediaProfile, new OnvifStreamUriListener() {
            @Override
            public void onvifStreamUriReceived(OnvifDevice onvifDevice, DeviceMediaProfile onvifMediaProfile, String s) {
                if (s != null) {
                    profile = onvifMediaProfile.getName();
                    streamuri = s;
                    //Toast.makeText(getActivity(),"Stream URI ::"+s, Toast.LENGTH_SHORT).show();
                    play();
                }
                else {
                    Toast.makeText(PlaylistActivity.this, "URI Not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void play() {
        if(onvifDevice == null)
        {
            Toast.makeText(PlaylistActivity.this, "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }
        //VXG PLAYER
        //Intent intent = new Intent(PlaylistActivity.this, TronXPlayer.class);
        //Bundle bundle = new Bundle();
        //bundle.putString("streamUri", streamuri);
        //bundle.putString("profile", profile);
        //intent.putExtras(bundle);
        //startActivity(intent);

        String playUrl = streamuri;
        if (!TextUtils.isEmpty(playUrl)) {
            if (getIntent().getBooleanExtra(EXTRA_BOOLEAN_SELECT_ITEM_TO_PLAY, false)){
                Intent data = new Intent();
                data.putExtra("url", playUrl);
                setResult(RESULT_OK, data);
                finish();
            }else {
                /*if (BuildConfig.YUV_EXPORT) {
                    // YUV EXPORT DEMO..
                    Intent i = new Intent(PlaylistActivity.this, YUVExportActivity.class);
                    i.putExtra("play_url", playUrl);
                    mPos = pos;
                    startActivity(i);
                }else{
                    Intent i = new Intent(PlaylistActivity.this, PlayActivity.class);
                    i.putExtra("play_url", playUrl);
                    mPos = pos;
                    ActivityCompat.startActivityForResult(this, i, REQUEST_PLAY, ActivityOptionsCompat.makeSceneTransitionAnimation(this, holder.mImageView, "video_animation").toBundle());
                }*/

                Intent i = new Intent(PlaylistActivity.this, PlayActivity.class);
                i.putExtra("play_url", playUrl);
                mPos = clickpos;
                //ActivityCompat.startActivityForResult(this, i, REQUEST_PLAY, ActivityOptionsCompat.makeSceneTransitionAnimation(this,holdern.mImageView, "video_animation").toBundle());
                ActivityCompat.startActivityForResult(this, i, REQUEST_PLAY, null);
            }

//                Intent i = new Intent(PlaylistActivity.this, TwoWndPlayActivity.class);
//                i.putExtra("play_url", playUrl);
//                mPos = pos;
//                ActivityCompat.startActivityForResult(this, i, REQUEST_PLAY, ActivityOptionsCompat.makeSceneTransitionAnimation(this, holder.mImageView, "video_animation").toBundle());

        }

    }

}
