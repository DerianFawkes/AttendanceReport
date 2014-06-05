/**
 * Created by Dima on 04.06.14.
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileInputStream;

public class AttendanceReport {
    public static void main(String[] args) {

        FileInputStream inp = new FileInputStream("C:\\Users\\Dima\\IdeaProjects\\Attendance Report\\Report.xls");
        System.out.println();
        Workbook wb = WorkbookFactory.create(inp);

    }
}
