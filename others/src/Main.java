//Практическое задание
//    1. Создать классы
//          Собака,
//          Домашний Кот,
//          Тигр,
//          Животное.
//    2. Животные могут бежать и плыть.
//       В качестве аргумента каждому методу передается длина препятствия.
//    3. У каждого животного есть ограничения на действия:
//          * бег: кот — 200 м, собака — 500 м;
//          * плавание: кот — не умеет плавать, собака — 10 м
//        Результатом выполнения действия будет печать в консоль. Например:  dogBobik.run(150); -> 'Бобик пробежал 150 м' .
//    4. Создать один массив с животными и заставляете их по очереди пробежать дистанцию и проплыть.
//    5. * Добавить подсчет созданных  Домашних Котов ,  Тигров ,  Собак ,  Животных
import java.util.Random;
import com.cheef.animals.Animal;
import com.cheef.animals.Cat;
import com.cheef.animals.Dog;
import com.cheef.animals.Tiger;

public class Main {
    public static void main( String[] args ) {
        Animal[] animals = {
            new Cat("Cat", 200, 0),
            new Dog("Dog", 500, 10),
            new Dog("Dog2", 500, 110),
            new Tiger("Tiger", 2000, 100),
            new Tiger("Tiger2", 5000, 1000),
        };

        Random rnd = new Random();
        for (Animal a: animals ) {
            int randomNumber = rnd.nextInt( 1000 );
            a.run( randomNumber );
            a.swim( randomNumber );
        }

        System.out.println(
            "Всего животных: " + Animal.getCount() + "\n" +
            "Всего котов: " + Cat.getCount() + "\n" +
            "Всего собак: " + Dog.getCount() + "\n" +
            "Всего тигров: " + Tiger.getCount() + "\n"
        );
    }
}
