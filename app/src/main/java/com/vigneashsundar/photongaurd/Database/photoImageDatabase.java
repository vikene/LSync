package com.vigneashsundar.photongaurd.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.vigneashsundar.photongaurd.DAO.photoDaoAccess;
import com.vigneashsundar.photongaurd.DAO.syncComputerDAO;
import com.vigneashsundar.photongaurd.model.photoImage;
import com.vigneashsundar.photongaurd.model.syncComputer;

@Database(entities = {photoImage.class,syncComputer.class},version = 3, exportSchema = false)
public abstract class photoImageDatabase extends RoomDatabase {
    public abstract photoDaoAccess mphotoDaoAccess();
    public abstract syncComputerDAO msyncComputer();
}
