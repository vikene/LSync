package com.vigneashsundar.photongaurd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.vigneashsundar.photongaurd.ImageViewEditor;
import com.vigneashsundar.photongaurd.R;
import com.vigneashsundar.photongaurd.VH.ImageViewHolder;
import com.vigneashsundar.photongaurd.customView.SquaredImageView;
import com.vigneashsundar.photongaurd.model.photoImage;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;


import java.io.File;
import java.util.List;

public class photoImageAdapter extends RecyclerView.Adapter<ImageViewHolder> implements updateAdapter {
    List<photoImage> PhotoArray;
    Context myApp;
    public  photoImageAdapter(List<photoImage> displayPics, Context app){
        this.myApp = app;
        this.PhotoArray = displayPics;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photoitemgrid,viewGroup,false);
        final ImageViewHolder ivh = new ImageViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoImage img =  PhotoArray.get(ivh.getAdapterPosition());
                File file = new File(img.imageURI);
                String path;
                if(file.exists()){
                    path = img.getImageURI();
                }else{
                    path = img.getImageLocation();
                }
                Intent intent = new Intent(myApp,ImageViewEditor.class);
                intent.putExtra("imagePath",path);
                myApp.startActivity(intent);

            }
        });
        return ivh;
    }

    public void updateAdapter(List<photoImage> pics){
        this.PhotoArray.clear();
        this.PhotoArray.addAll(pics);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, int i) {
        photoImage im = PhotoArray.get(i);

        RequestOptions options = new RequestOptions();
        File file = new File(im.imageURI);
        if(file.exists()){
            Glide.with(myApp)
                    .load(im.imageURI)
                    .into(imageViewHolder.photoImage);
        }
        else{
            if(im.fileUploaded != null)
            {
                if(im.getFileUploaded()){
                    im.setInlocal(false);
                    Glide.with(myApp)
                            .load(im.getImageLocation())
                            .into(imageViewHolder.photoImage);
                    Log.d("IMAGEVIEW", im.getImageLocation());
                }

            }

        }



    }

    @Override
    public int getItemCount() {
        return PhotoArray.size();
    }
}
