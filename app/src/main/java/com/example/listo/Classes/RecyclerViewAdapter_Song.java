package com.example.listo.Classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.listo.Activities.RoomMasterActivity;
import com.example.listo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter_Song extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Song> mSongs;
    private RequestQueue mRequestQueue;
    public static String title = "Error";

    public RecyclerViewAdapter_Song(Context mContext, ArrayList<Song> songs) {
        this.mContext = mContext;
        this.mSongs = songs;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_song,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        getTitleWithVideoId(mSongs.get(position).getVideoID());
            if (holder instanceof ViewHolder) {
                Glide.with(mContext)
                        .asBitmap()
                        .load(mSongs.get(position).getTumbnailUrl()) //loads from url
                        .into(((ViewHolder) holder).imageView_tumbnail);    // into the circle image view

                ((ViewHolder) holder).textView_SongName.setText(title);
                Log.d("ttt", "onBindViewHolder: " + title);

                ((ViewHolder) holder).cardView_song.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        removeAt(position);
                        return false;
                    }
                });
            }
    }

    public void removeAt(int position) {
        mSongs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mSongs.size());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_SongName;
        private CircleImageView imageView_tumbnail;
        private CardView cardView_song;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView_SongName = itemView.findViewById(R.id.textView_SongName);
            this.imageView_tumbnail = itemView.findViewById(R.id.imageView_tumbnail);
            this.cardView_song = itemView.findViewById(R.id.cardView_song);
        }
    }

    // Method that gets song title, cannot be static. later can be added in an async task maybe...
    private void getTitleWithVideoId(String videoId) {
        String url = "https://www.googleapis.com/youtube/v3/videos?id=" + videoId + "&key=" +
                YouTubeConfig.getApiKey() + "&part=snippet";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");
                            JSONObject object = jsonArray.getJSONObject(0);
                            JSONObject snippet = object.getJSONObject("snippet");
                            title = snippet.getString("title");
                            Log.d("ttd", title);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("Error.Response", "Somthing went wrong");
                    }
                }
        );
        // add it to the RequestQueue
        mRequestQueue.add(getRequest);

    }
}
