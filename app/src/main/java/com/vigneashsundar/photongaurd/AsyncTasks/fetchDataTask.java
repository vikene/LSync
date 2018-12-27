package com.vigneashsundar.photongaurd.AsyncTasks;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.vigneashsundar.photongaurd.Database.photoImageDatabase;
import com.vigneashsundar.photongaurd.adapter.updateAdapter;
import com.vigneashsundar.photongaurd.fragment.refreshDatabaseInterface;
import com.vigneashsundar.photongaurd.model.photoImage;

import java.util.List;

public class fetchDataTask  extends AsyncTask<String,String,List<photoImage>> {
    private photoImageDatabase mphotoDB;
    private updateAdapter updateCallback;
    private refreshDatabaseInterface dbCallback;

    public fetchDataTask(Context mContext, updateAdapter mupdate, refreshDatabaseInterface dbcal){
        String dbName = "photoimage_db";
        mphotoDB = Room.databaseBuilder(mContext, photoImageDatabase.class, dbName)
                .fallbackToDestructiveMigration()
                .build();
        updateCallback = mupdate;
        dbCallback = dbcal;
    }

    @Override
    protected void onPostExecute(List<photoImage> s) {
        //Show the result obtained from doInBackground
        updateCallback.updateAdapter(s);
        dbCallback.setTaskFlag(false);
        mphotoDB.close();

    }

    @Override
    protected List<photoImage> doInBackground(String... lists) {

        return mphotoDB.mphotoDaoAccess().fetchAllPhotos();
    }
}
