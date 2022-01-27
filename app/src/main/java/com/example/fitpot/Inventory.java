package com.example.fitpot;

import com.example.fitpot.Seed;
import com.example.fitpot.SeedType;

import java.util.List;
import java.util.Vector;

public class Inventory {
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

    public void AddSeed(Seed seed)
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

    public int GetSeedNumber(SeedType seedType)
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

    public Seed GetSeed(SeedType seedType)
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
}
