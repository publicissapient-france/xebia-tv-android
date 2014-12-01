package fr.xebia.tv.ui.player;

import android.os.Bundle;

import fr.xebia.tv.R;
import fr.xebia.tv.model.player.Video;
import fr.xebia.tv.model.youtube.YoutubeVideoDetails;
import fr.xebia.tv.presenter.Presenter;
import fr.xebia.tv.provider.Callback;
import fr.xebia.tv.provider.videos.VideosProvider;

public class VideoPlayerPresenter extends Presenter<VideoPlayerView> {

    Video video;
    VideosProvider videosProvider;

    public VideoPlayerPresenter(Video video, VideosProvider videosProvider, Bundle savedInstanceState) {
        this.video = video;
        this.videosProvider = videosProvider;
    }

    @Override public void onViewTaken() {
        loadVideo();
    }

    public void loadVideo() {
        view.post(new Runnable() {
            @Override public void run() {
                view.cueVideo();
                view.showTitle(video.title);
                loadDescription();
            }
        });
    }

    public String getVideoId() {
        return video.id;
    }

    public void loadDescription() {
        view.showDescriptionProgress();
        videosProvider.provideVideoDetails(video.id, new Callback<YoutubeVideoDetails>() {
            @Override public void onSuccess(YoutubeVideoDetails youtubeVideoDetails) {
                if(view == null){
                    return;
                }
                view.showDescription(youtubeVideoDetails.snippet.description);
            }

            @Override public void onError(Exception e) {
                if(view == null){
                    return;
                }
                view.showDescription(view.getResources().getString(R.string.description_load_error));
            }
        });
    }
}
