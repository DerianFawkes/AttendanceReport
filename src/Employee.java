import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dima on 04.07.14.
 */
public class Employee {
    private String name;
    private String post;
    private String serialNumber;

    ArrayList<EventRecord> eventsRecords;

    public Employee () {
        setName("default");
        setPost("default");
        setSerialNumber("default");
    }

    public Employee (String serialNumber, String name, String post) {
        setSerialNumber(serialNumber);
        setName(name);
        setPost(post);
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

    public void addEventRecord(String status, String date, String time) {
        eventsRecords.add(new EventRecord(status, date, time));
    }
}
