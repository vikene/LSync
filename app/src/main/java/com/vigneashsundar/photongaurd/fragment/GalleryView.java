package com.vigneashsundar.photongaurd.fragment;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.vigneashsundar.photongaurd.AsyncTasks.fetchDataTask;
import com.vigneashsundar.photongaurd.Database.photoImageDatabase;
import com.vigneashsundar.photongaurd.MainActivity;
import com.vigneashsundar.photongaurd.R;
import com.vigneashsundar.photongaurd.adapter.photoImageAdapter;
import com.vigneashsundar.photongaurd.adapter.updateAdapter;
import com.vigneashsundar.photongaurd.model.photoImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryView extends Fragment implements refreshDatabaseInterface{
    ScaleGestureDetector mscaleGesture;
    GridLayoutManager currentLayout;
    List<photoImage> mypics;
    photoImageAdapter pia;
    updateAdapter updateAdapterCallback;
    Boolean updateTaskRunning;
    Context Context;
    View root;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mypics = new ArrayList<>();
        pia = new photoImageAdapter(mypics,getContext());
        updateAdapterCallback = pia;
        Context = getContext();
        updateTaskRunning = false;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
        if(root == null){
            final View v = inflater.inflate(R.layout.gallery_view_fragment,parent,false);
            final RecyclerView rv = (RecyclerView)v.findViewById(R.id.rv);
            rv.setHasFixedSize(true);
            final GridLayoutManager gm = new GridLayoutManager(getContext(),3);
            currentLayout = gm;
            final GridLayoutManager gm1 = new GridLayoutManager(getContext(), 4);
            final GridLayoutManager gm2 = new GridLayoutManager(getContext(), 5);
            rv.setLayoutManager(gm);
            rv.setAdapter(pia);


            mscaleGesture = new ScaleGestureDetector(v.getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener(){
                @Override
                public boolean onScale(ScaleGestureDetector scaleGestureDetector){
                    if(scaleGestureDetector.getCurrentSpan() > 175 && scaleGestureDetector.getTimeDelta() > 175){
                        if(scaleGestureDetector.getCurrentSpan() - scaleGestureDetector.getPreviousSpan() < -1){
                            if(currentLayout == gm){
                                currentLayout = gm1;
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(v.getContext(), R.anim.item_shrink_layout);
                                rv.setLayoutAnimation(controller);
                                rv.scheduleLayoutAnimation();
                                rv.setLayoutManager(gm1);
                                return  true;
                            }
                            else if(currentLayout == gm1){
                                currentLayout = gm2;
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(v.getContext(), R.anim.item_shrink_layout);
                                rv.setLayoutAnimation(controller);
                                rv.scheduleLayoutAnimation();
                                rv.setLayoutManager(gm1);
                                rv.setLayoutManager(gm2);
                                return true;
                            }
                        }
                        else if(scaleGestureDetector.getCurrentSpan() - scaleGestureDetector.getPreviousSpan() > 1){
                            if(currentLayout == gm2){
                                currentLayout = gm1;
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(v.getContext(), R.anim.item_expand_layout);
                                rv.setLayoutAnimation(controller);
                                rv.scheduleLayoutAnimation();
                                rv.setLayoutManager(gm1);
                                return true;
                            }else if(currentLayout == gm1){
                                currentLayout = gm;
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(v.getContext(), R.anim.item_expand_layout);
                                rv.setLayoutAnimation(controller);
                                rv.scheduleLayoutAnimation();
                                rv.setLayoutManager(gm);
                                return true;
                            }
                        }

                    }

                    return false;
                }
            });

            rv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    mscaleGesture.onTouchEvent(motionEvent);
                    return false;
                }
            });

            root = v;
        }
        this.refreshdb(this);
                return  root;
    }


    @Override
    public void refreshdb(refreshDatabaseInterface callback) {
        if(!updateTaskRunning){
            new fetchDataTask(Context,updateAdapterCallback,callback).execute();
            updateTaskRunning = true;
        }
        else{
            Log.d("TASKDB", "TASK CALLED BUT NOT EXECUTING");
        }
    }

    @Override
    public void setTaskFlag(Boolean flag) {
        updateTaskRunning = flag;
        Log.d("TASKDB",flag+"");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){

        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

    }

}



