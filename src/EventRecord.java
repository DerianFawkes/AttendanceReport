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
        if (status.equals(ENTERPERMIT)) {
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

        day = Integer.parseInt(date.substring(1,2));
        month = Integer.parseInt(date.substring(3, 5));
        year = Integer.parseInt(date.substring(6, 10));

        hours = Integer.parseInt(time.substring(1,2));
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

    public boolean isStatusENTER() {
        boolean flag;
        if (getStatus().equals(Status.ENTER) ) {
            return true;
        }
        return false;
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
        System.out.print(getDateAndTime().get(Calendar.YEAR) + " ");
        System.out.print(getDateAndTime().get(Calendar.HOUR_OF_DAY) + " ");
        System.out.println(getDateAndTime().get(Calendar.MINUTE) + " ");
    }

    public String getStringTime () {
        String string;
        string = dateAndTime.get(Calendar.HOUR_OF_DAY)+":"+dateAndTime.get(Calendar.MINUTE);
        return string;
    }

    public String getStringDate () {
        String string;
        string = dateAndTime.get(Calendar.DAY_OF_MONTH)+"."+dateAndTime.get(Calendar.MONTH) + "." + dateAndTime.get(Calendar.YEAR);
        return string;
    }

    public boolean isAfter (int hours, int minutes) {
        int h = dateAndTime.get(Calendar.HOUR_OF_DAY);
        int m = dateAndTime.get(Calendar.MINUTE);
        System.out.println(h+":"+m);
        if (h >= hours && m >= minutes) {
            return true;
        }
        else
            return false;
    }

    public boolean isBefore (int hours, int minutes) {
        int h = dateAndTime.get(Calendar.HOUR_OF_DAY);
        int m = dateAndTime.get(Calendar.MINUTE);
        System.out.println(h+":"+m);
        if (h <= hours && m <= minutes) {
            return true;
        }
        else
            return false;
    }
}