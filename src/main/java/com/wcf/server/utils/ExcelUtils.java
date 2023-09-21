package com.wcf.server.utils;

import com.wcf.server.base.response.BizException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class ExcelUtils {
    private LocalDateTime date;
    private String value;
    private Boolean blank = false;
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    private void setDate(LocalDateTime date) {
        this.date = date;
    }

    private void setValue(String value) {
        this.value = value;
    }

    private void setBlank(Boolean blank) {
        this.blank = blank;
    }

    public Date getDate() {
        if (isBlank()) return null;

        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(this.date.atZone(zoneId).toInstant());
    }

    public String getString() {
        if (isBlank()) return null;

        return this.value;
    }

    public Integer getInteger() {
        if (isBlank()) return null;

        return (int) Double.parseDouble(this.value);
    }

    public BigDecimal getBigDecimal() {
        if (isBlank()) return null;

        return new BigDecimal(this.value);
    }

    public Boolean isBlank() {
        return this.blank;
    }


    public static List<HashMap<String, ExcelUtils>> parse(MultipartFile file) throws IOException {
        List<HashMap<String, ExcelUtils>> dataList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        HashMap<String, ExcelUtils> data;
        ExcelUtils excelUtils;

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        // 读取第一个 sheet
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
        Cell cell;
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = 0;
        for (int curRow = 0; curRow < rowCount; curRow++) {
            row = sheet.getRow(curRow);
            data = new HashMap<>();
            // 读取第一行的数据作为表头
            if (curRow == 0) {
                List<String> tempTitleList = new ArrayList<>();

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    tempTitleList.add(cell.getStringCellValue());
                }

                // 保留顺序去重
                Set<String> tempTitleSet = new LinkedHashSet<>(tempTitleList);
                titleList = new ArrayList<>(tempTitleSet);
                colCount = titleList.size();
                // 没有读取到表头信息, 结束
                if (colCount == 0) break;
                logger.info("解析 Excel 文件 \"{}\",共 {} 行, {} 列, 数据: {} 条", file.getOriginalFilename(), rowCount, colCount, rowCount - 1);
                continue;
            }

            for (int curCol = 0; curCol < colCount; curCol++) {
                cell = row.getCell(curCol);
                excelUtils = new ExcelUtils();

                // 特殊单元格处理 - 空单元格
                if (cell == null) {
                    excelUtils.setBlank(true);
                    data.put(titleList.get(curCol), excelUtils);
                    continue;
                }

                CellType cellType = cell.getCellType();
                if (cellType == CellType._NONE || cellType == CellType.BLANK) {
                    excelUtils.setBlank(true);
                } else if (cellType == CellType.NUMERIC) {
                    // 特殊单元格处理 - 日期
                    if (DateUtil.isCellDateFormatted(cell)) {
                        excelUtils.setDate(cell.getLocalDateTimeCellValue());
                    } else {
                        excelUtils.setValue(String.valueOf(cell.getNumericCellValue()));
                    }
                } else if (cellType == CellType.STRING) {
                    excelUtils.setValue(cell.getStringCellValue());
                } else {
                    throw new BizException("第 " + (curRow + 1) + " 行, 第 " + (curCol + 1) + " 列的单元格检测到异常值, 如果是公式, 请粘贴为值");
                }

                data.put(titleList.get(curCol), excelUtils);
            }
            dataList.add(data);
        }

        workbook.close();
        return dataList;
    }
}
