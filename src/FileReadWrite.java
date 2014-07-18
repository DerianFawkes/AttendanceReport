/**
 * Created by Dima on 05.06.14.
 */
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class FileReadWrite {

    public static Workbook read(String path) throws IOException, InvalidFormatException {
        FileInputStream fileInputStream = new FileInputStream(path);
        Workbook wb = WorkbookFactory.create(fileInputStream);
        fileInputStream.close();
        return wb;
    }

    public static void write(Workbook wb, String path) throws IOException, InvalidFormatException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        wb.write(fileOutputStream);
        fileOutputStream.close();
    }
}
