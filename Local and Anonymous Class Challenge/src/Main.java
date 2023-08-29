import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        //Create a record named Employee that contains First Name, Last Name and Hire Date
        //Set up a list of employees with various names and hire dates in the Main method
        //Set up a new list of employees that accepts this list as a parameter
        //Create a local class to wrap this class (pass Employee to the constructor and include
        //a field for this) and add some calculated fields such as full name and years worked

        //1. Create a list of employees using your local class
        //2. Create an anonymous class to sort your local class employees by full name or years worked
        //3. Print the sorted list

        List<Employee> employeeList = new ArrayList<>(List.of(
                new Employee("Tom", "Hughes", 2015),
                new Employee("Monika", "May", 2018),
                new Employee("Ben", "Doak", 2016),
                new Employee("Sarah", "McLoughlin", 2010),
                new Employee("Shane", "Mathews", 2017),
                new Employee("Lily", "Jones", 2021)
        ));

        printOrdered(employeeList, "name");
        System.out.println();
        printOrdered(employeeList, "year");


    }

    //A method to sort the employees list using a local class and an anonymous class
    public static void printOrdered(List<? extends Employee> locallist, String sortField) {
        int currentYear = LocalDate.now().getYear();
        class LocalClassEmployees {
            private Employee employee;
            private String fullName;
            private int yearsWorked;

            public LocalClassEmployees(Employee employee) {
                this.employee = employee;
                fullName = employee.firstName + " " + employee.lastName;
                this.yearsWorked = currentYear - employee.yearHired;
            }

            @Override
            public String toString() {
                return "%s has been as employee for %d years".formatted(fullName, yearsWorked);
            }
        }


//        List<LocalClassEmployees> list = new ArrayList<>();
//        for (Employee employee : locallist) {
//            list.add(new LocalClassEmployees(employee));
//        }

//        Instead of creating a LocalClassEmployees object for each employee in a for loop, I've used a map to
//        turn the original list passed into this method into a new map of LocalClassEmployees objects.
        List<LocalClassEmployees> list = locallist.stream()
                .map(LocalClassEmployees::new)
                .collect(Collectors.toList());

        //Call an anonymous class that implements Comparator interface
        var anonymousClass = new Comparator<LocalClassEmployees>() {
            //sortField never changes so the compiler will run
            @Override
            public int compare(LocalClassEmployees o1, LocalClassEmployees o2) {
                if (sortField.equals("name")){
                    return o1.fullName.compareTo(o2.fullName);
                }
                return o1.yearsWorked - o2.yearsWorked;
            }
        };

        //sort list via interface
        list.sort(anonymousClass);

        //print out list
        for (LocalClassEmployees myEmployee : list) {
            System.out.println(myEmployee);
        }
    }
}