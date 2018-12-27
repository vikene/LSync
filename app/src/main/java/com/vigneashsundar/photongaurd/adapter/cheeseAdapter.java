package com.vigneashsundar.photongaurd.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vigneashsundar.photongaurd.R;
import com.vigneashsundar.photongaurd.VH.cheeseViewHolder;
import com.vigneashsundar.photongaurd.model.cheese;

import java.util.List;

public class cheeseAdapter extends RecyclerView.Adapter<cheeseViewHolder> {
    List<cheese> myCheese;
    public cheeseAdapter(List<cheese> chee){
        this.myCheese = chee;
    }
    @NonNull
    @Override
    public cheeseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        cheeseViewHolder vh = new cheeseViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull cheeseViewHolder cheeseViewHolder, int i) {
        cheeseViewHolder.myImage.setText(myCheese.get(i).name);

    }

    @Override
    public int getItemCount() {
        return this.myCheese.size();
    }
    @Override
    public  void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }
}
