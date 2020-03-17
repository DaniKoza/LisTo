package com.example.listo;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener ,YouTubePlayer.PlaybackEventListener {

    private static final String TAG = "MainActivity";
    // vars
    private ArrayList<String> mSongNamesList = new ArrayList<>();
    private ArrayList<String> mTumbnailUrlsList = new ArrayList<>();
    private List<String> mSongIDs;
    private RecyclerViewAdapter_Song adapter_song;
    private RecyclerView recyclerView_SongList;

    private YouTubePlayerView mYouTubePlayerView;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mSongIDs = new ArrayList<>();

        mSongIDs.add("7Buz7tJ_XYc");
        mSongIDs.add("7Buz7tJ_XYc");
        mSongIDs.add("5Z0EWqe6cLM");
        mSongIDs.add("GK6w4kpUjUw");
        mSongIDs.add("GK6w4kpUjUw");


        mYouTubePlayerView = findViewById(R.id.youtube_player);

        mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(),this); // initializer

        initImageBitmaps();
        recyclerView_SongList = findViewById(R.id.recycler_view_song_list);
        adapter_song = new RecyclerViewAdapter_Song(this, mSongNamesList, mTumbnailUrlsList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_SongList.setLayoutManager(layoutManager);
        recyclerView_SongList.setAdapter(adapter_song);
    }


    private void initImageBitmaps() {
        mTumbnailUrlsList.add("http://img.youtube.com/vi/z44CLCafepA/mqdefault.jpg");
        mSongNamesList.add("MosheMosheMosheMosheMosheMosheMosheMosheMosheMoshe");

        mTumbnailUrlsList.add("http://img.youtube.com/vi/qmVeQEysvtk/mqdefault.jpg");
        mSongNamesList.add("MosheMoshe2");

        mTumbnailUrlsList.add("http://img.youtube.com/vi/qmVeQEysvtk/0.jpg");
        mSongNamesList.add("MosheMoshe3");

        mTumbnailUrlsList.add("http://img.youtube.com/vi/qmVeQEysvtk/1.jpg");
        mSongNamesList.add("4MosheMoshe");

        mTumbnailUrlsList.add("http://img.youtube.com/vi/qmVeQEysvtk/2.jpg");
        mSongNamesList.add("5MosheMosheMoshe");

        mTumbnailUrlsList.add("http://img.youtube.com/vi/qmVeQEysvtk/3.jpg");
        mSongNamesList.add("5MosheMosheMoshe");

        mTumbnailUrlsList.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mSongNamesList.add("5MosheMosheMoshe");

        mTumbnailUrlsList.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mSongNamesList.add("5MosheMosheMoshe");

        mTumbnailUrlsList.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mSongNamesList.add("5MosheMosheMoshe");

    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {
        Toast.makeText(this,"heyyyyyy",
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setShowFullscreenButton(false);
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        youTubePlayer.loadVideos(mSongIDs);
        youTubePlayer.setPlaybackEventListener(this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d(TAG, "onInitializationFailure: Failed to init!");
    }


    // Method to get title of song easy
//    public static String getTitleQuietly(String youtubeUrl) {
//        try {
//            if (youtubeUrl != null) {
//                URL embededURL = new URL("http://www.youtube.com/oembed?url=" +
//                        youtubeUrl + "&format=json"
//                );
//
//                return new JSONObject(IOUtils.toString(embededURL)).getString("title");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
