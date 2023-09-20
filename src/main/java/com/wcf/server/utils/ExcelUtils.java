package com.wcf.server.utils;

import com.wcf.server.base.response.BizException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    public static List<HashMap<String, Object>> parse(MultipartFile file) throws IOException {
        List<HashMap<String, Object>> dataList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        HashMap<String, Object> data;

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

                // 特殊单元格处理 - 空单元格
                if (cell == null) {
                    data.put(titleList.get(curCol), null);
                    continue;
                }

                CellType cellType = cell.getCellType();
                if (cellType == CellType._NONE || cellType == CellType.BLANK) {
                    data.put(titleList.get(curCol), null);
                } else if (cellType == CellType.NUMERIC) {
                    // 特殊单元格处理 - 日期
                    if (DateUtil.isCellDateFormatted(cell)) {
                        data.put(titleList.get(curCol), cell.getLocalDateTimeCellValue());
                    } else {
                        data.put(titleList.get(curCol), cell.getNumericCellValue());
                    }
                } else if (cellType == CellType.STRING) {
                    data.put(titleList.get(curCol), cell.getStringCellValue());
                } else {
                    throw new BizException("第 " + (curRow + 1) + " 行, 第 " + (curCol + 1) + " 列的单元格检测到异常值, 如果是公式, 请粘贴为值");
                }
            }
            dataList.add(data);
        }

        workbook.close();
        return dataList;
    }
}
