package com.example.fitpot;

import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;
import android.os.Looper;

import com.example.fitpot.Inventory;

public class InventoryTest {
    Inventory  inventory;

    @Test
    public void testInventory()
    {
        inventory = new Inventory();
        assertNotNull(inventory);
    }

    @Test
    public void testInventoryRed()
    {
        inventory = new Inventory();
        Seed redSeed = new Seed(SeedType.red);
        assertNotNull(redSeed);
        inventory.addSeed(redSeed);
        assertEquals(redSeed, inventory.getSeed(SeedType.red));
        assertEquals(1, inventory.getSeedNumber(SeedType.red));
    }

    @Test
    public void testInventoryYellow()
    {
        inventory = new Inventory();
        Seed redSeed = new Seed(SeedType.yellow);
        assertNotNull(redSeed);
        inventory.addSeed(redSeed);
        assertEquals(redSeed, inventory.getSeed(SeedType.yellow));
        assertEquals(1, inventory.getSeedNumber(SeedType.yellow));
    }

    @Test
    public void testInventoryGreen()
    {
        inventory = new Inventory();
        Seed redSeed = new Seed(SeedType.green);
        assertNotNull(redSeed);
        inventory.addSeed(redSeed);
        assertEquals(redSeed, inventory.getSeed(SeedType.green));
        assertEquals(1, inventory.getSeedNumber(SeedType.green));
    }

    @Test
    public void testInventoryBrown()
    {
        inventory = new Inventory();
        Seed redSeed = new Seed(SeedType.brown);
        assertNotNull(redSeed);
        inventory.addSeed(redSeed);
        assertEquals(redSeed, inventory.getSeed(SeedType.brown));
        assertEquals(1, inventory.getSeedNumber(SeedType.brown));
    }

    @Test
    public void testEmpty()
    {
        inventory = new Inventory();
        assertNull(inventory.getSeed(SeedType.yellow));
        assertNull(inventory.getSeed(SeedType.green));
        assertNull(inventory.getSeed(SeedType.red));
        assertNull(inventory.getSeed(SeedType.brown));

    }
}
