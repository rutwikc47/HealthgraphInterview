package com.rccorp.steamviewer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rutwik on 16/03/18.
 */

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {


    private Context mContext;

    private List<GameItem> mGame;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, null);
        mContext=view.getContext();
        return new ViewHolder(view);
    }

    public GameAdapter() {
        mGame = new ArrayList<>();
    }

    public void addCardItem(GameItem item) {
        mGame.add(item);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GameItem mItem=mGame.get(position);
        if (mItem.getGameName().length()>0){
            holder.gameName.setText(mItem.getGameName());
            Glide.with(mContext).load(mItem.getGameThumb()).into(holder.gameThumb);
        }

    }

    public GameItem getGameDetails(int position){
        GameItem item=mGame.get(position);
        return item;
    }


    @Override
    public int getItemCount() {
        return mGame.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView gameName;
        public ImageView gameThumb;

        public ViewHolder(View view) {
            super(view);

            gameName=(TextView)view.findViewById(R.id.name_tv);
            gameThumb=(ImageView)view.findViewById(R.id.thumb_imgv);

        }
    }
}
