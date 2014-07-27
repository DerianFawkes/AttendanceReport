/**
 * Created by Dima on 04.06.14.
 */
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class AttendanceReport {
    public static void main(String[] args) throws IOException, InvalidFormatException{

        DataBase dataBase;
        String destinationFolder = ".\\";

        dataBase = new DataBase(FileReadWrite.read("F:\\Users\\Fawkes\\IdeaProjects\\AttendanceReport\\src\\Report.xls"));



        dataBase.fillDataBase();
        //dataBase.exportReports(destinationFolder);
        dataBase.eventTest();
    }
}
