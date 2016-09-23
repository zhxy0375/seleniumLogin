package com.javarticles.guava;

public class Fruit {
    private String name;
    private boolean ripe;

    public Fruit(String name) {
        this.name = name;
        this.ripe = true;
    }

    public Fruit(String name, boolean isRipe) {
        this.name = name;
        this.ripe = isRipe;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public boolean isRipe() {
        return ripe;
    }

    public void ripe() {
        System.out.println("Mango is raw, let's ripe it");
        this.ripe = true;
    }
}
