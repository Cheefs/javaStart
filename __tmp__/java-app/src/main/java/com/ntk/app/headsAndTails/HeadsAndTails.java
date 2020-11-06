package com.ntk.app.headsAndTails;

public class HeadsAndTails {
    public static final int HEAD = 1;
    public static final int TAIL = 0;

    int coinState;

    public void throwCoin() {
        this.coinState = (int)Math.round( Math.random() );
    }

    public boolean isWin( int value ) {
        return value == this.coinState;
    }
}
