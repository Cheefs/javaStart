package com.ntk.app;

import com.ntk.app.headsAndTails.HeadsAndTails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App 
{
    public static void main( String[] args ) throws IOException {
        HeadsAndTails game = new HeadsAndTails();

        System.out.println( "========= Tails or Head! ===========" );
        System.out.println( HeadsAndTails.HEAD + ") - Head" );
        System.out.println( HeadsAndTails.TAIL + ") - Tail" );

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while ( true ) {
            game.throwCoin();
            System.out.print( "Your choice: " );
            String line = reader.readLine();

            boolean isNumber = line.matches("\\d+");
            if ( !isNumber ) {
                System.out.println( "  NOT A NUMBER!   " );
                continue;
            }
            if ( game.isWin( Integer.parseInt(line) ) ) {
                System.out.println( "============= You win! ==============" );
                break;
            }
            System.out.println( "========== Wrong! Try Again ============" );
        }
    }
}
