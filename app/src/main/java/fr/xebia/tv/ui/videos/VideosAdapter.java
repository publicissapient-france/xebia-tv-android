package fr.xebia.tv.ui.videos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import fr.xebia.tv.R;
import fr.xebia.tv.model.youtube.YoutubeVideoItem;

public class VideosAdapter extends BaseAdapter {

    private Context context;
    private List<YoutubeVideoItem> videos;

    public VideosAdapter(Context context, List<YoutubeVideoItem> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public YoutubeVideoItem getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.video_item_view, parent, false);
        }
        ((VideoItemView) convertView).bind(getItem(position));
        return convertView;
    }
}