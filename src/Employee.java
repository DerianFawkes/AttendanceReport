import org.apache.poi.ss.formula.functions.Count;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;

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

    private GregorianCalendar getFirstDate () {
        GregorianCalendar date = department.db.firstDate;
        return date;
    }

    private GregorianCalendar getLastDate () {
        GregorianCalendar date = department.db.lastDate;
        return date;
    }

    public Sheet addContentToSheet(Sheet sheet, CellStyle cs2, CellStyle cs3) {

        int lastrow = sheet.getLastRowNum();
        Row row = sheet.createRow(++lastrow);
        Cell cell = row.createCell(0);
        cell.setCellStyle(cs2);
        cell.setCellValue("Сотрудник:");
        cell = row.createCell(1);
        cell.setCellStyle(cs2);
        cell.setCellValue(this.getName());

        //подсчет и вывод опозданий от 16 минут до часа
        sheet = countLatenessAndAddToSheet(sheet, cs2, cs3, 9, 46, 10, 30, "Опоздания от 16 минут до часа: ");
        //подсчет и вывод опозданий от часа до 4-х часов
        sheet = countLatenessAndAddToSheet(sheet, cs2, cs3, 10, 31, 13, 30, "Опоздания от часа до 4-х часов: ");
        //подсчет и вывод опозданий свыше 4-х часов
        sheet = countLatenessAndAddToSheet(sheet, cs2, cs3, 13, 31, 23, 59, "Опоздания свыше 4-х часов: ");

        //Подсчет Невыходов
        sheet = countAbsence(sheet, cs2, cs3);

        return sheet;
    }

    private Sheet countLatenessAndAddToSheet (Sheet sheet,  CellStyle cs2, CellStyle cs3, int timeH1, int timeM1, int timeH2, int timeM2, String text) {

        int lastrow = sheet.getLastRowNum();
        int rowIndexBuff = lastrow; //буфер индекса строки, чтобы добавить количество опозданий
        sheet.createRow(++lastrow);//оставляем строку пустой, чтобы добавить количество после подсчета
        int count = 0; //подсчет количества опозданий на определенное время
        Row row;
        Cell cell;
        for (EventRecord item: eventRecords) {

            if (item.isStatusENTER() && item.isWorkDay() && item.isAfter(timeH1, timeM1) && item.isBefore(timeH2, timeM2)) {
                row = sheet.createRow(++lastrow);
                cell = row.createCell(4);
                cell.setCellStyle(cs3);
                cell.setCellValue("Дата:" + item.getStringDate());
                cell = row.createCell(6);
                cell.setCellStyle(cs3);
                cell.setCellValue("Время:" + item.getStringTime());
                count++;
            }
        }

        sheet.getRow(rowIndexBuff).createCell(1).setCellStyle(cs2);
        sheet.getRow(rowIndexBuff).getCell(1).setCellValue(text + count);

        return sheet;
    }

    //Подсчет невыходов
    private Sheet countAbsence(Sheet sheet, CellStyle cs2, CellStyle cs3) {
        Row row;
        Cell cell;
        int lastrow = sheet.getLastRowNum();
        int rowIndexBuff = lastrow;
        sheet.createRow(++lastrow); //Оставляем строку пустой
        int count = 0;
        GregorianCalendar comparingDate = getFirstDate();
        GregorianCalendar lastDate = getLastDate();
        Formatter fmt = new Formatter();
        int n = 0;
        do {
            boolean found = false;
            for (int i = n; i <= eventRecords.size(); i++)
            {
                EventRecord er = eventRecords.get(i);
                if (er.isStatusENTER() && comparingDate.get(Calendar.DAY_OF_YEAR) == er.getDateAndTime().get(Calendar.DAY_OF_YEAR)) {
                    row = sheet.createRow(++lastrow);
                    cell = row.createCell(3);
                    cell.setCellValue(er.getStringDate());
                    cell.setCellStyle(cs3);
                    n = i;
                    found = true;
                    break;
                }
            }
            if (!found && comparingDate.get(Calendar.DAY_OF_WEEK) != 1 && comparingDate.get(Calendar.DAY_OF_WEEK) != 7) {
                fmt.format("%td.%tm.%tY", comparingDate,comparingDate,comparingDate);
                row = sheet.createRow(++lastrow);
                cell = row.createCell(3);
                cell.setCellStyle(cs3);
                cell.setCellValue("Дата:" + fmt.toString());
                count++;
            }

            comparingDate.add(Calendar.DAY_OF_YEAR, 1);
        } while (comparingDate.get(Calendar.DAY_OF_YEAR) >= lastDate.get(Calendar.DAY_OF_YEAR) );

        sheet.getRow(rowIndexBuff).createCell(1).setCellStyle(cs2);
        sheet.getRow(rowIndexBuff).getCell(1).setCellValue("Невыходов: " + count);

        return sheet;
    }
}
