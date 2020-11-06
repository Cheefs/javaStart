package com.cheef.obstacles;

public class Human implements TestSubject {
    private final String name;
    private final int maxRunLength;
    private final int maxJumpHeight;

    public Human( String name, int maxJumpHeight, int maxRunLength ) {
        this.name = name;
        this.maxJumpHeight = maxJumpHeight;
        this.maxRunLength = maxRunLength;
    }

    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public boolean run( int length ) {
        return maxRunLength >= length;
    }
    @Override
    public boolean jump( int length ) {
        return maxJumpHeight >= length;
    }
}
