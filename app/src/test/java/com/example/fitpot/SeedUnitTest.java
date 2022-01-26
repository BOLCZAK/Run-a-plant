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
        assertEquals(SeedType.brown, seed.GetType());

        seed = new Seed(SeedType.yellow);
        assertEquals(SeedType.yellow, seed.GetType());

        seed = new Seed(SeedType.red);
        assertEquals(SeedType.red, seed.GetType());

        seed = new Seed(SeedType.green);
        assertEquals(SeedType.green, seed.GetType());
    }
}
