import java.util.GregorianCalendar;

/**
 * Created by Dima on 04.07.14.
 */
public class EventRecord {
    //static final String ENTERDENIED = "Вход запрещен";
    static final String ENTERPERMIT = "Вход разрешен";
    //static final String EXITDENIED = "Выход запрещен";
    static final String EXITPERMIT = "Выход разрешен";
    enum Status {ENTER, EXIT}
    Status status;
    private GregorianCalendar dateAndTime;

    public EventRecord(String status, String date, String time) {
        if (status == ENTERPERMIT) {
            this.status = Status.ENTER;
        }
        else {
            this.status = Status.EXIT;
        }
        setDateAndTime(date, time);
    }

    public GregorianCalendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(GregorianCalendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public void setDateAndTime(String date, String time) {
        int day;
        int month;
        int year;

        int hours;
        int minutes;

        day = Integer.parseInt(date.substring(0,1));
        month = Integer.parseInt(date.substring(3, 4));
        year = Integer.parseInt(date.substring(6, 9));

        hours = Integer.parseInt(time.substring(0,1));
        minutes = Integer.parseInt(time.substring(3,4));

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
}
