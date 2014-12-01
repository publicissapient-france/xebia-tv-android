package fr.xebia.tv.ui.videos;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.etsy.android.grid.StaggeredGridView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import fr.xebia.tv.R;
import fr.xebia.tv.model.youtube.YoutubeVideoItem;
import fr.xebia.tv.presenter.videos.VideosPresenter;

public class VideosView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.swipeable_container) SwipeRefreshLayout swipeableContainer;
    @InjectView(R.id.videos_list_grid) StaggeredGridView gridView;

    VideosAdapter adapter;
    VideosPresenter presenter;

    public VideosView(Context context) {
        super(context);
    }

    public VideosView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setupPresenter(VideosPresenter videosPresenter) {
        this.presenter = videosPresenter;
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        gridView.setColumnCountPortrait(1);
        gridView.setColumnCountLandscape(2);
        swipeableContainer.setColorSchemeColors(getResources().getColor(R.color.accent));
        swipeableContainer.setOnRefreshListener(this);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(VideosView.this);
    }

    @Override protected void onDetachedFromWindow() {
        presenter.dropView();
        super.onDetachedFromWindow();
    }

    @OnItemClick(R.id.videos_list_grid) public void onVideoSelected(int position) {
        presenter.onVideoSelected(position);
    }


    public void showVideos(List<YoutubeVideoItem> youtubeVideoItems) {
        adapter = new VideosAdapter(getContext(), youtubeVideoItems);
        gridView.setAdapter(adapter);
        gridView.setVisibility(VISIBLE);
        swipeableContainer.setRefreshing(false);
    }


    public void showProgress() {
        gridView.setVisibility(GONE);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                swipeableContainer.setRefreshing(true);
            }
        });
    }

    public void showEmpty() {

    }

    public void showError() {

    }

    @Override public void onRefresh() {
        presenter.refreshVideos();
    }
}
