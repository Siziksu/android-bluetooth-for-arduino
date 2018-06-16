package com.siziksu.bluetooth.presenter.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Macro implements Parcelable {

    public int id;
    public String name;
    public byte command;
    public boolean confirmation;

    public Macro() {}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.command);
        dest.writeByte(this.confirmation ? (byte) 1 : (byte) 0);
    }

    protected Macro(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.command = in.readByte();
        this.confirmation = in.readByte() != 0;
    }

    public static final Creator<Macro> CREATOR = new Creator<Macro>() {
        @Override
        public Macro createFromParcel(Parcel source) {return new Macro(source);}

        @Override
        public Macro[] newArray(int size) {return new Macro[size];}
    };
}
