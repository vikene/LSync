package com.vigneashsundar.photongaurd.VH;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.vigneashsundar.photongaurd.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    public ImageView photoImage;
    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        photoImage = itemView.findViewById(R.id.gridImage);
    }
}
