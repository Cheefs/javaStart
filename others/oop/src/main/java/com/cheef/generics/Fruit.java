package com.cheef.generics;

public abstract class Fruit {
    private final float weight;

    public float getWeight() {
        return this.weight;
    }

    public Fruit( float weight ) {
        this.weight = weight;
    }
}
