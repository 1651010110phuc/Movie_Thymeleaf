package fa.training.dto;

import org.apache.poi.sl.usermodel.Sheet;

import java.io.Closeable;

public interface Workbook extends Closeable, Iterable<Sheet> {
        int PICTURE_TYPE_EMF = 2;
        int PICTURE_TYPE_WMF = 3;
        int PICTURE_TYPE_PICT = 4;
        int PICTURE_TYPE_JPEG = 5;
        int PICTURE_TYPE_PNG = 6;
        int PICTURE_TYPE_DIB = 7;
}
