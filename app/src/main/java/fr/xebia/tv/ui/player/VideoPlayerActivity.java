package fr.xebia.tv.ui.player;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;

import fr.xebia.tv.R;
import fr.xebia.tv.model.player.Video;
import fr.xebia.tv.provider.videos.VideosProvider;

import static fr.xebia.tv.XebiaTvApplication.getXebiaTvApi;

public class VideoPlayerActivity extends YouTubeBaseActivity {

    public static final String EXTRA_VIDEO = "fr.xebia.tv.video";
    public static final int REQUEST_YOUTUBE_ERROR_RECOVERY = 1;

    private VideoPlayerPresenter videoPlayerPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player_view);
        Video video = getIntent().getParcelableExtra(EXTRA_VIDEO);
        videoPlayerPresenter = new VideoPlayerPresenter(video, new VideosProvider(getXebiaTvApi()), savedInstanceState);
        ((VideoPlayerView) findViewById(R.id.video_player_view))
                .setupPresenter(videoPlayerPresenter);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_YOUTUBE_ERROR_RECOVERY){
            if(resultCode != RESULT_OK){
                Toast.makeText(this, R.string.unknow_error, Toast.LENGTH_LONG).show();
                finish();
            } else {
                videoPlayerPresenter.loadVideo();
            }
        }
    }
}
