package com.example.listo.Classes;

public class Song {

    private String mVideoID;
    private int mLikes;
    private String mUrl;
    private String tumbnailUrl;
    private String title;

    public Song(String url) {
        this.mLikes = 0;
        this.mUrl = url;
        setVideoID(url);
        setTumbnailUrl();
    }
    public Song() {
        this.mLikes = 0;
        this.mUrl = "Have an id allready";
        this.mVideoID = "";
        setTumbnailUrl();
    }


    public void setVideoID(String url) {
        this.mVideoID = MyFunctions.extractVideoIdFromUrl(url);
    }

    public void setTumbnailUrl() {
        this.tumbnailUrl = MyFunctions.getTumbnailUrlFromVideoId(this.mVideoID);
    }

    public void setLikes(int likes) {
        this.mLikes = likes;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }


    public String getTumbnailUrl() {
        return tumbnailUrl;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getVideoID() {
        return mVideoID;
    }

    public int getLikes() {
        return mLikes;
    }
}
