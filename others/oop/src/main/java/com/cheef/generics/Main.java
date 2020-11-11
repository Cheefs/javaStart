package com.cheef.generics;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 1. Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа).
 * 2. Написать метод, который преобразует массив в ArrayList.
 *
 * 3. Задача:
 *      a. Даны классы Fruit -> Apple, Orange.
 *      b. Класс Box, в который можно складывать фрукты.
 *          Коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины.
 *      c. Для хранения фруктов внутри коробки можно использовать ArrayList.
 *      d. Написать метод getWeight(), который высчитывает вес коробки.
 *          Задать вес одного фрукта и их количество: вес яблока — 1.0f, апельсина — 1.5f (единицы измерения не важны).
 *      e. Внутри класса Box написать метод Compare, который позволяет сравнить текущую коробку с той,
 *          которую подадут в Compare в качестве параметра.
 *          True, если их массы равны, False — в противном случае. Можно сравнивать коробки с яблоками и апельсинами.
 *      f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую.
 *          Помним про сортировку фруктов: нельзя высыпать яблоки в коробку с апельсинами.
 *          Соответственно, в текущей коробке фруктов не остаётся, а в другую перекидываются объекты, которые были в первой.
 *      g. Не забываем про метод добавления фрукта в коробку
 **/
public class Main {
    public <T> void swapItems( int index, T[] a, T[] b ) {
        a[ index ] = b[ index ];
    }

    public <T> ArrayList<T> toArrayList( T[] array ) {
        return new ArrayList<T>( Arrays.asList( array ) );
    }

    public static void main(String[] args) {
        System.out.println("================= Task 1 ==========================");
        String[] tes1 = {"123", "432", "532"};
        String[] tes2 = {"sss", "aaa", "ddd"};

        (new Main()).swapItems( 1, tes1, tes2 );

        for ( String item: tes1 ) {
            System.out.println( item );
        }
        System.out.println("================= /Task 1 =========================");
        System.out.println("================= Task 2 ==========================");

        Integer[] testToList = { 1, 2, 3, 4, 5, 6, 7 };
        ArrayList<Integer> listOfInts = ( new Main() ).toArrayList( testToList );

        listOfInts.add( 5 );
        System.out.println( listOfInts.getClass().getName() );

        for ( Integer item: listOfInts ) {
            System.out.println( item );
        }

        System.out.println("================= /Task 2 =========================");
        System.out.println("================= Task 3 =========================");
        
        System.out.println("================= /Task 3 =========================");
    }
}
