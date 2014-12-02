package fr.xebia.tv.presenter.videos;

import android.os.Bundle;

import java.util.List;

import fr.xebia.tv.bus.PlayVideoEvent;
import fr.xebia.tv.model.youtube.YoutubeVideoItem;
import fr.xebia.tv.presenter.Presenter;
import fr.xebia.tv.provider.Callback;
import fr.xebia.tv.provider.videos.VideosProvider;
import fr.xebia.tv.ui.videos.VideosView;

import static fr.xebia.tv.XebiaTvApplication.BUS;

public class VideosPresenter extends Presenter<VideosView> {

    VideosProvider videosProvider;

    List<YoutubeVideoItem> youtubeVideoItems;

    public VideosPresenter(VideosProvider videosProvider, Bundle savedInstanceState) {
        super(savedInstanceState);
        this.videosProvider = videosProvider;
    }

    @Override public void onViewTaken() {
        showProgress();
        refreshVideos();
    }

    public void refreshVideos() {
        videosProvider.provideVideos(youtubeVideoItemsCallback);
    }

    public void onVideoSelected(int position) {
        YoutubeVideoItem youtubeVideoItem = youtubeVideoItems.get(position);
        BUS.post(new PlayVideoEvent(youtubeVideoItem.id.videoId, youtubeVideoItem.snippet.title));
    }

    public void showProgress() {
        view.showProgress();
    }

    private Callback<List<YoutubeVideoItem>> youtubeVideoItemsCallback = new Callback<List<YoutubeVideoItem>>() {
        @Override public void onSuccess(List<YoutubeVideoItem> youtubeVideoItems) {
            if (view == null) {
                return;
            }
            VideosPresenter.this.youtubeVideoItems = youtubeVideoItems;
            if (youtubeVideoItems.isEmpty()) {
                view.showEmpty();
            } else {
                view.showVideos(youtubeVideoItems);
            }
        }

        @Override public void onError(Exception e) {
            if (view == null) {
                return;
            }
            view.showError();
        }
    };

    public void onRetryLoadVideos() {
        showProgress();
        refreshVideos();
    }
}
