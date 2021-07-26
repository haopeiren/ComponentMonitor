package com.ffcs.util;

import com.ffcs.enums.ErrorCode;
import com.ffcs.exception.ServiceException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haopeiren
 * @since 2021/7/23
 */
@Slf4j
@UtilityClass
public class ExcelUtil {
    /**
     * 解析excel文件第一个shell的内容
     *
     * @param file excel文件
     * @return  文件内容
     * @throws IOException  获取文件流失败时抛IOException异常
     */
    public List<Map<String, String>> parseExcel(File file) throws IOException {
        return parseExcel(FileUtils.openInputStream(file));
    }

    /**
     * 解析excel文件第一个shell的内容
     *
     * @param is excel文件流
     * @return  文件内容
     */
    public List<Map<String, String>> parseExcel(InputStream is) {
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            log.error("failed to read file");
            throw new ServiceException(ErrorCode.READ_FILE_EXCEPTION);
        }
        Sheet sheet = workbook.getSheetAt(0);
        return parseSheet(sheet);
    }

    private List<Map<String, String>> parseSheet(Sheet sheet) {
        Row firstRow = sheet.getRow(0);
        int cellCount = firstRow.getPhysicalNumberOfCells();

        List<Map<String, String>> result = new ArrayList<>();
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rowCount; i++) {
            Row curRow = sheet.getRow(i);
            Map<String, String> dataMap = new HashMap<>();
            for (int j = 0; j < cellCount; j++) {
                Cell curCell = curRow.getCell(j);
                String value = getCellValue(curCell);
                dataMap.put(firstRow.getCell(j).getStringCellValue(), value);
            }
            result.add(dataMap);
        }
        return result;
    }

    private String getCellValue(Cell cell) {
        CellType type = cell.getCellType();
        String value = null;
        switch (type) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                value = new Double(cell.getNumericCellValue()).intValue() + "";
                break;
        }
        return value;
    }
}
