package com.cheef.exeptions;

//1. Создайте исключения: MyArraySizeException (неправильный размер массива),
//        и MyArrayDataException (в ячейке массива лежит что-то не то);
//
//2. Напишите метод, на вход которого подаётся двумерный строковый массив (String[][]) размером 4×4.
//        При подаче массива другого размера необходимо бросить исключение MyArraySizeException.
//
//3. Далее метод должен пройтись по всем элементам массива, преобразовать в int и просуммировать.
//        Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа),
//        должно быть брошено исключение MyArrayDataException с детализацией, в какой именно ячейке лежат неверные данные.
//        Расчет данных для этой матрицы прекращается.
//
//4. В методе main() нужно вызвать полученный метод, обработать возможные исключения MySizeArrayException и MyArrayDataException
//        и вывести результат расчёта.


public class Main {
    public static final int MAX_ARRAY_WIDTH = 4;
    public static final int MAX_ARRAY_HEIGHT = 4;

    public int getTotalSumFromArray( String[][] array ) throws MyArraySizeException {
        int totalSum = 0;
        if ( array.length != MAX_ARRAY_HEIGHT && array[0].length != MAX_ARRAY_WIDTH ) {
            throw new MyArraySizeException();
        }

        for (String[] rows: array ) {
            for (String column: rows ) {
                if ( !column.matches("[-|+]?\\d+") ) {
                    throw new MyArrayDataException();
                }
                totalSum += Integer.parseInt( column );
            }
        }
        return totalSum;
    }
    public static void main(String[] args) {
//        String[][] arrayOfStrings = new String[5][5];
//        String[][] arrayOfStrings = {
//            {"1", "asd", "\\c", "5"},
//            {"1", "asd", "\\c", "5"},
//            {"1", "asd", "\\c", "5"},
//            {"1", "asd", "\\c", "5"}
//        };

        String[][] arrayOfStrings = {
            {"1", "2", "3", "4"},
            {"1", "2", "3", "4"},
            {"1", "2", "3", "4"},
            {"1", "2", "3", "4"}
        };

        try {
            int sum = new Main().getTotalSumFromArray( arrayOfStrings );
            System.out.println( sum );
        } catch ( MyArraySizeException ex ) {
            ex.printStackTrace();
        }

    }
}
