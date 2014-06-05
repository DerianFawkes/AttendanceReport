/**
 * Created by Dima on 05.06.14.
 */
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileReadWrite {
    private FileInputStream fileInputStream;

    private FileOutputStream fileOutputStream;

    private String path;

    public FileReadWrite(String path) {
        this.path = path;
    }

    public FileReadWrite(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public void read() throws IOException {
        fileInputStream = new FileInputStream(path);
        fileInputStream.close();
    }

    public void write(Workbook workbook, String path) throws IOException {
        fileOutputStream = new FileOutputStream(path);
        fileInputStream.close();
    }
}
