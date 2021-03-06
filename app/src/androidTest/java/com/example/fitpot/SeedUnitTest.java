package com.example.fitpot;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class SeedUnitTest {
    private Seed seed;

    @Test
    public void SeedTypeTest()
    {
        seed = new Seed(SeedType.brown);
        assertEquals(SeedType.brown, seed.getType());

        seed = new Seed(SeedType.yellow);
        assertEquals(SeedType.yellow, seed.getType());

        seed = new Seed(SeedType.red);
        assertEquals(SeedType.red, seed.getType());

        seed = new Seed(SeedType.green);
        assertEquals(SeedType.green, seed.getType());

        seed.describeContents();
    }

    @Test
    public void SeedImgResTest(){
        seed = new Seed(SeedType.brown);
        assertEquals(R.drawable.seed_brown, seed.getSeedImgRes());

        seed = new Seed(SeedType.yellow);
        assertEquals(R.drawable.seed_yellow, seed.getSeedImgRes());

        seed = new Seed(SeedType.red);
        assertEquals(R.drawable.seed_red, seed.getSeedImgRes());

        seed = new Seed(SeedType.green);
        assertEquals(R.drawable.seed_green, seed.getSeedImgRes());

    }
}
