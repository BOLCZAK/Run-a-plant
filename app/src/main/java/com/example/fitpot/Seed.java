package com.example.fitpot;

public class Seed {
    private SeedType seedType;
    public Seed(SeedType seedType){
        this.seedType = seedType;
    }
    public SeedType GetType(){
        return seedType;
    }
}
