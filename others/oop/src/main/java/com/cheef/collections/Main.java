package com.cheef.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1 -  Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
 *      Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
 *      Посчитать, сколько раз встречается каждое слово.
 *
 * 2 -  Написать простой класс Телефонный Справочник, который хранит в себе список фамилий и телефонных номеров.
 *      В этот телефонный справочник с помощью метода add() можно добавлять записи,
 *      а с помощью метода get() искать номер телефона по фамилии.
 *      Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
 *      тогда при запросе такой фамилии должны выводиться все телефоны. Желательно не добавлять
 *      лишний функционал (дополнительные поля (имя, отчество, адрес), взаимодействие с пользователем
 *      через консоль и т.д).
 *      Консоль использовать только для вывода результатов проверки телефонного справочника
 **/
public class Main {
    public static void main(String[] args) {
        String[] words = {
                "word", "test", "auth", "sample", "test", "main", "car", "cat", "test",
                "js", "java", "sample", "main", "word", "car", "compose", "game"
        };
        Main.divideTask( "task1" );
        System.out.println( new Main().getUniqueWords( words ) );
        System.out.println( new Main().getWordsCount(words) );

        Main.divideTask( "task2" );

        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("test", "123232323");
        phoneBook.add("test2", "54352352");
        phoneBook.add("test", "9834546234");
        phoneBook.add("test3", "123232323");
        phoneBook.add("test", "878234356");

        System.out.println( phoneBook.getContacts() );

        System.out.println( phoneBook.get("test") );
    }

    /**
     * Get unique words from array
     * @param words - array of words
     * @return ArrayList
     **/
    List<String> getUniqueWords( String[] words ) {
        Map<String, String> map = new HashMap<>();
        for ( String word: words ) {
            map.put( word, word );
        }
        return new ArrayList<>( map.values() );
    }

    /**
     * Get count words in array
     * @param words - array of words
     * @return Map
     **/
    Map<String, Integer> getWordsCount( String[] words ) {
        Map<String, Integer> map = new HashMap<>();
        for ( String word: words ) {
            if ( map.get( word ) != null ) {
                int count = map.get( word );
                map.put( word, count + 1 );
            } else {
                map.put( word, 1 );
            }
        }
        return map;
    }

    static void divideTask( String name ) {
         System.out.println("=======================" + name + "=============================");
    }
}
