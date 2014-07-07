import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

/**
 * Created by Dima on 06.06.14.
 */
public class DataBase {
    static final int STATUS_COLUMN = 1;
    static final int EMPLOYEE_COLUMN = 3;
    static final int POST_COLUMN = 4;
    static final int DEPARTMENTNAMES_COLUMN = 5;
    static final int SERIALNUMBER_COLUMN = 6;
    static final int DATE_COLUMN = 7;
    static final int TIME_COLUMN = 8;
    
    Workbook workbook;

    ArrayList<Department> departments;

    public DataBase(Workbook workbook) {
        this.workbook = workbook;
    }

    public void fillDataBase() {
        departments = new ArrayList();
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            String[] cellsValue = new String[8];
            for (Cell cell: row) {
                int i = 0;
                cellsValue[i++] = cell.getStringCellValue();
            }

            boolean alreadyExists = false;

            for (Department item :departments) {
                String depname = item.getName();
                if (depname.equals(cellValue)) {
                    alreadyExists = true;
                }
            }
            if (alreadyExists != true) {
                departments.add(new Department(cellValue));
            }
        }
    }

    public void printDepartmentsNames() {
    for (Department item : departments)
        System.out.println(item.getName());
    }

    private boolean dep
}
