package fr.xebia.tv.ui.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.xebia.tv.BuildConfig;
import fr.xebia.tv.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION;
import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI;
import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT;
import static com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import static com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import static com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import static com.google.android.youtube.player.YouTubePlayer.Provider;

public class VideoPlayerView extends LinearLayout implements OnInitializedListener, OnFullscreenListener {

    @InjectView(R.id.video_player) YouTubePlayerView youTubePlayerView;
    @InjectView(R.id.video_progress) ProgressBar videoProgress;
    @InjectView(R.id.details_container) ViewGroup detailsContainer;
    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.description) TextView description;
    @InjectView(R.id.description_progress) ProgressBar descriptionProgress;

    VideoPlayerPresenter presenter;

    YouTubePlayer youTubePlayer;
    boolean fullscreen;
    boolean youtubeInitializationFailed;

    public VideoPlayerView(Context context) {
        super(context);
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setupPresenter(VideoPlayerPresenter videoPlayerPresenter) {
        this.presenter = videoPlayerPresenter;
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override protected void onDetachedFromWindow() {
        presenter.dropView();
        super.onDetachedFromWindow();
    }

    public void cueVideo() {
        if (youTubePlayer == null) {
            youTubePlayerView.initialize(BuildConfig.YOUTUBE_ANDROID_API_KEY, this);
        } else if (youtubeInitializationFailed) {
            doCueVideo();
        }
    }

    private void doCueVideo() {
        videoProgress.setVisibility(VISIBLE);
        youTubePlayer.cueVideo(presenter.getVideoId());
    }

    public void showTitle(String videoTitle) {
        title.setText(videoTitle);
    }

    public void showDescription(String videoDescription) {
        descriptionProgress.setVisibility(GONE);
        description.setText(videoDescription);
        description.setVisibility(VISIBLE);
    }

    public void showDescriptionProgress() {
        descriptionProgress.setVisibility(VISIBLE);
        description.setVisibility(GONE);
    }

    @OnClick(R.id.video_player) public void onVideoPlayerClicked() {
        if (youtubeInitializationFailed) {
            presenter.loadDescription();
            doCueVideo();
        }
    }


    @Override public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        this.youTubePlayer = youTubePlayer;
        youTubePlayer.setOnFullscreenListener(this);
        youTubePlayer.addFullscreenControlFlag(FULLSCREEN_FLAG_CUSTOM_LAYOUT | FULLSCREEN_FLAG_CONTROL_ORIENTATION | FULLSCREEN_FLAG_CONTROL_SYSTEM_UI | FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
        if (!wasRestored) {
            doCueVideo();
            youTubePlayer.setPlayerStateChangeListener(playerStateChangedListener);
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        videoProgress.setVisibility(GONE);
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(((Activity) getContext()), VideoPlayerActivity.REQUEST_YOUTUBE_ERROR_RECOVERY);
        } else {
            Toast.makeText(getContext(), R.string.unknow_error, Toast.LENGTH_LONG).show();
            ((Activity) getContext()).finish();
        }
    }

    private void doLayout() {
        FrameLayout.LayoutParams playerParams = (FrameLayout.LayoutParams) youTubePlayerView.getLayoutParams();
        if (fullscreen) {
            // When in fullscreen, the visibility of all other views than the player should be set to
            // GONE and the player should be laid out across the whole screen.
            playerParams.width = LayoutParams.MATCH_PARENT;
            playerParams.height = LayoutParams.MATCH_PARENT;

            detailsContainer.setVisibility(View.GONE);
        } else {
            // This layout is up to you - this is just a simple example (vertically stacked boxes in
            // portrait, horizontally stacked in landscape).
            detailsContainer.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams otherViewsParams = detailsContainer.getLayoutParams();
            playerParams.width = otherViewsParams.width = MATCH_PARENT;
            playerParams.height = WRAP_CONTENT;
            otherViewsParams.height = MATCH_PARENT;
        }
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        fullscreen = isFullscreen;
        doLayout();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        doLayout();
    }

    PlayerStateChangeListener playerStateChangedListener = new PlayerStateChangeListener() {
        @Override public void onLoading() {

        }

        @Override public void onLoaded(String s) {
            youtubeInitializationFailed = false;
            videoProgress.setVisibility(GONE);
        }

        @Override public void onAdStarted() {

        }

        @Override public void onVideoStarted() {

        }

        @Override public void onVideoEnded() {

        }

        @Override public void onError(ErrorReason errorReason) {
            youtubeInitializationFailed = true;
            videoProgress.setVisibility(GONE);
        }
    };

}
