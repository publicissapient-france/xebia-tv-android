package fr.xebia.tv.ui.videos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.xebia.tv.R;
import fr.xebia.tv.presenter.videos.VideosPresenter;
import fr.xebia.tv.provider.videos.VideosProvider;

import static fr.xebia.tv.XebiaTvApplication.getXebiaTvApi;

public class VideosFragment extends Fragment {

    private VideosView videosView;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        videosView = (VideosView) inflater.inflate(R.layout.videos_view, container, false);
        videosView.setupPresenter(new VideosPresenter(new VideosProvider(getXebiaTvApi()), savedInstanceState));
        return videosView;
    }

    @Override public void onDestroyView() {
        videosView = null;
        super.onDestroyView();
    }
}
