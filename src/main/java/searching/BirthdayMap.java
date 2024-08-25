package searching;

import java.util.*;


/**
 * This class represents a map that maps birthdays to persons.
 * Your map should be able to handle multiple people born on the same day.
 * Your map should be able to query efficiently for
 * - people born on a specific date and
 * - people born in a specific year.
 * You can assume that the input is valid for dates (format 'YYYY-MM-DD') and years (format 'YYYY').
 * The years do not start with 0.
 * The time complexity of the operations should be in O(log n + k)
 * where k is the number of people born on the specified date or year
 * and n is the number of different birthdays in the map.
 *
 * Complete the class to make the tests in BirthdayMapTest pass.
 * Do not modify the signature of existing methods.
 * Feel free to add instance variables and new methods.
 * Also feel free to import and use existing java classes.
 */
class BirthdayMap {
    // Hint: feel free to use existing java classes from Java such as java.util.TreeMap

    TreeMap<Birthday, List<Person>> birthdayTree;

    BirthdayMap() {
        birthdayTree = new TreeMap<>();
    }

    /**
     * Adds a person to the map.
     * The key is the birthday of the person.
     * The time complexity of the method should be in O(log n)
     * where n is the number of different birthdays in the map.
     * @param person
     */
    void addPerson(Person person) {
        Birthday newbd = new Birthday(person.birthday);
        List<Person> value;
        if (birthdayTree.containsKey(newbd)) {
            value = birthdayTree.get(newbd);
            value.add(person);
        } else {
            value = new ArrayList<>();
            value.add(person);
            birthdayTree.put(newbd, value);
        }
    }

    /**
     * The function returns a list of Person objects in the map born on the specified date.
     * @param date a String input representing the date (in 'YYYY-MM-DD' format)
     *             for which we want to retrieve people born on.
     * @return A list of Person objects representing all people born on the specified date.
     *          An empty list is returned if no entries are found for the specified date.
     */
    List<Person> getPeopleBornOnDate(String date) {
        Birthday birthdayDate = new Birthday(date);
        if (birthdayTree.containsKey(birthdayDate)) {
            return birthdayTree.get(birthdayDate);
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * The function returns a consolidated list of Person objects
     * in the map born in the specified year.
     * @param year A String input representing the year (in 'YYYY' format)
     *             for which we want to retrieve people born in.
     * @return A consolidated list of Person objects representing all people born in the specified year.
     *         If no entries are found for the specified year, the function returns an empty list.
     */
    List<Person> getPeopleBornInYear(String year) {
        Birthday endOfYear = new Birthday(year.concat("-12-31"));

        Birthday floor = birthdayTree.floorKey(endOfYear);
        Birthday ceiling = birthdayTree.ceilingKey(new Birthday(year.concat("-01-01")));

        if (floor != null && ceiling != null && floor.year == Integer.parseInt(year)) {
            if (floor.equals(ceiling)) {
                return birthdayTree.floorEntry(endOfYear).getValue();
            } else {
                List<Person> ret = new ArrayList<>();
                SortedMap<Birthday, List<Person>> range = birthdayTree.subMap(ceiling, true, floor, true);
                for (List<Person> people : range.values()) {
                    ret.addAll(people);
                }
                return ret;
            }
        }
         return new ArrayList<>();
    }


    /**
     * Example usage of the BirthdayMap class, feel free to modify.
     */
    public static void main(String[] args) {
        Person alice = new Person("Alice", "2000-07-17");
        Person bob = new Person("Bob", "2000-08-15");
        Person charlie = new Person("Charlie", "2001-06-06");

        BirthdayMap map = new BirthdayMap();
        map.addPerson(alice);
        map.addPerson(bob);
        map.addPerson(charlie);

        System.out.println(map.getPeopleBornOnDate("2000-07-17"));
        System.out.println(map.getPeopleBornInYear("2000"));
    }

}

class Person {
    String name;
    String birthday; // format: YYYY-MM-DD

    Person(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

class Birthday implements Comparable {
    int year;
    int month;
    int day;

    //String date must be of format YYYY-MM-DD
    Birthday(String date) {
        String[] separation = date.split("-");
        year = Integer.parseInt(separation[0]);
        month = Integer.parseInt(separation[1]);
        day = Integer.parseInt(separation[2]);
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (o.getClass() != this.getClass()){
            throw new ClassCastException("You must compare 2 instances of the same Class");
        }
        Birthday other = (Birthday) o;
        if (this.year != other.year) {
            return Integer.compare(this.year, other.year);
        } else if (this.month != other.month) {
            return Integer.compare(this.month, other.month);
       } else return Integer.compare(this.day, other.day);
    }
}