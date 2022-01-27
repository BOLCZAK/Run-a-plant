package com.example.fitpot;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;

public class Seed implements Parcelable {
    private SeedType seedType;
    public Seed(SeedType seedType){
        this.seedType = seedType;
    }

    protected Seed(Parcel in) {
        this.seedType = SeedType.values()[in.readInt()];
    }

    public static final Creator<Seed> CREATOR = new Creator<Seed>() {
        @Override
        public Seed createFromParcel(Parcel in) {
            return new Seed(in);
        }

        @Override
        public Seed[] newArray(int size) {
            return new Seed[size];
        }
    };

    public SeedType getType(){
        return seedType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(seedType.ordinal());
    }
}
