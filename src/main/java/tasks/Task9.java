package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

    private long count;

    // Костыль, эластик всегда выдает в топе "фальшивую персону".
    // Конвертируем начиная со второй
    public List<String> getNames(List<Person> persons) {
        //isEmpty приятнее читается
        if (persons.isEmpty()) {
            return Collections.emptyList();
        }
        //Удалять из начала списка плохо, придется перебрать весь список, воспользуемся skip
        //persons.remove(0);
        return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
    }

    // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
    public Set<String> getDifferentNames(List<Person> persons) {
        //distinct не нужен, так как toSet и так возвращает уникальную коллекцию
        //P.S. Idea подсказала, что можно использовать new HashSet)))
        return new HashSet<>(getNames(persons));
        //return getNames(persons).stream().collect(Collectors.toSet());
    }

    // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
    public String convertPersonToString(Person person) {
    /*
    String result = "";
    if (person.secondName() != null) {
      result += person.secondName();
    }

    if (person.firstName() != null) {
      result += " " + person.firstName();
    }

    if (person.secondName() != null) {
      result += " " + person.secondName();
    }
    return result;*/
        //Как-то избыточно проверок, можно фильтрануть в стриме и сложить строки через пробел
        return Stream.of(person.secondName(), person.firstName(), person.middleName())
                .filter(Objects::nonNull)
                .reduce(" ", String::concat);
    }

    // словарь id персоны -> ее имя
    public Map<Integer, String> getPersonNames(Collection<Person> persons) {
        /*Map<Integer, String> map = new HashMap<>(1);
        for (Person person : persons) {
            if (!map.containsKey(person.id())) {
                map.put(person.id(), convertPersonToString(person));
            }
        }
        return map;*/
        //Перепишу через стрим
        return persons.stream()
                .collect(Collectors.toMap(Person::id, this::convertPersonToString, (first, second) -> first, HashMap::new));
    }

    // есть ли совпадающие в двух коллекциях персоны?
    public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
        /*
        boolean has = false;
        for (Person person1 : persons1) {
            for (Person person2 : persons2) {
                if (person1.equals(person2)) {
                    has = true;
                }
            }
        }
        return has;*/
        //Работает за O(persons1.size() * persons2.size()) - долго
        //Первую коллекцию перевел в сет, чтобы работало за линейное время, но возможно и в общем случае сработает
        return Collections.disjoint(new HashSet<Person>(persons1), persons2);
    }

    // Посчитать число четных чисел
    public long countEven(Stream<Integer> numbers) {
        /*count = 0;
        numbers.filter(num -> num % 2 == 0).forEach(num -> count++);
        return count;*/
        //Как-то странно использовать отдельную приватную переменную для этого, не думаю, что нам нужно запоминать ее значение и привязывать к объекту
        return numbers.filter(num -> num % 2 == 0).count();
    }

    // Загадка - объясните почему assert тут всегда верен
    // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
    //По дефолту хэш мапа создается размера 1 и увеличивается вдвое при ее заполнении
    //Соответственно для массива размера 10к хэшмапа увеличится n раз, где 2 ^ n < 10000 < 2 ^ (n + 1)
    // log2(10000) ~ 13.2, поэтому 2 ^ 13 = 8192, 2 ^ 14 = 16384 и коллизии не будет, в каждый бакет попадет по одному элементу
    // Подозреваю, что хеш у чисел это остаток от деления, поэтому они будут располагаться в бакетах по возрастанию
    //Поэтому итоговые результаты совпадают
    void listVsSet() {
        List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
        List<Integer> snapshot = new ArrayList<>(integers);
        Collections.shuffle(integers);
        Set<Integer> set = new HashSet<>(integers);
        assert snapshot.toString().equals(set.toString());
    }
}