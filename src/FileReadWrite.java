/**
 * Created by Dima on 05.06.14.
 */
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileReadWrite {
    private FileInputStream fileInputStream;

    private FileOutputStream fileOutputStream;

    public FileReadWrite() {
        //this.path = wb;
    }

    public Sheet read(String path) throws IOException, InvalidFormatException {
        fileInputStream = new FileInputStream(path);
        Workbook wb = WorkbookFactory.create(fileInputStream);
        fileInputStream.close();
        return wb.getSheetAt(0);
    }

    public void write(Workbook wb, String path) throws IOException, InvalidFormatException {
        fileOutputStream = new FileOutputStream(path);
        wb.write(fileOutputStream);
        fileInputStream.close();
    }
}
