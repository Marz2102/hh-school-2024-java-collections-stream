package tasks;

import common.Area;
import common.Person;

import java.util.*;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

    public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                    Map<Integer, Set<Integer>> personAreaIds,
                                                    Collection<Area> areas) {
        Set<String> PersonsAndAreas = new HashSet<String>();
        //Создаю мапу, связываюшую id и area - O(areas.size())
        Map<Integer, String> AreasById = new HashMap<Integer, String>();
        for(Area area : areas){
            AreasById.put(area.getId(), area.getName());
        }
        //Прохожу по списку Person, для каждого person смотрю его id всех area и добавляю их название из мапы
        //O(суммарное количество элементов в сетах в personAreaIds)
        for(Person person : persons){
            for(Integer id : personAreaIds.get(person.id())){
                PersonsAndAreas.add(person.firstName() + " - " + AreasById.get(id));
            }
        }
        return PersonsAndAreas;
    }
}
