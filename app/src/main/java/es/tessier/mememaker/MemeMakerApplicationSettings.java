package es.tessier.mememaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import es.tessier.mememaker.utils.StorageType;

/**
 * Created by Evan Anger on 8/13/14.
 */
public class MemeMakerApplicationSettings {

    SharedPreferences mSharedPreferences;
    final String KEY_STORAGE = "Storage";
    public MemeMakerApplicationSettings(Context context)
    {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        //mSharedPreferences = context.getSharedPreferences(KEY_STORAGE,Context.Mode_PRIVATE);
    }

    public  String getStoredPreferences(){

        String storage = mSharedPreferences.getString("Storage",StorageType.INTERNAL);


        return storage;
     }

    public void setSharedPreferences(String tipo){

        siguiente punto 3 .5!!!!
        SharedPreferences.Editor editableAppPrefs = mSharedPreferences.edit();
    }
}
