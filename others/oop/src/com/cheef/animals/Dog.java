package com.cheef.animals;

public class Dog extends Animal {
    static int count = 0;

    public Dog( String name, int maxRunDistance, int maxSwimDistance ) {
        super(name, maxRunDistance, maxSwimDistance);
        Dog.count++;
    }

    public static int getCount() {
        return count;
    }
}
