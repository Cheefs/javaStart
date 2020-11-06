package com.cheef.obstacles;

public class Wall implements Obstacle {
    private final int height;

    public boolean handle( TestSubject subject ) {
        return subject.jump( height );
    }

    @Override
    public String geInfo() {
        return "Wall" + " " + height + "m";
    }

    public Wall(int height) {
        this.height = height;
    }
}
