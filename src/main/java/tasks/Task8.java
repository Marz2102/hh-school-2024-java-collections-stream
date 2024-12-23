package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;
import java.util.stream.Collectors;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
    private final PersonService personService;

    public Task8(PersonService personService) {
        this.personService = personService;
    }

    public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
        Set<Resume> resumes = personService.findResumes(persons.stream().map(Person::id).collect(Collectors.toSet()));
        //Создаю мапу, связывающую id и список резюме
        Map<Integer, List<Resume>> PersonsAndResume = resumes.stream()
                .collect(Collectors.groupingBy(Resume::personId));
        //Создаю пустой сет для ответа
        Set<PersonWithResumes> personWithResumes = new HashSet<PersonWithResumes>();
        //Заполняю сет, проверяя на наличие у выбранного айдишника хотя бы одного резюме
        for(Person person : persons){
            personWithResumes.add(new PersonWithResumes(person, new HashSet<Resume>(PersonsAndResume.getOrDefault(person.id(), Collections.<Resume>emptyList()))));
        }
        return personWithResumes;
    }
}