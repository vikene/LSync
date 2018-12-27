package com.vigneashsundar.photongaurd.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity
public class photoImage {
    @NonNull
    @PrimaryKey
    public String hashCode;
    public String imageName;
    public String imageURI;
    public String id;
    public String bucketDisplayName;
    public String bucketID;
    public int dateAdded;
    public int dateTaken;
    public int dateModified;
    public String description;
    public String height;
    public String isPrivate;
    public String width;
    public String title;
    public String size;
    public String picasaID;
    public String orientation;
    public String miniThumbMagic;
    public String mimeType;
    public String latitude;
    public String longitude;
    public Boolean fileUploaded;
    public String imageLocation;
    public Boolean inlocal;
    public String fileuploadTime;

    public Boolean getFileUploaded() {
        return fileUploaded;
    }

    public void setFileUploaded(Boolean fileUploaded) {
        this.fileUploaded = fileUploaded;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Boolean getInlocal() {
        return inlocal;
    }

    public void setInlocal(Boolean inlocal) {
        this.inlocal = inlocal;
    }

    public String getFileuploadTime() {
        return fileuploadTime;
    }

    public void setFileuploadTime(String fileuploadTime) {
        this.fileuploadTime = fileuploadTime;
    }



    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public photoImage(){

    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBucketDisplayName() {
        return bucketDisplayName;
    }

    public void setBucketDisplayName(String bucketDisplayName) {
        this.bucketDisplayName = bucketDisplayName;
    }

    public String getBucketID() {
        return bucketID;
    }

    public void setBucketID(String bucketID) {
        this.bucketID = bucketID;
    }

    public int getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(int dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(int dateTaken) {
        this.dateTaken = dateTaken;
    }

    public int getDateModified() {
        return dateModified;
    }

    public void setDateModified(int dateModified) {
        this.dateModified = dateModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPicasaID() {
        return picasaID;
    }

    public void setPicasaID(String picasaID) {
        this.picasaID = picasaID;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getMiniThumbMagic() {
        return miniThumbMagic;
    }

    public void setMiniThumbMagic(String miniThumbMagic) {
        this.miniThumbMagic = miniThumbMagic;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        photoImage that = (photoImage) o;
        return Objects.equals(imageName, that.imageName) &&
                Objects.equals(imageURI, that.imageURI) &&
                Objects.equals(id, that.id) &&
                Objects.equals(bucketDisplayName, that.bucketDisplayName) &&
                Objects.equals(bucketID, that.bucketID) &&
                Objects.equals(dateAdded, that.dateAdded) &&
                Objects.equals(dateTaken, that.dateTaken) &&
                Objects.equals(dateModified, that.dateModified) &&
                Objects.equals(description, that.description) &&
                Objects.equals(height, that.height) &&
                Objects.equals(isPrivate, that.isPrivate) &&
                Objects.equals(width, that.width) &&
                Objects.equals(title, that.title) &&
                Objects.equals(size, that.size) &&
                Objects.equals(picasaID, that.picasaID) &&
                Objects.equals(orientation, that.orientation) &&
                Objects.equals(miniThumbMagic, that.miniThumbMagic) &&
                Objects.equals(mimeType, that.mimeType) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {

        return Objects.hash(imageName, imageURI);
    }
}
