package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.util.stream.Collectors;
/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {

    public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                       Collection<Person> persons2,
                                                       int limit) {
        //Создаем объдиненный список людей - O(persons1.size() + persons2.size())
        List<Person> UnionPersons = new ArrayList<Person>();
        UnionPersons.addAll(persons1);
        UnionPersons.addAll(persons2);
        //Сортируем его - O(NlogN), берем первые limit объектов, переводим в список и возвращаем
        return UnionPersons.stream()
                .sorted(Comparator.comparing(Person::createdAt))
                .limit(limit)
                .toList();
    }
}