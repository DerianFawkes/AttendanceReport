import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;

/**
 * Created by Dima on 04.07.14.
 */
public class Employee {
    static final int STATUS_COLUMN = 1;
    static final int DATE_COLUMN = 7;
    static final int TIME_COLUMN = 8;

    private String name;
    private String post;
    private String serialNumber;
    private Department department;

    ArrayList<EventRecord> eventRecords;

    public Employee (String serialNumber, String name, String post, Department department) {
        setSerialNumber(serialNumber);
        setName(name);
        setPost(post);
        setDepartment(department);
        eventRecords = new ArrayList<EventRecord>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void addRecord(String[] cellsValue) {
        eventRecords.add(new EventRecord(cellsValue[STATUS_COLUMN], cellsValue[DATE_COLUMN], cellsValue[TIME_COLUMN], this));
    }

    public void printEvents () {
        for (EventRecord item : eventRecords) {
            item.printFields();
        }
    }

    public ArrayList<Row> sort() {
        ArrayList<Row> rows= new ArrayList<Row>();
        Row row = new Row();
        for (EventRecord item:eventRecords) {
            if (item.getStatusString().equals(item.ENTERPERMIT)) {

            }
        }
        return rows;
    }

}
