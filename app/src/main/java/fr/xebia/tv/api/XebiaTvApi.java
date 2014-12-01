package fr.xebia.tv.api;


import java.util.List;

import fr.xebia.tv.model.youtube.YoutubeVideoDetails;
import fr.xebia.tv.model.youtube.YoutubeVideoItem;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface XebiaTvApi {

    @GET("/server/videos/list")
    void getChannelVideos(Callback<List<YoutubeVideoItem>> youtubeChannelCallback);

    @GET("/server/video/{videoId}")
    void getVideoDetails(@Path("videoId") String videoId, Callback<List<YoutubeVideoDetails>> youtubeVideoDetailsCallback);
}
