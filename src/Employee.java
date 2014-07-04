import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dima on 04.07.14.
 */
public class Employee extends Department {
    String name;
    String post;

    ArrayList<EventRecord> eventsRecords;

    public Employee (String name) {
        this.name = name;
    }
}
