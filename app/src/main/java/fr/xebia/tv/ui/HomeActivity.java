package fr.xebia.tv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.crashlytics.android.Crashlytics;
import fr.xebia.tv.R;
import fr.xebia.tv.bus.PlayVideoEvent;
import fr.xebia.tv.model.player.Video;
import fr.xebia.tv.ui.player.VideoPlayerActivity;

import static fr.xebia.tv.XebiaTvApplication.BUS;


public class HomeActivity extends ActionBarActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);
        setContentView(R.layout.home_activity);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.ic_icon_transparent));
    }

    @Override protected void onStart() {
        BUS.register(this);
        super.onStart();
    }

    public void onEventMainThread(PlayVideoEvent playVideoEvent) {
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra(VideoPlayerActivity.EXTRA_VIDEO, new Video(playVideoEvent.videoId, playVideoEvent.videoTitle));
        startActivity(intent);
    }

    @Override protected void onStop() {
        BUS.unregister(this);
        super.onStop();
    }
}
