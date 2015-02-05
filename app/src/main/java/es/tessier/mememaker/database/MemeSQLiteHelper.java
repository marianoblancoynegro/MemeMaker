package es.tessier.mememaker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Evan Anger on 8/17/14.
 */
public class MemeSQLiteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "memes.db";
    public static final int DB_VERSION=1;
    public static final String TAG = MemeSQLiteHelper.class.getName();

    //meme table functionality
    public static final String MEMES_TABLE ="MEMES";
    public static final String COLUMN_MEMES_ASSET="asset";
    public static final String COLUMN_MEMES_NAME="name";
    public static final String COLUMN_MEMES_ID="_id";

    //sql sentence

    public static final String CREATE_TABLE_MEMES="CREATE TABLE "+MEMES_TABLE+ " ( "+
            COLUMN_MEMES_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_MEMES_ASSET + " TEXT NOT NULL, "+
            COLUMN_MEMES_NAME + " TEXT NOT NULL );";

    //annotations functionality
    public static final String ANNOTATIONS_TABLE="ANNOTATIONS";
    public static final String COLUMN_ANNOTATIONS_ID="_id";
    public static final String COLUMN_ANNOTATIONS_TITLE="title";
    public static final String COLUMN_ANNOTATIONS_MEMEID="fk_meme_id";
    public static final String COLUMN_ANNOTATIONS_Y="y";
    public static final String COLUMN_ANNOTATIONS_X="x";
    public static final String COLUMN_ANNOTATIONS_COLOR="color";

    //sql sentence

    public static final String CREATE_TABLE_ANNOTATIONS="CREATE TABLE "+ANNOTATIONS_TABLE+" ( "+
            COLUMN_ANNOTATIONS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_ANNOTATIONS_TITLE+ " TEXT NOT NULL, "+
            COLUMN_ANNOTATIONS_Y+" INTEGER NOT NULL, "+
            COLUMN_ANNOTATIONS_X+" INTEGER NOT NULL, "+
            COLUMN_ANNOTATIONS_COLOR+" INTEGER NOT NULL," +
            COLUMN_ANNOTATIONS_MEMEID+ " INTEGER, " +
            "FOREIGN KEY ("+COLUMN_ANNOTATIONS_MEMEID +") REFERENCES " +MEMES_TABLE+"("+COLUMN_MEMES_ID+"));";


    public MemeSQLiteHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*try {*/
            db.execSQL(CREATE_TABLE_MEMES);
            db.execSQL(CREATE_TABLE_ANNOTATIONS);
            Log.e(TAG, CREATE_TABLE_MEMES);
            Log.e(TAG, CREATE_TABLE_ANNOTATIONS);
        /*}catch (SQLException e){
            Log.e(TAG, "Android SQLException caught"+e);
        }*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //Meme Table functionality

    //Meme Table Annotations functionality

}
