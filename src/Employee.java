import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Calendar;
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

    public Sheet addContentToSheet(Sheet sheet, CellStyle cs2, CellStyle cs3) {
        int rowIndexBuff; //буфер индекса строки, чтобы добавить количество опозданий
        int count; //подсчет количества опозданий на определенное время
        int lastrow = sheet.getLastRowNum();
        Row row = sheet.createRow(++lastrow);
        Cell cell = row.createCell(0);
        cell.setCellStyle(cs2);
        cell.setCellValue("Сотрудник:");
        cell = row.createCell(1);
        cell.setCellStyle(cs2);
        cell.setCellValue(this.getName());

        //подсчет и вывод опозданий от 16 минут до часа
        row = sheet.createRow(++lastrow);
        rowIndexBuff = lastrow;
        count = 0;
        for (EventRecord item: eventRecords) {
//            GregorianCalendar dateAndTime = item.getDateAndTime();
//            GregorianCalendar compareDate1 = item.getDateAndTime();
//            GregorianCalendar compareDate2 = item.getDateAndTime();
//            compareDate1.set(Calendar.HOUR, 9);
//            compareDate1.set(Calendar.MINUTE, 45);
//            compareDate2.set(Calendar.HOUR, 10);
//            compareDate2.set(Calendar.MINUTE, 31);
            if (item.isStatusENTER() && item.isAfter(9, 46) && item.isBefore(10, 30)) {
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
        sheet.getRow(rowIndexBuff).createCell(2).setCellValue("Опоздания от 16 минут до часа :" + count);
        sheet.getRow(rowIndexBuff).createCell(2).setCellStyle(cs2);

        //подсчет и вывод опозданий от часа до 4-х часов
        row = sheet.createRow(++lastrow);
        rowIndexBuff = lastrow;
        count = 0;
        for (EventRecord item: eventRecords) {
//            GregorianCalendar dateAndTime = item.getDateAndTime();
//            GregorianCalendar compareDate1 = item.getDateAndTime();
//            GregorianCalendar compareDate2 = item.getDateAndTime();
//            compareDate1.set(Calendar.HOUR, 13);
//            compareDate1.set(Calendar.MINUTE, 30);
//            compareDate2.set(Calendar.HOUR, 23);
//            compareDate2.set(Calendar.MINUTE, 59);
            if (item.isStatusENTER() && item.isAfter(10, 31) && item.isBefore(13, 30)) {
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
        sheet.getRow(rowIndexBuff).createCell(2).setCellValue("Опоздания от часа до 4-х часов :" + count);
        sheet.getRow(rowIndexBuff).createCell(2).setCellStyle(cs2);

        //подсчет и вывод опозданий свыше 4-х часов
        row = sheet.createRow(++lastrow);
        rowIndexBuff = lastrow;
        count = 0;
        for (EventRecord item: eventRecords) {
//            GregorianCalendar dateAndTime = item.getDateAndTime();
//            GregorianCalendar compareDate1 = item.getDateAndTime();
//            GregorianCalendar compareDate2 = item.getDateAndTime();
//            compareDate1.set(Calendar.HOUR, 10);
//            compareDate1.set(Calendar.MINUTE, 30);
//            compareDate2.set(Calendar.HOUR, 13);
//            compareDate2.set(Calendar.MINUTE, 31);
            if (item.isStatusENTER() && item.isAfter(13, 31) && item.isBefore(23, 59)) {
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
        sheet.getRow(rowIndexBuff).createCell(2).setCellValue("Опоздания от часа до 4-х часов :" + count);
        sheet.getRow(rowIndexBuff).createCell(2).setCellStyle(cs2);


        return sheet;
    }

}
