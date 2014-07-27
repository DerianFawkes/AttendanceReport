import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
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
        for(Department dep:departments) {
            System.out.println(dep.getName());
            for(Employee emp: dep.employers) {
                System.out.println(emp.getName());
                for(EventRecord evt: emp.eventRecords) {
                    evt.printFields();
                }
            }
        }
    }

    public void exportReports (String destinationFolder) throws IOException, InvalidFormatException {
        for (Department currentdep:departments) {
            if (!currentdep.getName().equals("СКЛАД")) {
                Workbook wb = currentdep.createReportByDepartment();
                String path = destinationFolder + currentdep.getName() + ".xlsx";
                FileReadWrite.write(wb, path);
            } else {
                Workbook wb = currentdep.createReportForStorage();
                String path = destinationFolder + currentdep.getName() + ".xlsx";
                FileReadWrite.write(wb, path);
            }
        }
    }

}
