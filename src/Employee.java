import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dima on 04.07.14.
 */
public class Employee extends Department {
    private String name;
    private String post;

    ArrayList<EventRecord> eventsRecords;

    public Employee () {
        this.name = "default";
        this.post = "default";
    }

    public Employee (String name) {
        this.name = name;
        this.post = "default";
    }

    public Employee (String name, String post) {
        this.name = name;
        this.post = post;
    }
}
