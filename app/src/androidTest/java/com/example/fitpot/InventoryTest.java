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
        inventory.AddSeed(redSeed);
        assertEquals(redSeed, inventory.GetSeed(SeedType.red));
        assertEquals(1, inventory.GetSeedNumber(SeedType.red));
    }

    @Test
    public void testInventoryYellow()
    {
        inventory = new Inventory();
        Seed redSeed = new Seed(SeedType.yellow);
        assertNotNull(redSeed);
        inventory.AddSeed(redSeed);
        assertEquals(redSeed, inventory.GetSeed(SeedType.yellow));
        assertEquals(1, inventory.GetSeedNumber(SeedType.yellow));
    }

    @Test
    public void testInventoryGreen()
    {
        inventory = new Inventory();
        Seed redSeed = new Seed(SeedType.green);
        assertNotNull(redSeed);
        inventory.AddSeed(redSeed);
        assertEquals(redSeed, inventory.GetSeed(SeedType.green));
        assertEquals(1, inventory.GetSeedNumber(SeedType.green));
    }

    @Test
    public void testInventoryBrown()
    {
        inventory = new Inventory();
        Seed redSeed = new Seed(SeedType.brown);
        assertNotNull(redSeed);
        inventory.AddSeed(redSeed);
        assertEquals(redSeed, inventory.GetSeed(SeedType.brown));
        assertEquals(1, inventory.GetSeedNumber(SeedType.brown));
    }

    @Test
    public void testEmpty()
    {
        inventory = new Inventory();
        assertNull(inventory.GetSeed(SeedType.yellow));
        assertNull(inventory.GetSeed(SeedType.green));
        assertNull(inventory.GetSeed(SeedType.red));
        assertNull(inventory.GetSeed(SeedType.brown));

    }
}
