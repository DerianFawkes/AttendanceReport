import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

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

    //Метод для наполнения листа данными для всех отделов, кроме склада
    public Sheet addContentToSheet(Sheet sheet, CellStyle cs2, CellStyle cs3, CellStyle cs4, CellStyle cs5, CellStyle cs6) {

        //Добавление на лист имени сотрудника
        sheet = addEmployeeName(sheet, cs2);

        //подсчет и вывод опозданий от 16 минут до часа
        sheet = countLatenessAndAddToSheet(sheet, cs2, cs3, 9, 46, 10, 30, "Опоздания от 16 минут до часа: ");
        //подсчет и вывод опозданий от часа до 4-х часов
        sheet = countLatenessAndAddToSheet(sheet, cs2, cs3, 10, 31, 13, 30, "Опоздания от часа до 4-х часов: ");
        //подсчет и вывод опозданий свыше 4-х часов
        sheet = countLatenessAndAddToSheet(sheet, cs2, cs3, 13, 31, 23, 59, "Опоздания свыше 4-х часов: ");

        //Подсчет Невыходов
        sheet = countAbsenceAndAddToSheet(sheet, cs2, cs3);

        //Подсчет и вывод Ушел раньше
        sheet = countLeftEarlyAndAddToSheet(sheet, cs2, cs3, 17, 59);

        //Подсчет и вывод Ушел позже
        sheet = countLeftLaterAndAddtoSheet(sheet, cs2, cs3, 18, 30);

        //Подсчет и вывод Выходов в нерабочие дни
        sheet = countWorkOnHolidaysAndAddToSheet(sheet, cs2, cs3);

        //Таблица Детализации
        sheet = addDetalisationTableToSheet(sheet, cs4, cs5, cs6);
        return sheet;
    }

    //Метод для наполнения листа данными только по Складу
    public Sheet addContentToSheetForStorage(Sheet sheet, CellStyle cs2, CellStyle cs3, CellStyle cs4) {
        //Добавление на лист имени сотрудника
        sheet = addEmployeeName(sheet, cs2);
        //Добавление детализации
        sheet = addDetalisationTableToSheet(sheet, cs2, cs3, cs4);
        return sheet;
    }

    private Sheet addEmployeeName(Sheet sheet, CellStyle cs2) {

        int lastrow = sheet.getLastRowNum();
        Row row = sheet.createRow(++lastrow);
        Cell cell = row.createCell(0);
        cell.setCellStyle(cs2);
        cell.setCellValue("Сотрудник:");
        cell = row.createCell(1);
        cell.setCellStyle(cs2);
        cell.setCellValue(this.getName());
        sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 1,4));

        return sheet;
    }

    private Sheet countLatenessAndAddToSheet (Sheet sheet,  CellStyle cs2, CellStyle cs3, int timeH1, int timeM1, int timeH2, int timeM2, String text) {

        int lastrow = sheet.getLastRowNum()+1;
        int rowIndexBuff = lastrow; //буфер индекса строки, чтобы добавить количество опозданий
        sheet.createRow(lastrow);//оставляем строку пустой, чтобы добавить количество после подсчета
        int count = 0; //подсчет количества опозданий на определенное время
        Row row;
        Cell cell;
        for (EventRecord item: eventRecords) {

            if (item.isStatusENTER() && item.isWorkDay() && item.isAfter(timeH1, timeM1) && item.isBefore(timeH2, timeM2)) {
                row = sheet.createRow(++lastrow);
                cell = row.createCell(4);
                cell.setCellStyle(cs3);
                cell.setCellValue("Дата: " + item.getStringDate());
                sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 4,5));
                cell = row.createCell(6);
                cell.setCellStyle(cs3);
                cell.setCellValue("Время: " + item.getStringTime());
                sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 6,7));
                count++;
            }
        }

        sheet.getRow(rowIndexBuff).createCell(1).setCellStyle(cs2);
        sheet.getRow(rowIndexBuff).getCell(1).setCellValue(text + count);
        sheet.addMergedRegion(new CellRangeAddress(rowIndexBuff, rowIndexBuff, 1,4));

        return sheet;
    }

    //Подсчет невыходов
    private Sheet countAbsenceAndAddToSheet(Sheet sheet, CellStyle cs2, CellStyle cs3) {
        Row row;
        Cell cell;
        int lastrow = sheet.getLastRowNum()+1;
        int rowIndexBuff = lastrow;
        sheet.createRow(lastrow); //Оставляем строку пустой
        int count = 0;
        GregorianCalendar comparingDate = new GregorianCalendar(DataBase.getFirstDate().get(Calendar.YEAR),
                                                                DataBase.getFirstDate().get(Calendar.MONTH),
                                                                DataBase.getFirstDate().get(Calendar.DAY_OF_MONTH));
        GregorianCalendar lastDate = DataBase.getLastDate();

        int n = 0;
        do {
            boolean found = false;
            for (int i = n; i < eventRecords.size(); i++)
            {
                EventRecord er = eventRecords.get(i);
                if (er.isStatusENTER() && comparingDate.get(Calendar.DAY_OF_YEAR) == er.getDateAndTime().get(Calendar.DAY_OF_YEAR)) {
                    n = i;
                    found = true;
                    break;
                }
            }
            if (!found && comparingDate.get(Calendar.DAY_OF_WEEK) != 1 && comparingDate.get(Calendar.DAY_OF_WEEK) != 7 && !Util.isHoliday(comparingDate)) {
                Formatter fmt = new Formatter();
                fmt.format("%td.%tm.%tY", comparingDate,comparingDate,comparingDate);
                row = sheet.createRow(++lastrow);
                cell = row.createCell(4);
                cell.setCellStyle(cs3);
                cell.setCellValue("Дата: " + fmt.toString());
                sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 4,5));
                fmt.close();
                count++;
            }

            comparingDate.add(Calendar.DAY_OF_YEAR, 1);
        } while (comparingDate.get(Calendar.DAY_OF_YEAR) <= lastDate.get(Calendar.DAY_OF_YEAR) && comparingDate.get(Calendar.YEAR) == lastDate.get(Calendar.YEAR));

        sheet.getRow(rowIndexBuff).createCell(1).setCellStyle(cs2);
        sheet.getRow(rowIndexBuff).getCell(1).setCellValue("Невыходов: " + count);
        sheet.addMergedRegion(new CellRangeAddress(rowIndexBuff, rowIndexBuff, 1,4));

        return sheet;
    }

    private Sheet countLeftEarlyAndAddToSheet(Sheet sheet, CellStyle cs2, CellStyle cs3, int timeH, int timeM) {

        int lastrow = sheet.getLastRowNum()+1;
        int rowIndexBuff = lastrow; //буфер индекса строки, чтобы добавить количество опозданий
        sheet.createRow(lastrow);//оставляем строку пустой, чтобы добавить количество после подсчета
        int count = 0; //подсчет количества опозданий на определенное время
        Row row;
        Cell cell;
        for (EventRecord item: eventRecords) {

            if (item.isStatusEXIT() && item.isWorkDay() && item.isBefore(timeH, timeM)) {
                row = sheet.createRow(++lastrow);
                cell = row.createCell(4);
                cell.setCellStyle(cs3);
                cell.setCellValue("Дата: " + item.getStringDate());
                sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 4,5));
                cell = row.createCell(6);
                cell.setCellStyle(cs3);
                cell.setCellValue("Время: " + item.getStringTime());
                sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 6,7));
                count++;
            }
        }

        sheet.getRow(rowIndexBuff).createCell(1).setCellStyle(cs2);
        sheet.getRow(rowIndexBuff).getCell(1).setCellValue("Ушел раньше: " + count);
        sheet.addMergedRegion(new CellRangeAddress(rowIndexBuff, rowIndexBuff, 1,4));

        return sheet;
    }

    private Sheet countLeftLaterAndAddtoSheet (Sheet sheet, CellStyle cs2, CellStyle cs3, int timeH, int timeM) {
        int lastrow = sheet.getLastRowNum()+1;
        int rowIndexBuff = lastrow; //буфер индекса строки, чтобы добавить количество опозданий
        sheet.createRow(lastrow);//оставляем строку пустой, чтобы добавить количество после подсчета
        int count = 0; //подсчет количества опозданий на определенное время
        Row row;
        Cell cell;
        for (EventRecord item: eventRecords) {

            if (item.isStatusEXIT() && item.isWorkDay() && item.isAfter(timeH, timeM)) {
                row = sheet.createRow(++lastrow);
                cell = row.createCell(4);
                cell.setCellStyle(cs3);
                cell.setCellValue("Дата: " + item.getStringDate());
                sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 4,5));
                cell = row.createCell(6);
                cell.setCellStyle(cs3);
                cell.setCellValue("Время: " + item.getStringTime());
                sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 6,7));
                count++;
            }
        }

        sheet.getRow(rowIndexBuff).createCell(1).setCellStyle(cs2);
        sheet.getRow(rowIndexBuff).getCell(1).setCellValue("Ушел позже: " + count);
        sheet.addMergedRegion(new CellRangeAddress(rowIndexBuff, rowIndexBuff, 1,4));

        return sheet;
    }

    private Sheet countWorkOnHolidaysAndAddToSheet (Sheet sheet, CellStyle cs2, CellStyle cs3) {
        int lastrow = sheet.getLastRowNum()+1;
        int rowIndexBuff = lastrow; //буфер индекса строки, чтобы добавить количество опозданий
        sheet.createRow(lastrow);//оставляем строку пустой, чтобы добавить количество после подсчета
        int count = 0; //подсчет количества опозданий на определенное время
        Row row;
        Cell cell;
        for (EventRecord item: eventRecords) {

            if (!item.isWorkDay()) {
                row = sheet.createRow(++lastrow);

                cell = row.createCell(4);
                cell.setCellStyle(cs3);
                cell.setCellValue("Дата: " + item.getStringDate());
                sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 4,5));
                cell = row.createCell(6);
                cell.setCellStyle(cs3);
                cell.setCellValue("Время: " + item.getStringTime());
                sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 6,7));
                if(item.isStatusENTER()) {
                    count++;
                    cell = row.createCell(3);
                    cell.setCellStyle(cs3);
                    cell.setCellValue("Вход:");
                } else {
                    cell = row.createCell(3);
                    cell.setCellStyle(cs3);
                    cell.setCellValue("Выход:");
                }

            }
        }

        sheet.getRow(rowIndexBuff).createCell(1).setCellStyle(cs2);
        sheet.getRow(rowIndexBuff).getCell(1).setCellValue("Выходы в нерабочие дни: " + count);
        sheet.addMergedRegion(new CellRangeAddress(rowIndexBuff, rowIndexBuff, 1,4));

        return sheet;
    }

    private Sheet addDetalisationTableToSheet (Sheet sheet, CellStyle cs4, CellStyle cs5, CellStyle cs6) {
        Row row;
        Cell cell;
        int lastrow = sheet.getLastRowNum();
        row = sheet.createRow(++lastrow);
        for (int i = 0; i <9; i++) {
            row.createCell(i).setCellStyle(cs4);
        }
        row.getCell(0).setCellValue("Дата");
        row.getCell(1).setCellValue("День недели");
        row.getCell(2).setCellValue("Приход");
        sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 2, 4));
        row.getCell(5).setCellValue("Уход");
        sheet.addMergedRegion(new CellRangeAddress(lastrow, lastrow, 5, 7));
        row.getCell(8).setCellValue("Комментарий");
        sheet.setColumnWidth(8, 8000);

        row = sheet.createRow(++lastrow);
        for (int i = 0; i <9; i++) {
            row.createCell(i).setCellStyle(cs5);
        }
        row.getCell(2).setCellValue("Должен");
        row.getCell(3).setCellValue("Факт.");
        row.getCell(4).setCellValue("Расхожд.");
        row.getCell(5).setCellValue("Должен");
        row.getCell(6).setCellValue("Факт.");
        row.getCell(7).setCellValue("Расхожд.");


        GregorianCalendar comparingDate = new GregorianCalendar(DataBase.getFirstDate().get(Calendar.YEAR),
                                                                DataBase.getFirstDate().get(Calendar.MONTH),
                                                                DataBase.getFirstDate().get(Calendar.DAY_OF_MONTH));

        GregorianCalendar lastDate = DataBase.getLastDate();

        int startIndex = 0;
        do {
            row = sheet.createRow(++lastrow);
            for (int i = 0; i <9; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(cs6);
            }
            Formatter fmt = new Formatter();
            fmt.format("%td.%tm.%tY", comparingDate, comparingDate, comparingDate);
            row.getCell(0).setCellValue(fmt.toString());
            fmt.close();
            row.getCell(1).setCellValue(Util.getDayOfWeek(comparingDate));
            boolean written = false;
            for (int i = startIndex; i < eventRecords.size(); i++)
            {
                EventRecord er = eventRecords.get(i);
                if (comparingDate.get(Calendar.DAY_OF_YEAR) == er.getDateAndTime().get(Calendar.DAY_OF_YEAR)) {
                    if (er.isStatusENTER()) {
                        row.getCell(3).setCellValue(er.getStringTime());
                        startIndex = i;
                        written = true;
                    } else {
                            row.getCell(6).setCellValue(er.getStringTime());
                    }
                } else if (comparingDate.get(Calendar.DAY_OF_YEAR) < er.getDateAndTime().get(Calendar.DAY_OF_YEAR)) {
                    break;
                }
            }
            comparingDate.add(Calendar.DAY_OF_YEAR, 1);
        } while (comparingDate.get(Calendar.DAY_OF_YEAR) <= lastDate.get(Calendar.DAY_OF_YEAR) && comparingDate.get(Calendar.YEAR) == lastDate.get(Calendar.YEAR));

        return sheet;
    }
}


