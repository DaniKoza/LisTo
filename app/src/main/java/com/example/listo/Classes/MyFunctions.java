package com.example.listo.Classes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;

public class MyFunctions {

    /* Parse videosID from URL */
    private static final String YOU_TUBE_URL_REG_EX = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
    private static final String[] VIDEO_ID_REGEX = {"\\?vi?=([^&]*)", "watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};
    private static final String IMG_YOUTUBE_COM_VI = "http://img.youtube.com/vi/";

    // Get video id from a url
    public static String extractVideoIdFromUrl(String url) {
        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);

        for (String regex : VIDEO_ID_REGEX) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);

            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    // Simplify given url
    private static String youTubeLinkWithoutProtocolAndDomain(String url) {
        Pattern compiledPattern = Pattern.compile(YOU_TUBE_URL_REG_EX);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return url.replace(matcher.group(), "");
        }
        return url;
    }

    // Getting the default tumbnail url with video id
    public static String getTumbnailUrlFromVideoId(@NonNull String videoId) {
        return IMG_YOUTUBE_COM_VI + videoId + "/default.jpg";
    }

    // Return list with video id's - easier to load on youtube player
    public static ArrayList<String> generateIdList(ArrayList<Song> mSongs) {
        ArrayList<String> idList = new ArrayList<String>();
        for (int i = 0 ; i < mSongs.size(); i++) {
            idList.add(mSongs.get(i).getVideoID());
        }
        return idList;
    }
}

