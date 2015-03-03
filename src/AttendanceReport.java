/**
 * Created by Dima on 04.06.14.
 */
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class AttendanceReport {
    public static DataBase dataBase;
    static GUI newGUI;
    static Settings settings;

    public static void main(String[] args) throws IOException, InvalidFormatException{
        importSettings();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }

public static void createGUI() {
        GUI newGUI = new GUI();
        }

    public static GUI getGUI() {
        return newGUI;
    }

    public static void importSettings() throws IOException {
        settings = new Settings();
    }
}
