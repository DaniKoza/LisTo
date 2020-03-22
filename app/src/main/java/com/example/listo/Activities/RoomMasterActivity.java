package com.example.listo.Activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.listo.Classes.Song;
import com.example.listo.R;
import com.example.listo.Classes.RecyclerViewAdapter_Song;
import com.example.listo.Classes.YouTubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RoomMasterActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener {

    private static final String TAG = "MainActivity - Room Master";
    // vars
    private ArrayList<Song> mSongs;
    private RecyclerViewAdapter_Song adapter_song;
    private RecyclerView recyclerView_SongList;
    private YouTubePlayerView mYouTubePlayerView;
    private EditText editText_entered_url;
    private Button button_add, button_clear;
    private DatabaseReference mDatabaseRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mSongs = new ArrayList<Song>();
        button_add.setOnClickListener(mClickAddListener);
        button_clear.setOnClickListener(mClickClearListener);

        mDatabaseRoom = FirebaseDatabase.getInstance().getReference();

        //init youtube player
        mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), RoomMasterActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter_song = new RecyclerViewAdapter_Song(this, mSongs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_SongList.setLayoutManager(layoutManager);
        recyclerView_SongList.setAdapter(adapter_song);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initViews() {
        mYouTubePlayerView = findViewById(R.id.youtube_player);
        recyclerView_SongList = findViewById(R.id.recycler_view_song_list);
        button_add = findViewById(R.id.button_add);
        button_clear = findViewById(R.id.button_clear);
        editText_entered_url = findViewById(R.id.editText_entered_url);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            editText_entered_url.setShowSoftInputOnFocus(false);
        } else { // API 11-20
            editText_entered_url.setTextIsSelectable(true);
        }
    }

    // Clear action
    private View.OnClickListener mClickClearListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editText_entered_url.getText().clear();
        }
    };

    // Add action
    private View.OnClickListener mClickAddListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (editText_entered_url.getText().toString().isEmpty()) {
                editText_entered_url.setError("You have to enter a url :)");
                editText_entered_url.requestFocus();
                return;
            }

            mDatabaseRoom.push().setValue((mSongs.get(mSongs.size() - 1)).getVideoID());
            adapter_song.notifyItemInserted(mSongs.size() - 1);
            editText_entered_url.getText().clear();
            return;
        }
    };


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setShowFullscreenButton(false);
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        youTubePlayer.loadVideo("");
        youTubePlayer.setPlaybackEventListener(this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d("YoutubeAPI: ", "onInitializationFailure: Failed to init!");
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {
        Toast.makeText(this, "heyyyyyy",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopped() {
    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }


}
