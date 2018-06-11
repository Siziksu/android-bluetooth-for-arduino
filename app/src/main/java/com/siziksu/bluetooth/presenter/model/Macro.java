package com.siziksu.bluetooth.presenter.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Macro implements Parcelable {

    public int id;
    public String name;
    public String command;

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.command);
    }

    public Macro() {}

    protected Macro(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.command = in.readString();
    }

    public static final Parcelable.Creator<Macro> CREATOR = new Parcelable.Creator<Macro>() {
        @Override
        public Macro createFromParcel(Parcel source) {return new Macro(source);}

        @Override
        public Macro[] newArray(int size) {return new Macro[size];}
    };
}
