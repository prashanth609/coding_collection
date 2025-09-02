package Java_8;

import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

public class JavaEightCoding {
    public static void main(String[] args) {
        firstNonRepeating("Prashanth");
        List<Employee> employees = Arrays.asList(new Employee("Prakash", "Sharma", 15000), new Employee("Pooja", "Agarwal", 12000), new Employee("Prakash", "Bansal", 18000), new Employee("Pawan", "Kumar", 9000), new Employee("Ravi", "Patel", 20000), new Employee("pooja", "Zaveri", 13000), // lower-case 'p' to show case-insensitive
                new Employee(null, "X", 17000), // null-safe checks
                new Employee("Pooja", null, 11500));
        normalizeNameAndSort(employees);
    }

    public static void firstNonRepeating(String s) {
        Map<Character, Long> freq = s.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(c -> c, LinkedHashMap::new, Collectors.counting()));
        Optional<Character> ch = freq.entrySet().stream().filter(e -> e.getValue() == 1).map(Map.Entry::getKey).findFirst();
        System.out.println("firstNonRepeating : " + (ch.isPresent() ? ch.get() : "String is Empty"));

    }

    public static void normalizeNameAndSort(List<Employee> employees) {

        List<String> result = employees.stream()
                // salary > 10000
                .filter(e -> e.getSalary() > 10_000)
                // fName starts with 'P' (case-insensitive, null-safe)
                .filter(e -> {
                    String f = e.getFName();
                    return f != null && !f.isEmpty() && Character.toUpperCase(f.charAt(0)) == 'P';
                })
                // sort by fName (case-insensitive, nulls last), then by lName
                .sorted(Comparator.comparing(Employee::getFName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)).thenComparing(Employee::getLName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                // map to "fName lName"
                .map(e -> (e.getFName() == null ? "" : e.getFName()) + " " + (e.getLName() == null ? "" : e.getLName())).collect(Collectors.toList());

        System.out.println("normalizeNameAndSort : " + result.toString());

    }
}

class Employee {
    private final String fName;
    private final String lName;
    private final int salary;

    public Employee(String fName, String lName, int salary) {
        this.fName = fName;
        this.lName = lName;
        this.salary = salary;
    }

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }

    public int getSalary() {
        return salary;
    }
}
