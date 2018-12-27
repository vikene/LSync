package com.vigneashsundar.photongaurd.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity
public class syncComputer {
    @NonNull
    @PrimaryKey
    public String ipAddress;
    public String computerName;
    public int lastConnected;
    public int datasent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        syncComputer that = (syncComputer) o;
        return Objects.equals(ipAddress, that.ipAddress) &&
                Objects.equals(computerName, that.computerName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ipAddress, computerName);
    }

    @NonNull
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(@NonNull String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public int getLastConnected() {
        return lastConnected;
    }

    public void setLastConnected(int lastConnected) {
        this.lastConnected = lastConnected;
    }

    public int getDatasent() {
        return datasent;
    }

    public void setDatasent(int datasent) {
        this.datasent = datasent;
    }
}
