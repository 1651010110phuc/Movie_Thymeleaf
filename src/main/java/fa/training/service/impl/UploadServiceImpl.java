package fa.training.service.impl;

import fa.training.service.UploadService;
import fa.training.utils.UploadUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class UploadServiceImpl implements UploadService {
    private final UploadUtils uploadUtils;

    public UploadServiceImpl(UploadUtils uploadUtils) {
        this.uploadUtils = uploadUtils;
    }

    @Override
    public List<Map<String, String>> upload(MultipartFile file) throws Exception {
        Path tempDir = Files.createTempDirectory("");
        File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
        file.transferTo(tempFile);
        Workbook workbook = WorkbookFactory.create(tempFile);
        Sheet sheet = workbook.getSheetAt(0);
        Supplier<Stream<Row>> rowStreamSupplier = uploadUtils.getRowStreamSupplier(sheet);

        Row headerRow = rowStreamSupplier.get().findFirst().get();
        List<String> headerCells = StreamSupport.stream(headerRow.spliterator(), false)
                .map(Cell::getStringCellValue)
                .collect(Collectors.toList());
        int colCount = headerCells.size();
        return rowStreamSupplier.get()
                .map(row -> {
                    List<String> cellList = uploadUtils.getStream(row).map(cell -> {
                        String cellVal = "";
                        if (cell.getCellType().equals(CellType.STRING)) {
                            cellVal = cell.getStringCellValue();
                        } else if (cell.getCellType().equals(CellType.NUMERIC)) {
                            cellVal = String.valueOf(cell.getNumericCellValue());
                            if (DateUtil.isCellDateFormatted(cell)) {
                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = cell.getDateCellValue();
                                cellVal = df.format(date);
                            }
                        }
                        return cellVal;
                    }).collect(Collectors.toList());

                    return IntStream.range(0, colCount)
                            .boxed()
                            .collect(Collectors.toMap(index -> headerCells.get(index),
                                    index -> cellList.get(index)));
                }).collect(Collectors.toList());
    }
}
