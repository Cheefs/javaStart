package com.cheef.obstacles;

import com.cheef.animals.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 1. Продолжаем работать с участниками и выполнением действий.
 *    Создайте три класса Человек, Кот, Робот, которые не наследуются от одного класса.
 *    Эти классы должны уметь бегать и прыгать, все также с выводом информации о действии в консоль.
 *
 * 2. Создайте два класса: беговая дорожка и стена, при прохождении через которые,
 *    участники должны выполнять соответствующие действия (бежать или прыгать),
 *    результат выполнения печатаем в консоль (успешно пробежал, не смог пробежать и т.д.).
 *    У препятствий есть длина (для дорожки) или высота (для стены), а участников ограничения на бег и прыжки.
 *
 * 3. Создайте два массив: с участниками и препятствиями, и заставьте всех участников пройти этот набор препятствий.
 *    Если участник не смог пройти одно из препятствий, то дальше по списку он препятствий не идет.
 **/
public class Main {
    public static void main(String[] args) {
        TestSubject[] subjects = {
            new Cat("Barsik", 5, 100 ),
            new Cat("Murzik", 9, 80 ),
            new Cat("Boss", 15, 500 ),

            new Human("Human1", 2, 300 ),
            new Human("Human2", 1, 200 ),
            new Human("Human3", 3, 100 ),

            new Robot("Robot1", 20, 1000 ),
            new Robot("Robot2", 30, 2000 ),
            new Robot("Robot3", 50, 5000 ),
        };

        List<Obstacle> obstacles = new ArrayList<>();
        Random rnd = new Random();
        for( int i = 0; i < 3; i++ ) {
            obstacles.add( new Wall( rnd.nextInt( 50 ) ) );
            obstacles.add( new Treadmill( rnd.nextInt( 5000 ) ) );
        }

        for ( TestSubject subject: subjects ) {
            boolean isWin = true;
            for ( Obstacle obstacle: obstacles ) {

//                System.out.println("**************" +  obstacle.handle( subject ) + "*******************");

                if ( !obstacle.handle( subject )  ) {
                    isWin = false;
                    System.out.println( subject.getName() + " " + "can`t handle obstacle" + " " + obstacle.geInfo() );
                    System.out.println( subject.getName() + " " + "lose!" );
                    break;
                }
                System.out.println( subject.getName() + " " + "handle obstacle" + " " + obstacle.geInfo() );
                System.out.println( subject.getName() + " " + "going to Next!" );
            }

            if ( isWin ) {
                System.out.println( subject.getName() + " " + "handle all obstacles!" );
            }

            System.out.println( "==================================================" );
        }
    }
}
