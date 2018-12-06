package com.things.smartcam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class DbCamera extends DatabaseMaster {
    public static final String TABLE_CAMERA = "tronxcamera";
    public static final String KEY_ID = "id";
    //private final String KEY_CAMERA_ID = "cameraId";
    private final String KEY_CAMERA_NAME = "name";
    //private final String KEY_OWNER = "owner";
    //private final String KEY_REAL_OWNER = "realOwner";
    //private final String KEY_CAN_EDIT = "canEdit";
    //private final String KEY_CAN_DELETE = "canDelete";
    //private final String KEY_RIGHTS = "rights";
    private final String KEY_USERNAME = "username";
    private final String KEY_PASSWORD = "password";
    //private final String KEY_TIMEZONE = "timezone";
    //private final String KEY_VENDOR = "vendor";
    //private final String KEY_MODEL_ID = "modelId";
    //private final String KEY_MODEL_NAME = "modelName";
    //private final String KEY_MAC = "mac";
    //private final String KEY_EXTERNAL_JPG_URL = "externalSnapshotUrl";
    //private final String KEY_INTERNAL_JPG_URL = "internalSnapshotUrl";
    //private final String KEY_EXTERNAL_RTSP_URL = "externalRtspUrl";
    //private final String KEY_INTERNAL_RTSP_URL = "internalRtspUrl";
    //private final String KEY_HLS_URL = "hlsUrl";
    //private final String KEY_IS_ONLINE = "isOnline";
    //private final String KEY_HAS_CREDENTIAL = "hasCredential";

    //Location
    //private final String KEY_LATITUDE = "latitude";
    //private final String KEY_LONGITUDE = "longitude";

    // Fields for edit camera
    private final String KEY_INTERNAL_HOST = "internalHost";
    //private final String KEY_EXTERNAL_HOST = "externalHost";
    private final String KEY_INTERNAL_HTTP = "internalHttp";
    private final String KEY_INTERNAL_RTSP = "internalRtsp";
    //private final String KEY_EXTERNAL_HTTP = "externalHttp";
    //private final String KEY_EXTERNAL_RTSP = "externalRtsp";

    //Thumbnail URL
    //private final String KEY_THUMBNAIL_URL = "thumbnailUrl";

    //private final String KEY_DISCOVERABLE = "isDiscoverable";
    //private final String KEY_PUBLIC = "isPublic";
    private final String KEY_RTSP_URL = "rtspurl";
    private final String KEY_PROFILE = "profile";

    public DbCamera(Context context) {
        super(context);
    }

    public void onCreateCustom(SQLiteDatabase db) {

        String CREATE_TABLE_Cameras = "CREATE TABLE " + TABLE_CAMERA + "(" + KEY_ID + " INTEGER " +
                "PRIMARY KEY autoincrement" + "," +
                "" + KEY_CAMERA_NAME + " TEXT NULL" + "," +
                "" + KEY_INTERNAL_HOST + " TEXT NULL" + ","  +
                "" + KEY_INTERNAL_HTTP + " TEXT NULL" + ","  +
                "" + KEY_INTERNAL_RTSP + " TEXT NULL" + ","  +
                "" + KEY_PROFILE + " TEXT NULL" + ","  +
                "" + KEY_RTSP_URL + " TEXT NULL" + ","  +
                "" + KEY_USERNAME + " TEXT NULL" + "," + KEY_PASSWORD + " TEXT NULL" +")";

/*
        String CREATE_TABLE_Cameras = "CREATE TABLE " + TABLE_CAMERA + "(" + KEY_ID + " INTEGER " +
                "PRIMARY KEY autoincrement" + "," + KEY_CAMERA_ID + " TEXT NOT NULL" + "," +
                "" + KEY_CAMERA_NAME + " TEXT NULL" + "," + KEY_OWNER + " TEXT  NOT NULL" + "," +
                "" + KEY_USERNAME + " TEXT NULL" + "," + KEY_PASSWORD + " TEXT NULL" + "," +
                "" + KEY_TIMEZONE + " TEXT NULL" + "," + KEY_VENDOR + " TEXT NULL" + "," +
                "" + KEY_MODEL_ID + " TEXT NULL" + "," + KEY_MAC + " TEXT NULL " + "," +
                "" + KEY_EXTERNAL_JPG_URL + " TEXT NULL " + "," + KEY_INTERNAL_JPG_URL + " TEXT " +
                "NULL " + "," + KEY_EXTERNAL_RTSP_URL + " TEXT NULL" + "," +
                "" + KEY_INTERNAL_RTSP_URL + " TEXT NULL" + "," + KEY_IS_ONLINE + " TEXT NULL" + "," +
                "" + KEY_HAS_CREDENTIAL + " INTEGER NULL" + "," + KEY_INTERNAL_HOST + " TEXT " +
                "NULL" + "," + KEY_EXTERNAL_HOST + " TEXT NULL" + "," +
                "" + KEY_INTERNAL_HTTP + " INTEGER NULL" + "," + KEY_EXTERNAL_HTTP + " INTEGER " +
                "NULL" + "," + KEY_INTERNAL_RTSP + " INTEGER NULL" + "," +
                "" + KEY_EXTERNAL_RTSP + " INTEGER NULL" + "," + KEY_THUMBNAIL_URL + " TEXT NULL" +
                "," + KEY_HLS_URL + " TEXT NULL" +
                "," + KEY_REAL_OWNER + " TEXT NULL" + "," + KEY_CAN_EDIT + " TEXT NULL" +
                "," + KEY_CAN_DELETE + " TEXT NULL" + "," + KEY_RIGHTS + " TEXT NULL" +
                "," + KEY_DISCOVERABLE + " INTEGER NULL" + "," + KEY_PUBLIC + " INTEGER NULL" + "," +
                "" + KEY_MODEL_NAME + " TEXT NULL" + ")";
                */

        db.execSQL(CREATE_TABLE_Cameras);
    }

    public void onUpgradeCustom(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAMERA);

        // Create tables again
        onCreateCustom(db);
    }

    public void addCamera(TronXCamera tronXCamera) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentValueFrom(tronXCamera);

        db.insert(TABLE_CAMERA, null, values);
        db.close();

    }

    public List<TronXCamera> getallCameras() {
        TronXCamera tronXCamera = null;
        List<TronXCamera> cameras = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CAMERA, new String[]{KEY_ID,
                        KEY_CAMERA_NAME, KEY_INTERNAL_HOST, KEY_INTERNAL_HTTP,
                        KEY_INTERNAL_RTSP,KEY_PROFILE,KEY_RTSP_URL,
                        KEY_USERNAME, KEY_PASSWORD},null,null, null, null,
                null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                tronXCamera = new TronXCamera();
                tronXCamera = getCameraFromCursor(cursor, tronXCamera);
                cameras.add(tronXCamera);
            }
            cursor.close();
        }
        db.close();

        return cameras;
    }

    // Getting single Camera
    public TronXCamera getCamera(int id) {
        TronXCamera tronXCamera = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CAMERA, new String[]{KEY_ID,
                        KEY_CAMERA_NAME, KEY_INTERNAL_HOST, KEY_INTERNAL_HTTP,
                        KEY_INTERNAL_RTSP,KEY_PROFILE,KEY_RTSP_URL,
                        KEY_USERNAME, KEY_PASSWORD},

/*
        Cursor cursor = db.query(TABLE_CAMERA, new String[]{KEY_ID, KEY_CAMERA_ID,
                        KEY_CAMERA_NAME, KEY_OWNER, KEY_USERNAME, KEY_PASSWORD, KEY_TIMEZONE, KEY_VENDOR,
                        KEY_MODEL_ID, KEY_MAC, KEY_EXTERNAL_JPG_URL, KEY_INTERNAL_JPG_URL,
                        KEY_EXTERNAL_RTSP_URL, KEY_INTERNAL_RTSP_URL, KEY_IS_ONLINE, KEY_HAS_CREDENTIAL,
                        KEY_INTERNAL_HOST, KEY_EXTERNAL_HOST, KEY_INTERNAL_HTTP, KEY_EXTERNAL_HTTP,
                        KEY_INTERNAL_RTSP, KEY_EXTERNAL_RTSP, KEY_THUMBNAIL_URL, KEY_HLS_URL, KEY_REAL_OWNER, KEY_CAN_EDIT,
                        KEY_CAN_DELETE, KEY_RIGHTS, KEY_DISCOVERABLE, KEY_PUBLIC, KEY_MODEL_NAME},
*/
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null,
                null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                tronXCamera = new TronXCamera();
                tronXCamera = getCameraFromCursor(cursor, tronXCamera);
            }
            cursor.close();
        }
        db.close();

        return tronXCamera;
    }

  /*  public ArrayList<EvercamCamera> getCamerasByOwner(String ownerUsername, int maxRecords) {
        String selectQuery = "SELECT  * FROM " + TABLE_CAMERA + " where upper(" + KEY_OWNER + ") " +
                "= upper('" + ownerUsername + "') order by " + KEY_ID + " asc";

        return selectCameraListByQuery(selectQuery, maxRecords);
    }*/

    public int updateCamera(TronXCamera tronXCamera) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentValueFrom(tronXCamera);

        // update row
        int return_value = db.update(TABLE_CAMERA, values, KEY_ID + " = ?",
                new String[]{String.valueOf(tronXCamera.getId())});
        db.close();

        return return_value;
    }

    public void deleteCamera(String cameraId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CAMERA, KEY_ID + " = ?", new String[]{cameraId});
        db.close();
    }

   /* public void deleteCameraByOwner(String ownerUsername) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CAMERA, " upper(" + KEY_OWNER + ") = upper(?)",
                new String[]{String.valueOf(ownerUsername)});
        db.close();
    }*/

    private ContentValues getContentValueFrom(TronXCamera tronXCamera) {
        ContentValues values = new ContentValues();
        values.put(KEY_CAMERA_NAME, tronXCamera.getName());
        values.put(KEY_INTERNAL_HOST, tronXCamera.getInternalHost());
        values.put(KEY_INTERNAL_HTTP, tronXCamera.getInternalHttp());
        values.put(KEY_INTERNAL_RTSP, tronXCamera.getInternalRtsp());
        values.put(KEY_PROFILE, tronXCamera.getInternalRtsp());
        values.put(KEY_RTSP_URL, tronXCamera.getInternalRtsp());
        values.put(KEY_USERNAME, tronXCamera.getUsername());
        values.put(KEY_PASSWORD, tronXCamera.getPassword());
        return values;
    }

    private ArrayList<TronXCamera> selectCameraListByQuery(String query, int maxRecords) {
        ArrayList<TronXCamera> cameraList = new ArrayList<TronXCamera>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            do {
                TronXCamera tronXCamera = new TronXCamera();
                tronXCamera = getCameraFromCursor(cursor, tronXCamera);

                cameraList.add(tronXCamera);
                count++;
            } while (cursor.moveToNext() && (maxRecords == 0 || count < maxRecords));
        }

        cursor.close();
        db.close();

        return cameraList;
    }

    private TronXCamera getCameraFromCursor(Cursor cursor, TronXCamera tronXCamera) {
        tronXCamera.setId(Integer.parseInt(cursor.getString(0)));
        tronXCamera.setName(cursor.getString(1));
        tronXCamera.setInternalHost(cursor.getString(2));
        tronXCamera.setInternalHttp(cursor.getString(3));
        tronXCamera.setInternalRtsp(cursor.getString(4));
        tronXCamera.setProfile(cursor.getString(5));
        tronXCamera.setRtspurl(cursor.getString(6));
        tronXCamera.setUsername(cursor.getString(7));
        tronXCamera.setPassword(cursor.getString(8));

        return tronXCamera;
    }
}
