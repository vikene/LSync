package com.vigneashsundar.photongaurd.VH;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vigneashsundar.photongaurd.R;

public class cheeseViewHolder extends RecyclerView.ViewHolder {
    public TextView myImage;
    public cheeseViewHolder(@NonNull View itemView) {
        super(itemView);
        myImage = (TextView) itemView.findViewById(R.id.imV);
    }
}
