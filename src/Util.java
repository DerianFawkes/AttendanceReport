import sun.util.resources.CalendarData_th;

import java.text.Collator;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Dima on 08.08.14.
 */
public abstract class Util {
    private static int[] MONTHS_WITH_HOLYDAYS = {1,2,3,5,6,11};
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

    public static boolean isHoliday (GregorianCalendar gc) {
        GregorianCalendar[] holydayDates = AttendanceReport.settings.getHolydaysDates();
        for(int item: MONTHS_WITH_HOLYDAYS) {
            if (gc.get(Calendar.MONTH) == item) {
                for(GregorianCalendar holyday : holydayDates) {
                    if (gc.get(Calendar.DAY_OF_MONTH) == holyday.get(Calendar.DAY_OF_MONTH)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Сравнение двух строк в алфавитном порядке
    // Если первая строка распологается до второй - истина
    //Если распологается после - ложь
    public static boolean compareStrings(String firstS, String secondS) {
        Collator russianCollator = Collator.getInstance(new Locale("ru", "RU"));
        int result = russianCollator.compare(firstS, secondS);
        if (result < 0) {
            return true;
        } else {
            return false;
        }
    }


}

