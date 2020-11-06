package com.cheef.animals;

public class Cat extends Animal {
    static int count = 0;

    public Cat( String name, int maxRunDistance, int maxSwimDistance ) {
        super(name, maxRunDistance, maxSwimDistance);
        Cat.count++;
    }

    public static int getCount() {
        return count;
    }
}
