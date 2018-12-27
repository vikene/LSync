package com.vigneashsundar.photongaurd;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.vigneashsundar.photongaurd.AsyncTasks.databaseAsyncTask;
import com.vigneashsundar.photongaurd.Database.photoImageDatabase;
import com.vigneashsundar.photongaurd.adapter.cheeseAdapter;
import com.vigneashsundar.photongaurd.adapter.photoImageAdapter;
import com.vigneashsundar.photongaurd.fragment.GalleryView;
import com.vigneashsundar.photongaurd.fragment.refreshDatabaseInterface;
import com.vigneashsundar.photongaurd.fragment.syncProgressTab;
import com.vigneashsundar.photongaurd.model.cheese;
import com.vigneashsundar.photongaurd.model.photoImage;
import com.vigneashsundar.photongaurd.model.syncComputer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final int READ_WRITE_STORAGE = 5;
    String currentView;
    FragmentManager fm;
    photoImageDatabase mphotoDB;
    Boolean firstTime;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    GalleryView gv;
    syncProgressTab st;
    refreshDatabaseInterface galleryRefreshCallback;
    int CurrentDirection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String dbName = "photoimage_db";
        firstTime = false;
        mphotoDB = Room.databaseBuilder(getApplicationContext(), photoImageDatabase.class, dbName)
                .fallbackToDestructiveMigration()
                .build();
        currentView="Gallery";
        fm = getSupportFragmentManager();
        CurrentDirection = 1;
        gv = (GalleryView) fm.findFragmentByTag("gallery");
        if(gv == null){
            gv = new GalleryView();
            galleryRefreshCallback = gv;
        }
        if(st == null){
            st = new syncProgressTab();
        }

        final AHBottomNavigation bottomNavigationView = findViewById(R.id.bottomPanel);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Gallery", R.drawable.ic_image_white_24dp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Computer", R.drawable.ic_computer_white_24dp);
        bottomNavigationView.addItem(item1);
        bottomNavigationView.addItem(item2);
        bottomNavigationView.setCurrentItem(0);
        bottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                if(position == 0){
                        //GalleryView firstTab = new GalleryView();
                        FragmentTransaction fmt = fm.beginTransaction();
                        if(CurrentDirection == 0){
                            fmt.setCustomAnimations(R.anim.movein_right,R.anim.moveout_right);
                        }
                        fmt.replace(R.id.tabPager,gv);
                        currentView = "Gallery";
                        CurrentDirection = 1;
                        fmt.commit();

                }
                else if(position == 1){
                        FragmentTransaction fmt = fm.beginTransaction();
                        if(CurrentDirection == 1){
                            fmt.setCustomAnimations(R.anim.movein_left,R.anim.moveout_left);
                        }
                        fmt.replace(R.id.tabPager,st);
                        currentView = "sync";
                        CurrentDirection = 0;
                        fmt.commit();
                    }
            }
        });
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            FragmentTransaction fmt = fm.beginTransaction();
            fmt.replace(R.id.tabPager,gv);
            fmt.commit();
            this.uploadAsync();
            this.dataBaseThread();

        }
        else
        {
            firstTime = true;
            requestPermission();
        }


    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    public void requestPermission(){
        String [] Permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ActivityCompat.requestPermissions(this,Permissions ,READ_WRITE_STORAGE);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults){
        switch (requestCode){
            case READ_WRITE_STORAGE:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission GRANTED LOAD our fragment
                    fm = getSupportFragmentManager();
                    //GalleryView gv = new GalleryView();
                    if(gv == null)
                    {
                        gv = new GalleryView();
                    }
                    FragmentTransaction fmt = fm.beginTransaction();
                    fmt.replace(R.id.tabPager,gv);
                    fmt.commit();
                    this.dataBaseThread();
                    this.uploadAsync();
                }
                else{
                    //DO Nothing
                }
            }

        }

    }


    public void uploadAsync(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    builder = new NotificationCompat.Builder(getApplicationContext(), "UPLOAD");
                    builder.setContentTitle("Photon Guard")
                            .setContentText("Upload in progress")
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setPriority(NotificationCompat.PRIORITY_MAX);
                    int PROGRESS_MAX = 100;
                    int PROGRESS_CURRENT = 0;
                    builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, true);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        NotificationChannel channel = new NotificationChannel("UPLOAD","UPLOAD PHOTON SECURE",NotificationManager.IMPORTANCE_HIGH);
                        notificationManager.createNotificationChannel(channel);
                        builder.setChannelId("UPLOAD");
                    }
                    notificationManager.notify(56, builder.build());
                    if(firstTime){
                        Thread.sleep(10000);
                        //GIVE it a better load time
                    }
                    Thread.sleep(5000);

                }
                catch (InterruptedException e){
                    Log.d("Interupt", "EXCEPTION");
                }
                int MAX_COUNT = 0;
                int NOW_COUNT = 0;
                List<photoImage> temp = mphotoDB.mphotoDaoAccess().findFirstUnUploaded(false);
                List<photoImage> temp2 = mphotoDB.mphotoDaoAccess().findFirstUnUploaded(true);
                NOW_COUNT = temp2.size();
                temp2.clear();
                syncComputer syncComputer = mphotoDB.msyncComputer().getSyncComputer();
                MAX_COUNT = NOW_COUNT+temp.size();
                builder.setProgress(MAX_COUNT,NOW_COUNT,false);
                notificationManager.notify(56,builder.build());
                if(syncComputer == null)
                {

                }
                else{
                    Boolean failed = false;
                    for(int i=0;i<temp.size();i++){
                        uploadToserver uts = new uploadToserver();
                        try{
                            Boolean result = uts.execute(temp.get(i).getImageURI(),temp.get(i).getHashCode(),syncComputer.getIpAddress()).get();
                            if(result){
                                builder.setProgress(MAX_COUNT,NOW_COUNT+i,false);
                                notificationManager.notify(56, builder.build());
                            }
                            else {
                                failed = true;
                            }
                        }
                        catch (Exception e){
                            Log.d("UPLOADFAILED", "UPLOAD ERROR");

                        }
                    }
                    if(!failed){
                        builder.setContentText("All photos securely backed up!");
                        builder.setProgress(0,0,false); //Removes the bar from notification
                        notificationManager.notify(56, builder.build());
                    }

                }
            }
        }).start();
    }

    public class uploadToserver extends AsyncTask<String,String,Boolean>{
        String uri;
        String hash;
        String url;
        @Override
        protected Boolean doInBackground(String... strings) {
            uri = strings[0];
            hash = strings[1];
            url = strings[2];
            try{
                JsonObject result = Ion.with(getApplicationContext())
                        .load(url+"/upload")
                        .setMultipartFile("image","image/jpeg",new File(uri))
                        .asJsonObject()
                        .get();

                String res = result.get("message").toString();
                if(res.equals("\"ok\"")){
                    String imageLocation = result.get("url").toString();
                    mphotoDB.mphotoDaoAccess().updateQuery(true,hash,url+imageLocation.split("\"")[1]);
                    return  true;
                }
                return false;
            }
            catch (Exception e){
                builder.setProgress(0,0,false);
                builder.setContentText("Trouble finding the server! Connected on Same wifi network?");
                notificationManager.notify(56, builder.build());
                Log.d("NETWORK EXCEPTION", e.getStackTrace().toString());
                return false;
            }

        }
    }

    public void dataBaseThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if(!firstTime){
                        Thread.sleep(3000);
                    }

                }
                catch (Exception e){
                    Log.d("DBTHREAD", "DELAY START ERROR");
                }

                databaseAsyncTask runner= new databaseAsyncTask(getApplicationContext(), mphotoDB,gv);
                runner.execute();
            }
        }).start();
    }


}
