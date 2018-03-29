package com.hl.afchelper.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huanglei on 2018/a/17.
 */

public class Data implements Parcelable {
        private int mId;
        private String new_title;
        private String new_content;
        private String imageUrl;
    private String videoUrl;


    public Data(int mId, String new_title, String new_content) {
        this.new_title = new_title;
        this.new_content = new_content;
        this.mId = mId;
    }

    public Data(int mId, String new_title, String new_content,String imageUrl) {
        this.new_title = new_title;
        this.new_content = new_content;
        this.mId = mId;
        this.imageUrl = imageUrl;
    }

    public Data(int mId, String new_title, String new_content,String imageUrl,String videoUrl) {
        this.new_title = new_title;
        this.new_content = new_content;
        this.mId = mId;
        this.imageUrl = imageUrl;
        this.videoUrl =videoUrl;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getNew_title() {
            return new_title;
        }

    public int getId() {
        return this.mId;
    }


        public String getNew_content() {
            return new_content;
        }

        public void setNew_title(String new_title) {
            this.new_title = new_title;
        }

        public void setNew_content(String new_content) {
            this.new_content = new_content;
        }

        public String getImageUrl() {
        return imageUrl;
    }

        public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt (this.mId);
        dest.writeString (this.new_title);
        dest.writeString (this.new_content);
        dest.writeString (this.imageUrl);
        dest.writeString (this.videoUrl);
    }

    protected Data(Parcel in) {
        this.mId = in.readInt ();
        this.new_title = in.readString ();
        this.new_content = in.readString ();
        this.imageUrl = in.readString ();
        this.videoUrl = in.readString ();
    }

    public static final Creator<Data> CREATOR = new Creator<Data> () {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data (source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
