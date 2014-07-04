import java.util.ArrayList;

/**
 * Created by Dima on 04.07.14.
 */
public class Department {
    String name;
    ArrayList<Employee> employers;

    public Department () {
        this.name = "default";
    }

    public Department (String name) {
        this.name = name;
    }

    public void addEmployee(String name) {
        employers.add(new Employee(name));
    }

}
