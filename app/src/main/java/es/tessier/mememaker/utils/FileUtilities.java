package es.tessier.mememaker.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;

/**
 * Created by Evan Anger on 7/28/14.
 */
public class FileUtilities {

    public static File[] getImages(Context context)
    {
        //File fileDirectory = context.getFilesDir(); //se implementa por la linea inferior
        File fileDirectory = getFileDirectory(context);

        final File[] filteredFiles = fileDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {

                if (pathname.getAbsolutePath().contains("jpg")){
                    return true;
                }else
                if (pathname.getAbsolutePath().contains("png")){
                    return true;
                }else
                return false;
            }
        });

        return filteredFiles;
    }

    static String TAG = FileUtilities.class.getName();

    public static boolean isExternalStorageAvailable()
    {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
        {
            return true;
        }
        return false;
    }
    public static File getFileDirectory(Context context)
    {
        String Storage_type="StorageType.INTERNAL";  //guardara las variables StorageType.INTERNAL o StorageType.PUBLIC_EXTERNAL o StorageType.PRIVATE_EXTERNAL
        Storage_type="StorageType.PUBLIC_EXTERNAL";
        if(Storage_type.equalsIgnoreCase("StorageType.INTERNAL"))
        {
            return context.getFilesDir();
        }
        else
        {
            if(isExternalStorageAvailable())
            {
                if(Storage_type.equalsIgnoreCase("StorageType.PRIVATE_EXTERNAL"))
                {
                    //las imagenes no podran ser accesibles desde otras aplicaciones:
                    return context.getExternalFilesDir(null);
                }
                else
                {
                    String album_name="mememaker";
                    File fotospublicas = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),album_name);
                    if(!fotospublicas.mkdirs())
                    {
                        Log.e(TAG,"Directorio no creado");
                    }
                    return fotospublicas;
                }
            }
        }
        return null;
    }

    public static void copyFile(Context context, String assetName)
    {
        // File fileDirectory = context.getFilesDir();    //esto lo modificamos por la linea inferior e implementamos el metodo.
        File fileDirectory = getFileDirectory(context);

        File fileToWrite = new File (fileDirectory, assetName);
        AssetManager assetManager = context.getAssets();
        InputStream in=null;
        OutputStream out=null;
        try{
            in=assetManager.open(assetName);
            /*
            out = context.openFileOutput(
                    fileToWrite.getAbsolutePath(),
                    context.MODE_PRIVATE);
*/
            out = new FileOutputStream(fileToWrite);

            byte[] buffer = new byte[1024];
            int read;

            while((read=in.read(buffer))!=-1)
            {
                out.write(buffer, 0, read);
            }


        }catch (FileNotFoundException e){
            Log.e(TAG, "FileNotFoudException caught ", e);
        } catch (IOException e){
            Log.e(TAG, "IOException caught ", e);
        }finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveAssetImage(Context context, String assetName)
    {
        copyFile(context, assetName);

    }

    public static Uri saveImageForSharing(Context context, Bitmap bitmap,  String assetName) {
        File fileToWrite = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), assetName);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileToWrite);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return Uri.fromFile(fileToWrite);
        }
    }


    public static void saveImage(Context context, Bitmap bitmap, String name) {
        //File fileDirectory = context.getFilesDir(); //se implementa por la linea inferior
        File fileDirectory = getFileDirectory(context);

        File fileToWrite = new File(fileDirectory, name);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileToWrite);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
