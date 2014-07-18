import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

/**
 * Created by Dima on 06.06.14.
 */
public class DataBase {

    static final int DEPARTMENTNAMES_COLUMN = 5;
    static final int START_ROW_INDEX = 1;

    
    Workbook workbook;

    ArrayList<Department> departments;

    public DataBase(Workbook workbook) {
        this.workbook = workbook;
    }

    public void fillDataBase() {
        departments = new ArrayList();
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowIndex = sheet.getLastRowNum();
        for (int i = START_ROW_INDEX; i < lastRowIndex; i++) {
            Row row = sheet.getRow(i);
            String[] cellsValue = new String[9];
            int j = 0;
            for (Cell cell: row) {
                cellsValue[j] = cell.getStringCellValue();
                j++;
            }

            int checkresult = checkDepartment(cellsValue[DEPARTMENTNAMES_COLUMN]);
            if (checkresult == -1) {
                Department newDepartment = new Department(cellsValue[DEPARTMENTNAMES_COLUMN]);
                newDepartment.addEmployeeAndRecord(cellsValue);
                departments.add(newDepartment);
            }
            else {
                departments.get(checkresult).addEmployeeAndRecord(cellsValue);
            }
        }
    }

    private int checkDepartment (String department) {
        int i = 0;
        for (Department item : departments) {
            String depname = item.getName();
            if (depname.equals(department)) {
                return i;
            }
            i++;
        }
    return -1;
    }

    public void printDepartmentsNames() {
        for (Department item : departments) {
            System.out.println(item.getName());
        }


    }

    public void printDepartmentsEmployees(String depname) {
        for (Department item : departments) {
            if (depname.equals(item.getName())) {
                item.printEmployees();
            }
        }


    }

    public void eventTest () {
        //String[] zzz = {"08.07.2014",
        //departments.get(1).employers.get(1).addRecord();
        departments.get(1).employers.get(1).printEvents();
    }

    public Workbook exportByDepartment () {
        Workbook wb = new XSSFWorkbook();

        Sheet sheet;
        Row row;
        Cell cell;

        CellStyle cs1 = wb.createCellStyle();
        Font f1 = wb.createFont();
        f1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        f1.setFontHeightInPoints((short) 12);
        cs1.setFont(f1);

        CellStyle cs2 = wb.createCellStyle();
        Font f2 = wb.createFont();
        f2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        f2.setFontHeightInPoints((short) 10);
        cs2.setFont(f2);

        CellStyle cs3 = wb.createCellStyle();
        Font f3 = wb.createFont();
        f3.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        f3.setFontHeightInPoints((short) 8);
        cs3.setFont(f3);

        sheet = wb.createSheet();
        wb.setSheetName(0, "Отчет");

        int rownum = 0;
        row = sheet.createRow(rownum);
        cell = row.createCell(3);
        cell.setCellStyle(cs1);
        cell.setCellValue("Отчет по посещаемости");

        for (Department currentdep:departments) {

        }
        return wb;
    }

}
