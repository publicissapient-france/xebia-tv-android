package fr.xebia.tv.provider.videos;

import java.lang.ref.WeakReference;
import java.util.List;

import fr.xebia.tv.api.XebiaTvApi;
import fr.xebia.tv.model.youtube.YoutubeVideoDetails;
import fr.xebia.tv.model.youtube.YoutubeVideoItem;
import fr.xebia.tv.provider.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static fr.xebia.tv.XebiaTvApplication.getXebiaTvApi;

public class VideosProvider {

    XebiaTvApi xebiaTvApi;

    public VideosProvider(XebiaTvApi xebiaTvApi) {
        this.xebiaTvApi = xebiaTvApi;
    }

    public void provideVideos(Callback<List<YoutubeVideoItem>> youtubeVideoItemsCallback) {
        final WeakReference<Callback<List<YoutubeVideoItem>>> weakCallback = new WeakReference<>(youtubeVideoItemsCallback);

        getXebiaTvApi().getChannelVideos(new retrofit.Callback<List<YoutubeVideoItem>>() {
            @Override public void success(List<YoutubeVideoItem> youtubeVideoItems, Response response) {
                Callback<List<YoutubeVideoItem>> youtubeVideoItemsCallback = weakCallback.get();
                if (youtubeVideoItemsCallback != null) {
                    youtubeVideoItemsCallback.onSuccess(youtubeVideoItems);
                }
            }

            @Override public void failure(RetrofitError error) {
                Callback<List<YoutubeVideoItem>> youtubeVideoItemsCallback = weakCallback.get();
                if (youtubeVideoItemsCallback != null) {
                    youtubeVideoItemsCallback.onError(new Exception("Couldn't load videos !"));
                }
            }
        });
    }

    public void provideVideoDetails(String videoId, Callback<YoutubeVideoDetails> youtubeVideoDetailsCallback) {
        final WeakReference<Callback<YoutubeVideoDetails>> weakCallback = new WeakReference<>(youtubeVideoDetailsCallback);

        getXebiaTvApi().getVideoDetails(videoId, new retrofit.Callback<List<YoutubeVideoDetails>>() {
            @Override public void success(List<YoutubeVideoDetails> youtubeVideoDetailsList, Response response) {
                Callback<YoutubeVideoDetails> youtubeVideoDetailsCallback = weakCallback.get();
                if (youtubeVideoDetailsCallback != null) {
                    youtubeVideoDetailsCallback.onSuccess(youtubeVideoDetailsList.get(0));
                }
            }

            @Override public void failure(RetrofitError error) {
                Callback<YoutubeVideoDetails> youtubeVideoDetailsCallback = weakCallback.get();
                if (youtubeVideoDetailsCallback != null) {
                    youtubeVideoDetailsCallback.onError(new Exception("Couldn't load videos !"));
                }
            }
        });
    }

}
