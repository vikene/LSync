package com.vigneashsundar.photongaurd.AsyncTasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.vigneashsundar.photongaurd.Database.photoImageDatabase;
import com.vigneashsundar.photongaurd.fragment.GalleryView;
import com.vigneashsundar.photongaurd.fragment.refreshDatabaseInterface;
import com.vigneashsundar.photongaurd.model.photoImage;

import java.util.List;

public class databaseAsyncTask extends AsyncTask<List<photoImage>,Integer,Boolean> {

    private Context mContext;
    private photoImageDatabase mphotoDB;
    private GalleryView callback;
    private refreshDatabaseInterface ccb;

    public  databaseAsyncTask(Context appContext,photoImageDatabase pdb, GalleryView cb){
        mContext = appContext;
        mphotoDB = pdb;
        callback = cb;
        ccb = callback;
    }
    @Override
    protected void onPostExecute(Boolean s) {
        //Show the result obtained from doInBackground
        if(s){
            Log.d("Database","Data base is dirty");
            //GalleryView.fetchDataTask runner= new GalleryView.fetchDataTask();
            //runner.execute(mypics);
            ccb.refreshdb(callback);
        }
    }
    @Override
    protected void onProgressUpdate(Integer... s){
            super.onProgressUpdate();
            if( s[0] % 100 == 0){
                ccb.refreshdb(callback);
            }

    }
    @Override
    protected Boolean doInBackground(List<photoImage>... lists) {
        Boolean dirtyState = false;
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        final String[] columns = {MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.DATE_ADDED,MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.DESCRIPTION,
                MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.HEIGHT,
                MediaStore.Images.Media.IS_PRIVATE, MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.TITLE,MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.PICASA_ID, MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Media.MINI_THUMB_MAGIC, MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.LATITUDE, MediaStore.Images.Media.LONGITUDE};
        final String orderby = MediaStore.Images.Media.DATE_ADDED;
        Cursor cursor =mContext.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,columns,null,null,MediaStore.Images.Media.DATE_ADDED + " DESC"
        );
        try {


            int count = cursor.getCount();
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                photoImage temp = new photoImage();
                temp.setImageURI(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                temp.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
                temp.setBucketDisplayName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
                temp.setBucketID(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)));
                temp.setDateAdded(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)));
                temp.setDateModified(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)));
                temp.setDateTaken(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)));
                temp.setDescription(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION)));
                temp.setImageName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                temp.setHeight(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT)));
                temp.setIsPrivate(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.IS_PRIVATE)));
                temp.setWidth(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH)));
                temp.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE)));
                temp.setSize(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.SIZE)));
                temp.setPicasaID(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.PICASA_ID)));
                temp.setOrientation(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION)));
                temp.setMiniThumbMagic(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MINI_THUMB_MAGIC)));
                temp.setMimeType(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)));
                temp.setLatitude(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LATITUDE)));
                temp.setLongitude(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE)));
                temp.setFileUploaded(false);
                temp.setInlocal(true);
                temp.setImageLocation("");
                temp.setFileuploadTime("");
                temp.setHashCode(temp.hashCode()+"");
                if(mphotoDB.mphotoDaoAccess().findThisExist(temp.getHashCode()) == null){
                    mphotoDB.mphotoDaoAccess().insertSingleImage(temp);
                    dirtyState = true;
                }
                if(i>0 && i % 100 ==0){
                    publishProgress(i);
                }
            }
            cursor.close();


        }catch (NullPointerException e){
            Log.d("Exception","READING IMAGES");
        }
        mphotoDB.close();
        return dirtyState;
    }
}
