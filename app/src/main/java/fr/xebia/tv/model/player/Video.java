package fr.xebia.tv.model.player;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {
    public final String id;
    public final String title;

    public Video(String id, String title) {
        this.id = id;
        this.title = title;
    }

    protected Video(Parcel in) {
        id = in.readString();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

}
