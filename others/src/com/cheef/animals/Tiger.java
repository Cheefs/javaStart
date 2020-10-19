package com.cheef.animals;

public class Tiger extends Animal {
    static int count = 0;

    public Tiger( String name, int maxRunDistance, int maxSwimDistance ) {
        super(name, maxRunDistance, maxSwimDistance);
        Tiger.count++;
    }

    public static int getCount() {
        return count;
    }
}
