import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Dima on 04.07.14.
 */
public class Department {
    static final int SERIALNUMBER_COLUMN = 6;
    static final int EMPLOYEE_COLUMN = 3;
    static final int POST_COLUMN = 4;
    static DataBase db = null;

    String name;
    ArrayList<Employee> employers;

    public Department (String name, DataBase db) {
        setName(name);
        employers = new ArrayList<Employee>();
        if (db == null)this.db = db;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void addEmployeeAndRecord (String[] cellsValue) {
        int checkresult = checkEmployee(cellsValue[SERIALNUMBER_COLUMN]);
        if (checkresult == -1) {
            Employee newEmployee = new Employee(cellsValue[SERIALNUMBER_COLUMN], cellsValue[EMPLOYEE_COLUMN], cellsValue[POST_COLUMN], this);
            newEmployee.addRecord(cellsValue);
            employers.add(newEmployee);
        }
        else {
            employers.get(checkresult).addRecord(cellsValue);
        }
    }

    public int checkEmployee (String serialnumber) {
        int i = 0;
        for (Employee item : employers) {
            String empSN = item.getSerialNumber();
            if (empSN.equals(serialnumber)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void printEmployees() {
        for( Employee item : employers) {
            System.out.println(item.getName()+" "+item.getSerialNumber());
        }
    }

    public void startSorting () {
        System.out.println("Sorting"+this.getName());
        printEmployees();
        sortEmployees(employers, 0, employers.size()-1);

    }

    private void sortEmployees (ArrayList<Employee> arrayList,int leftIndex, int rightIndex) {

        int i = leftIndex;
        int j = rightIndex;

        Employee temp, p;

        p = arrayList.get(leftIndex+(rightIndex-leftIndex)/2);

         do {
            String stringi, stringj, stringp;
            stringi = arrayList.get(i).getName();
            stringj = arrayList.get(j).getName();
            stringp = p.getName();
            while ( Util.compareStrings(stringi, stringp) ) {
                stringi = arrayList.get(i).getName();
                System.out.println("i++");
                i++;
                if (i >= arrayList.size()) break;
            }
            while ( !Util.compareStrings(stringj, stringp)) {
                stringj = arrayList.get(j).getName();
                System.out.println("j--");
                j--;
                if (j < 0) break;
            }

            if (i <= j) {
                System.out.println("change");
                temp = arrayList.get(i);
                arrayList.set(i, arrayList.get(j));
                arrayList.set(j, temp);
                i++; j--;
            }
        } while ( i<=j );


        // рекурсивные вызовы, если есть, что сортировать
        if ( leftIndex < i - 1 ) {sortEmployees(arrayList,leftIndex, i - 1);
            System.out.println("---recursive left side");}
        if ( i < rightIndex ) {sortEmployees(arrayList, i, rightIndex);
            System.out.println("---recursive right side");}

    }


    public Workbook createReportByDepartment () {
        Workbook wb = new XSSFWorkbook();

        Sheet sheet;
        Row row;
        Cell cell;

        CellStyle cs1 = wb.createCellStyle();
        Font f1 = wb.createFont();
        f1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        f1.setFontHeightInPoints((short) 12);
        f1.setFontName("Arial");
        cs1.setFont(f1);

        CellStyle cs2 = wb.createCellStyle();
        Font f2 = wb.createFont();
        f2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        f2.setFontHeightInPoints((short) 10);
        f2.setFontName("Arial");
        cs2.setFont(f2);

        CellStyle cs3 = wb.createCellStyle();
        Font f3 = wb.createFont();
        f3.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        f3.setFontHeightInPoints((short) 8);
        f3.setFontName("Arial");
        cs3.setFont(f3);

        CellStyle cs4 = wb.createCellStyle();
        cs4.setFont(f2);
        cs4.setAlignment(CellStyle.ALIGN_CENTER);
        cs4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs4.setWrapText(true);
        cs4.setBorderBottom(CellStyle.BORDER_THIN);
        cs4.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cs4.setBorderLeft(CellStyle.BORDER_THIN);
        cs4.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cs4.setBorderRight(CellStyle.BORDER_THIN);
        cs4.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cs4.setBorderTop(CellStyle.BORDER_THIN);
        cs4.setTopBorderColor(IndexedColors.BLACK.getIndex());

        CellStyle cs5 = wb.createCellStyle();
        Font f5 = wb.createFont();
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);
        f5.setFontHeightInPoints((short) 8);
        f5.setFontName("Arial");
        cs5.setFont(f5);
        cs5.setAlignment(CellStyle.ALIGN_CENTER);
        cs5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs5.setBorderBottom(CellStyle.BORDER_THIN);
        cs5.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cs5.setBorderLeft(CellStyle.BORDER_THIN);
        cs5.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cs5.setBorderRight(CellStyle.BORDER_THIN);
        cs5.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cs5.setBorderTop(CellStyle.BORDER_THIN);
        cs5.setTopBorderColor(IndexedColors.BLACK.getIndex());

        CellStyle cs6 = wb.createCellStyle();
        Font f6 = wb.createFont();
        f6.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        f6.setFontHeightInPoints((short) 8);
        f6.setFontName("Arial");
        cs6.setFont(f6);
        cs6.setBorderBottom(CellStyle.BORDER_THIN);
        cs6.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cs6.setBorderLeft(CellStyle.BORDER_THIN);
        cs6.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cs6.setBorderRight(CellStyle.BORDER_THIN);
        cs6.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cs6.setBorderTop(CellStyle.BORDER_THIN);
        cs6.setTopBorderColor(IndexedColors.BLACK.getIndex());

        sheet = wb.createSheet();
        wb.setSheetName(0, "Отчет");

        int rownum = 0;
        row = sheet.createRow(rownum);
        cell = row.createCell(2);
        cell.setCellStyle(cs1);
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 2, 5));
        cell.setCellValue("Отчет по посещаемости");


        for (Employee item : employers) {
            item.addContentToSheet(sheet, cs2, cs3, cs4, cs5, cs6);
        }

        sheet.autoSizeColumn(0);
        return wb;
    }

    public Workbook createReportForStorage () {
        Workbook wb = new XSSFWorkbook();

        Sheet sheet;
        Row row;
        Cell cell;

        CellStyle cs1 = wb.createCellStyle();
        Font f1 = wb.createFont();
        f1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        f1.setFontHeightInPoints((short) 12);
        f1.setFontName("Arial");
        cs1.setFont(f1);

        CellStyle cs2 = wb.createCellStyle();
        Font f2 = wb.createFont();
        f2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        f2.setFontHeightInPoints((short) 10);
        f2.setFontName("Arial");
        cs2.setFont(f2);
        cs2.setWrapText(true);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        cs2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs2.setWrapText(true);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setTopBorderColor(IndexedColors.BLACK.getIndex());

        CellStyle cs3 = wb.createCellStyle();
        Font f3 = wb.createFont();
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);
        f3.setFontHeightInPoints((short) 8);
        f3.setFontName("Arial");
        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);
        cs3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs3.setBorderBottom(CellStyle.BORDER_THIN);
        cs3.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cs3.setBorderLeft(CellStyle.BORDER_THIN);
        cs3.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cs3.setBorderRight(CellStyle.BORDER_THIN);
        cs3.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cs3.setBorderTop(CellStyle.BORDER_THIN);
        cs3.setTopBorderColor(IndexedColors.BLACK.getIndex());

        CellStyle cs4 = wb.createCellStyle();
        Font f4 = wb.createFont();
        f4.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        f4.setFontHeightInPoints((short) 8);
        f4.setFontName("Arial");
        cs4.setFont(f4);
        cs4.setBorderBottom(CellStyle.BORDER_THIN);
        cs4.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cs4.setBorderLeft(CellStyle.BORDER_THIN);
        cs4.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cs4.setBorderRight(CellStyle.BORDER_THIN);
        cs4.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cs4.setBorderTop(CellStyle.BORDER_THIN);
        cs4.setTopBorderColor(IndexedColors.BLACK.getIndex());

        sheet = wb.createSheet();
        wb.setSheetName(0, "Отчет");

        int rownum = 0;
        row = sheet.createRow(rownum);
        cell = row.createCell(2);
        cell.setCellStyle(cs1);
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 2, 5));
        cell.setCellValue("Отчет по посещаемости");

        for (Employee item : employers) {
            item.addContentToSheetForStorage(sheet, cs2, cs3, cs4);
        }

        return wb;
    }
}
