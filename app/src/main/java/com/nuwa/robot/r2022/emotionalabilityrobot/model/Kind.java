package com.nuwa.robot.r2022.emotionalabilityrobot.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Kind   implements Parcelable {
    private String kindTitle ;
    private String kindImage ;

    protected Kind(Parcel in) {
        kindTitle = in.readString();
        kindImage = in.readString();
    }

    public Kind() {
    }

    public static final Creator<Kind> CREATOR = new Creator<Kind>() {
        @Override
        public Kind createFromParcel(Parcel in) {
            return new Kind(in);
        }

        @Override
        public Kind[] newArray(int size) {
            return new Kind[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(kindTitle);
        parcel.writeString(kindImage);
    }
}
