package com.vigneashsundar.photongaurd;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.net.URI;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class ImageViewEditor extends AppCompatActivity {
    float rotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_editor);
        String imagePath = getIntent().getStringExtra("imagePath");
        GPUImage gpuImage = new GPUImage(this);
        gpuImage.setImage(Uri.parse(imagePath));
        final ImageView photoEditor = findViewById(R.id.gpuImageView);
        ImageButton rotateLeft = (ImageButton)findViewById(R.id.rotateLeft);
        ImageButton rotateRight = (ImageButton)findViewById(R.id.rotateRight);
        rotation = 0;
        rotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotation -= 90;
                photoEditor.setRotation(rotation);
            }
        });
        rotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotation += 90;
                photoEditor.setRotation(rotation);

            }
        });

        try {
            Glide.with(getApplicationContext()).load(imagePath).into(photoEditor);


        }
        catch (Exception e){
            Log.d("ERROR","LOAD ERROR");
        }

    }

    public void rotateFunctions(){

    }
}
