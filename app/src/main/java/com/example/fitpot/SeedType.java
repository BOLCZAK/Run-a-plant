package com.example.fitpot;

public enum SeedType {
    brown("Brown Seed"),
    yellow("Yellow Seed"),
    red("Red Seed"),
    green("Green Seed");

    private String name;

    private SeedType(String s) {
        name = s;
    }


    @Override
    public String toString(){
        return this.name;
    }
}
