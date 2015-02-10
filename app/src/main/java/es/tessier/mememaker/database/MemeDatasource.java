package es.tessier.mememaker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

    public MemeDatasource(Context context) {

        mContext = context;
        mMemeSqlLiteHelper = new MemeSQLiteHelper(mContext);

        SQLiteDatabase database = mMemeSqlLiteHelper.getReadableDatabase();
    }

    public SQLiteDatabase openWritable(){
        return mMemeSqlLiteHelper.getWritableDatabase();
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
