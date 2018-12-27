package com.vigneashsundar.photongaurd.fragment;

import android.content.Context;

import com.vigneashsundar.photongaurd.adapter.updateAdapter;

public interface refreshDatabaseInterface {
    public void refreshdb(refreshDatabaseInterface callback);
    public  void setTaskFlag(Boolean flag);
}
