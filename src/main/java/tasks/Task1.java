package tasks;

import common.Person;
import common.PersonService;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

    private final PersonService personService;

    public Task1(PersonService personService) {
        this.personService = personService;
    }

    public List<Person> findOrderedPersons(List<Integer> personIds) {
        Set<Person> persons = personService.findPersons(personIds);
        //Создаем мапу, связывающую id и человека
        Map<Integer, Person> PersonAndIds = new HashMap<Integer, Person>();
        //Заполнение мапы - O(personIds.size())
        for (Person person : persons) {
            PersonAndIds.putIfAbsent(person.id(), person);
        }
        //Создаем итоговый массив
        List<Person> SortedPersons = new ArrayList<Person>();
        //Заполнение массива - O(personIds.size())
        for (Integer id : personIds) {
            SortedPersons.add(PersonAndIds.get(id));
        }
        return SortedPersons;
    }
}