package com.cheef.obstacles;

public class Treadmill implements Obstacle {
    private final int length;

    public String geInfo() {
        return "Treadmill" + " " + length + "m";
    }

    public Treadmill(int length) {
        this.length = length;
    }

    @Override
    public boolean handle( TestSubject subject ) {
        return subject.run( length );
    }
}
