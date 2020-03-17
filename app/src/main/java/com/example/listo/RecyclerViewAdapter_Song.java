package com.example.listo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter_Song extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> mSongNamesList = new ArrayList<>();
    private ArrayList<String> mTumbnailsList = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter_Song(Context mContext, ArrayList<String> mSongNames, ArrayList<String> mTumbnails) {
        this.mSongNamesList = mSongNames;
        this.mTumbnailsList = mTumbnails;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_song,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(mTumbnailsList.get(position)) //loads from url
                    .into(((ViewHolder) holder).song_IMG_tumbnail);    // into the circle image view

            ((ViewHolder) holder).song_LBL_name.setText(mSongNamesList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mSongNamesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView song_LBL_name;
        private CircleImageView song_IMG_tumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            this.song_LBL_name = itemView.findViewById(R.id.song_LBL_song_name);
            this.song_IMG_tumbnail = itemView.findViewById(R.id.song_IMG_tumbnail);
        }
    }
}
