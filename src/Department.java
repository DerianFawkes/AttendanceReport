import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

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

        sheet = wb.createSheet();
        wb.setSheetName(0, "Отчет");

        int rownum = 0;
        row = sheet.createRow(rownum);
        cell = row.createCell(3);
        cell.setCellStyle(cs1);
        cell.setCellValue("Отчет по посещаемости");

        for (Employee item : employers) {
            item.addContentToSheet(sheet, cs2, cs3);
        }

        sheet.autoSizeColumn(0);
        return wb;
    }

    public Workbook createReportForStorage () {
        Workbook wb = new XSSFWorkbook();
        return wb;
    }
}
