package com.vigneashsundar.photongaurd.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.vigneashsundar.photongaurd.model.syncComputer;

@Dao
public interface syncComputerDAO {
    @Insert
    void insertSyncComputer(syncComputer msyncComputer);
    @Query("SELECT * FROM syncComputer LIMIT 1")
    syncComputer getSyncComputer();
    @Delete
    void deleteSyncComputer(syncComputer msyncComputer);
}
