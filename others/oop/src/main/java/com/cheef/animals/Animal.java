package com.cheef.animals;

public class Animal {
    static int count = 0;

    protected String name;
    protected int maxRunDistance;
    protected int maxSwimDistance;

    public static int getCount() {
        return count;
    }

    public Animal(String name, int maxRunDistance, int maxSwimDistance) {
        Animal.count++;
        this.name = name;
        this.maxRunDistance = maxRunDistance;
        this.maxSwimDistance = maxSwimDistance;
    }

    public void run( int distance ) {
        if ( distance > maxRunDistance ) {
            String msg = maxRunDistance > 0 ? "не может пробежать больше "+ maxRunDistance +" м" : "не умеет бегать!";
            System.out.println( name + " " + msg );
            return;
        }
        System.out.println( name + " пробежал "+ distance +" м");
    };
    public void swim( int distance ) {
        if ( distance > maxSwimDistance ) {
            String msg = maxSwimDistance > 0 ? "не может проплыть больше "+ maxSwimDistance +" м" : "не умеет плавать!";
            System.out.println( name + " " + msg );
            return;
        }
        System.out.println(  name +" проплыл "+ distance +" м");
    };
}
