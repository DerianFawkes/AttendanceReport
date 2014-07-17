import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Dima on 04.07.14.
 */
public class EventRecord {
    //static final String ENTERDENIED = "Вход запрещен";
    static final String ENTERPERMIT = "Вход разрешён";
    //static final String EXITDENIED = "Выход запрещен";
    static final String EXITPERMIT = "Выход разрешён";

    enum Status {ENTER, EXIT}
    private Status status;
    private GregorianCalendar dateAndTime;
    private Employee employee;

    public EventRecord(String status, String date, String time, Employee employee) {
        if (status == ENTERPERMIT) {
            this.status = Status.ENTER;
        }
        else {
            this.status = Status.EXIT;
        }
        setDateAndTime(date, time);
        this.employee = employee;
    }

    public GregorianCalendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(GregorianCalendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setDateAndTime(String date, String time) {
        int day;
        int month;
        int year;

        int hours;
        int minutes;

        day = Integer.parseInt(date.substring(0,2));
        month = Integer.parseInt(date.substring(3, 5));
        year = Integer.parseInt(date.substring(6, 10));

        hours = Integer.parseInt(time.substring(0,2));
        minutes = Integer.parseInt(time.substring(3,5));

                setDateAndTime(new GregorianCalendar(year, month, day, hours, minutes));
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusString() {
        if (getStatus() == Status.ENTER) {
            return ENTERPERMIT;
        }
        else {
            return EXITPERMIT;
        }
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString() {
        String string;
        string = Integer.toString(getDateAndTime().get(Calendar.DATE)) + getStatusString();
        return string;
    }

    public void printFields () {
        System.out.print(getDateAndTime().get(Calendar.DAY_OF_MONTH) + " ");
        System.out.print(getDateAndTime().get(Calendar.MONTH) + " ");
        System.out.println(getDateAndTime().get(Calendar.YEAR) + " ");
    }
}