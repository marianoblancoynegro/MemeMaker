package es.tessier.mememaker.database;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.tessier.mememaker.MemeMakerApplication;
import es.tessier.mememaker.models.Meme;
import es.tessier.mememaker.models.MemeAnnotation;

/**
 * Created by Evan Anger on 8/17/14.
 */
public class MemeDatasource
{

    private Context mContext;
    private MemeSQLiteHelper mMemeSqlLiteHelper;
    /*
        public MemeDatasource(Context context) {

            mContext = context;
            mMemeSqlLiteHelper = new MemeSQLiteHelper(mContext);

            SQLiteDatabase database = mMemeSqlLiteHelper.getReadableDatabase();
        }
    */
    public SQLiteDatabase openWritable(){
        return mMemeSqlLiteHelper.getWritableDatabase();
    }

    public SQLiteDatabase openReadable(){
        return mMemeSqlLiteHelper.getReadableDatabase();
    }

    public List<Meme> read(){



    }

    public List<Meme> readMemes(){
        SQLiteDatabase database = openReadable();

        Cursor cursor= database.query(MemeSQLiteHelper.MEMES_TABLE,
                new String[]{MemeSQLiteHelper.COLUMN_MEMES_NAME, BaseColumns._ID, MemeSQLiteHelper.COLUMN_MEMES_ASSET}
                , null, null, null, null, null);

        ArrayList<Meme> array = new ArrayList<Meme>();

        if (cursor.moveToFirst()){
            do{
                Meme m = new Meme(getIntFromColumnName(cursor, MemeSQLiteHelper.COLUMN_MEMES_ID),
                                   getStringFromColumnName(cursor, MemeSQLiteHelper.COLUMN_MEMES_NAME),
                                    getStringFromColumnName(cursor, MemeSQLiteHelper.COLUMN_MEMES_ASSET),
                                     null);
                array.add(m);

            }while (cursor.moveToNext()==false);
        }else{
            Log.e("Cursor", "Cursor vacío");
        }

        cursor.close();
        return array;
    }

    public void addMemeAnnotations(ArrayList<Meme> memes){
        SQLiteDatabase database = openReadable();

        for (Meme i: memes)
        {
          ArrayList<MemeAnnotation> anots = new ArrayList<MemeAnnotation>();
          Cursor c=database.rawQuery("select * from"+MemeSQLiteHelper.COLUMN_ANNOTATIONS_MEMEID+
                  "="+i.getId(), null);
            7 sqlite, documento 5

        }

    }

    private int getIntFromColumnName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getStringFromColumnName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }


    public void closeDatabase(SQLiteDatabase db){
        db.close();
    }

    public void create(Meme meme){

        SQLiteDatabase database = openWritable();
        database.beginTransaction();

        ContentValues memeValues = new ContentValues();

        memeValues.put(MemeSQLiteHelper.COLUMN_MEMES_NAME, meme.getName());
        memeValues.put(MemeSQLiteHelper.COLUMN_MEMES_ASSET, meme.getAssetLocation());
        Long memeID;
        memeID=database.insert(MemeSQLiteHelper.MEMES_TABLE, null, memeValues);

        ArrayList<MemeAnnotation> annot = new ArrayList<MemeAnnotation>();
        annot=meme.getAnnotations();

        for (MemeAnnotation anEach : annot){
            ContentValues annotationValues = new ContentValues();
            annotationValues.put(MemeSQLiteHelper.COLUMN_ANNOTATIONS_MEMEID, memeID);
            annotationValues.put(MemeSQLiteHelper.COLUMN_ANNOTATIONS_COLOR, anEach.getColor());
            annotationValues.put(MemeSQLiteHelper.COLUMN_ANNOTATIONS_X, anEach.getLocationX());
            annotationValues.put(MemeSQLiteHelper.COLUMN_ANNOTATIONS_Y, anEach.getLocationY());
            annotationValues.put(MemeSQLiteHelper.COLUMN_ANNOTATIONS_TITLE, anEach.getTitle());
            database.insert(MemeSQLiteHelper.ANNOTATIONS_TABLE, null, annotationValues);
        }

        database.setTransactionSuccessful();
        database.endTransaction();
        closeDatabase(database);
    }


}
