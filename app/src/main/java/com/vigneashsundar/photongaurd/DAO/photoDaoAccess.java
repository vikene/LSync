package com.vigneashsundar.photongaurd.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.vigneashsundar.photongaurd.model.photoImage;

import java.util.List;

@Dao
public interface photoDaoAccess {
    @Insert
    void insertSingleImage(photoImage mphotoImage);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStatus(photoImage mphotoImage);
    @Insert
    void insertMultipleImages(List<photoImage> mphotoImagesList);
    @Query("SELECT * FROM photoImage ORDER BY dateModified DESC")
    List<photoImage> fetchAllPhotos();
    @Query("SELECT * FROM photoImage WHERE hashCode = :hashCode ")
    photoImage findThisExist(String hashCode);
    @Query("SELECT * FROM photoImage WHERE fileUploaded = :status ORDER BY dateModified DESC")
    List<photoImage> findFirstUnUploaded(Boolean status);
    @Query("UPDATE photoImage set fileUploaded= :status, imageLocation = :location where hashCode= :hashcode")
    void updateQuery(Boolean status, String hashcode,String location);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateUploadStatus(photoImage mphotoImage);


}
