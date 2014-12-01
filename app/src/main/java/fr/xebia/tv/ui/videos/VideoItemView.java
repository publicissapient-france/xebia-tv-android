package fr.xebia.tv.ui.videos;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.xebia.tv.R;
import fr.xebia.tv.model.youtube.YoutubeVideoItem;

import static fr.xebia.tv.core.Compatibility.removeOnGlobalLayoutListener;

public class VideoItemView extends CardView {

    @InjectView(R.id.thumbnail) ImageView thumbnail;
    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.description) TextView description;

    private YoutubeVideoItem youtubeVideoItem;

    public VideoItemView(Context context) {
        super(context);
    }

    public VideoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                removeOnGlobalLayoutListener(getViewTreeObserver(), this);
                thumbnail.getLayoutParams().height = thumbnail.getMeasuredWidth() * 3 / 4;
            }
        });
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void bind(YoutubeVideoItem youtubeVideoItem) {
        this.youtubeVideoItem = youtubeVideoItem;
        title.setText(youtubeVideoItem.snippet.title);
        description.setText(youtubeVideoItem.snippet.description);
        Picasso.with(getContext()).load(youtubeVideoItem.snippet.thumbnails.medium.url)
                .placeholder(new ColorDrawable(getResources().getColor(android.R.color.black)))
                .fit().centerInside().into(thumbnail);
    }
}
