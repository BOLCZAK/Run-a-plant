package com.example.fitpot;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Vector;

public class Inventory implements Parcelable {
    private List<Seed> brownSeeds;
    private List<Seed> greenSeeds;
    private List<Seed> yellowSeeds;
    private List<Seed> redSeeds;

    public Inventory(){
        brownSeeds = new Vector<Seed>();
        greenSeeds = new Vector<Seed>();
        yellowSeeds = new Vector<Seed>();
        redSeeds = new Vector<Seed>();
    }

    protected Inventory(Parcel in) {
        brownSeeds = in.createTypedArrayList(Seed.CREATOR);
        greenSeeds = in.createTypedArrayList(Seed.CREATOR);
        yellowSeeds = in.createTypedArrayList(Seed.CREATOR);
        redSeeds = in.createTypedArrayList(Seed.CREATOR);
    }

    public static final Creator<Inventory> CREATOR = new Creator<Inventory>() {
        @Override
        public Inventory createFromParcel(Parcel in) {
            return new Inventory(in);
        }

        @Override
        public Inventory[] newArray(int size) {
            return new Inventory[size];
        }
    };

    public void addSeed(Seed seed)
    {
        switch (seed.getType())
        {
            case red:
                redSeeds.add(seed);
                break;
            case brown:
                brownSeeds.add(seed);
                break;
            case green:
                greenSeeds.add(seed);
                break;
            case yellow:
                yellowSeeds.add(seed);
                break;
        }
    }

    public int getSeedNumber(SeedType seedType)
    {
        int number = 0;
        switch (seedType)
        {
            case red:
                number = redSeeds.size();
                break;
            case brown:
                number = brownSeeds.size();
                break;
            case green:
                number = greenSeeds.size();
                break;
            case yellow:
                number = yellowSeeds.size();
                break;
        }
        return number;
    }

    public Seed getSeed(SeedType seedType)
    {
        Seed seed = null;
        switch (seedType)
        {
            case red:
                if(redSeeds.size()>0){
                    seed = redSeeds.get(redSeeds.size()-1);
                }
                break;
            case brown:
                if(brownSeeds.size()>0){
                    seed = brownSeeds.get(brownSeeds.size()-1);
                }
                break;
            case green:
                if(greenSeeds.size()>0){
                    seed = greenSeeds.get(greenSeeds.size()-1);
                }
                break;
            case yellow:
                if(yellowSeeds.size()>0){
                    seed = yellowSeeds.get(yellowSeeds.size()-1);
                }
                break;
        }
        return seed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(brownSeeds);
        parcel.writeTypedList(greenSeeds);
        parcel.writeTypedList(yellowSeeds);
        parcel.writeTypedList(redSeeds);
    }
}
