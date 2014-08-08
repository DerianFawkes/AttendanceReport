import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Dima on 08.08.14.
 */
public abstract class Util {
    public static String getDayOfWeek(GregorianCalendar gc) {
        String string;
        switch (gc.get(Calendar.DAY_OF_WEEK)) {
            case 1: string = "вс.";
                break;
            case 2: string = "пн.";
                break;
            case 3: string = "вт.";
                break;
            case 4: string = "ср.";
                break;
            case 5: string = "чт.";
                break;
            case 6: string = "пт.";
                break;
            case 7: string = "сб.";
                break;
            default: string = "error";
        }
        return string;
    }
}
