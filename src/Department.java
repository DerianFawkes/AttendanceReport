import java.util.ArrayList;

/**
 * Created by Dima on 04.07.14.
 */
public class Department {
    String name;
    String serialnumber;
    ArrayList<Employee> employers;

    public Department () {
        this.name = "default";
    }

    public Department (String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public void addEmployee(String newSerialnumber, String newName, String newPost) {
        boolean alreadyExists = false;
        for (Employee item : employers) {
            String currentSerialNumber = item.getSerialNumber();

            if(currentSerialNumber.equals(serialnumber)) {
                alreadyExists = true;
                item.addEventRecord();
            }
        }

        if (!alreadyExists) {
            Employee newEmployee = new Employee(newSerialnumber, newName, newPost);
            newEmployee.addEventRecord();
            employers.add(new Employee(newSerialnumber, newName, newPost));
        }

    }
}
