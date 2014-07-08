import java.util.ArrayList;

/**
 * Created by Dima on 04.07.14.
 */
public class Department {
    static final int SERIALNUMBER_COLUMN = 6;
    static final int EMPLOYEE_COLUMN = 3;
    static final int POST_COLUMN = 4;

    String name;
    ArrayList<Employee> employers;

    public Department (String name) {
        setName(name);
        employers = new ArrayList<Employee>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void addEmployeeAndRecord (String[] cellsValue) {
        int checkresult = checkEmployee(cellsValue[SERIALNUMBER_COLUMN]);
        if (checkresult == -1) {
            Employee newEmployee = new Employee(cellsValue[SERIALNUMBER_COLUMN], cellsValue[EMPLOYEE_COLUMN], cellsValue[POST_COLUMN], this);
            newEmployee.addRecord(cellsValue);
            employers.add(newEmployee);
        }
        else {
            employers.get(checkresult).addRecord(cellsValue);
        }
    }

    public int checkEmployee (String serialnumber) {
        int i = 0;
        for (Employee item : employers) {
            String empSN = item.getSerialNumber();
            if (empSN.equals(serialnumber)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void printEmployees() {
        for( Employee item : employers) {
            System.out.println(item.getName()+" "+item.getSerialNumber());
        }
    }
}
