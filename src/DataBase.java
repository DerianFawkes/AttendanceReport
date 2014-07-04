import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

/**
 * Created by Dima on 06.06.14.
 */
public class DataBase {
    static final int DEPARTMENTNAMES_COLUMN = 5;

    Workbook workbook;

    ArrayList<String> departmentsNames;

    public DataBase(Workbook workbook) {
        this.workbook = workbook;
    }

    public void setDepartmentsNames() {
        departmentsNames = new ArrayList();
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            boolean flag = false;
            Cell cell = row.getCell(DEPARTMENTNAMES_COLUMN);
            String cellValue = cell.getStringCellValue();

            for (String item :departmentsNames) {
                if (item.equals(cellValue)) {
                    flag = true;
                }
            }
            if (flag != true) {
                departmentsNames.add(cellValue);
            }
        }
    }

    public void printDepartmentsNames() {
    for (String item : departmentsNames)
        System.out.println(item);
    }
}
