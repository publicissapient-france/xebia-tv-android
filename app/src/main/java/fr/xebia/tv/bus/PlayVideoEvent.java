package fr.xebia.tv.bus;

public class PlayVideoEvent {

    public final String videoId;
    public final String videoTitle;

    public PlayVideoEvent(String videoId, String videoTitle) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
    }
}
