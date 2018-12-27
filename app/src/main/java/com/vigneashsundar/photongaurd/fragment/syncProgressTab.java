package com.vigneashsundar.photongaurd.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vigneashsundar.photongaurd.Database.photoImageDatabase;
import com.vigneashsundar.photongaurd.R;
import com.vigneashsundar.photongaurd.model.syncComputer;

public class syncProgressTab extends Fragment {
    TextView computerSetup;
    ImageView cloudSync;
    photoImageDatabase mphotoDB;
    View root;
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
        if(root == null){
            View v = inflater.inflate(R.layout.sync_progress,parent,false);
            computerSetup = v.findViewById(R.id.computerSetup);
            cloudSync = v.findViewById(R.id.cloudSync);
            computerSetup.setText("Computer Not setup");



            root = v;
        }
        isComputerSetup setup = new isComputerSetup();
        setup.execute("computer");
        return  root;
    }

    public class isComputerSetup extends AsyncTask<String,String,Boolean>{
        @Override
        protected void onPostExecute(Boolean s){
            if(s == true){
                computerSetup.setText("Computer Setup Complete");
                Glide.with(getContext())
                        .load(R.drawable.cloudsync)
                        .into(cloudSync);

            }
            else{
                computerSetup.setText("Computer Not Setup");
                if(isPackageInstalled("com.google.zxing.client.android",getContext().getPackageManager())){
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent,0);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Need to install bar code application")
                            .setMessage("To scan QR code, we need to install zxing bar code client")
                            .setPositiveButton("YEs", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                                    startActivity(marketIntent);
                                }
                            })
                            .setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.show();


                }
            }
            mphotoDB.close();


        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String dbName = "photoimage_db";

            mphotoDB = Room.databaseBuilder(getContext(), photoImageDatabase.class, dbName)
                    .fallbackToDestructiveMigration()
                    .build();
            syncComputer computer = mphotoDB.msyncComputer().getSyncComputer();
            if(computer == null){
                return  false;
            }else{
                return true;
            }
        }
    }

    public class updateSynComputer extends AsyncTask<String,String,Boolean>{
        @Override
        protected void onPostExecute(Boolean s){
            computerSetup.setText("Computer Setup Complete");
        }
        @Override
        protected Boolean doInBackground(String... strings) {
            syncComputer msync = new syncComputer();
            msync.setComputerName(strings[0]);
            msync.setIpAddress(strings[1]);
            msync.setDatasent(0);
            msync.setLastConnected(0);
            mphotoDB.msyncComputer().insertSyncComputer(msync);
            return null;
        }
    }
    private Boolean isPackageInstalled(String packageName, PackageManager packageManager){
        Boolean found = true;
        try{
            packageManager.getPackageInfo(packageName,0);
        }
        catch (PackageManager.NameNotFoundException e){
            found = false;
        }
        return  found;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                String res = data.getStringExtra("SCAN_RESULT");
                updateSynComputer update = new updateSynComputer();
                update.execute("sample", res);
                Log.d("QRREAD", data.getStringExtra("SCAN_RESULT"));
            }
        }

    }
}
