package com.things.smartcam;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseMaster extends SQLiteOpenHelper {
    // Version 3: Added camera field :has credential
    // Version 4: New constraint camera id + owner
    // Version 5: Added camera fields for internal && external host &
    // port.Remove camera id-user constraint
    // Version 6: Added fields camera owner and can edit.
    // Version 7: Add can delete
    // Version 8: Removed database user table
    // Version 9: Add thumbnail URL in camera object
    // Version 10: Added rights string in camera object
    // Version 11: Added public & discoverable in camera object
    // Version 12: Added HLS URL in camera object
    // Version 13: Added model ID
    // Version 14: Replaced camera status with isOnline
    private static final String TAG = "DatabaseMaster";
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "tronxcam";
    private Context context = null;

    public DatabaseMaster(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        new DbCamera(this.context).onCreateCustom(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        new DbCamera(context).onUpgradeCustom(db, oldVersion, newVersion);
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
