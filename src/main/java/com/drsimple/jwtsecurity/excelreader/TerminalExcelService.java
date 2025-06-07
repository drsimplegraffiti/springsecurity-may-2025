package com.drsimple.jwtsecurity.excelreader;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TerminalExcelService {

    private static final Logger logger = LoggerFactory.getLogger(TerminalExcelService.class);

    private final TerminalRepository terminalRepository;

    public void readAndSaveFromExcel(MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            List<Terminal> terminals = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Terminal terminal = Terminal.builder()
                        .terminalId(row.getCell(0).getStringCellValue())
                        .serialNumber(row.getCell(1).getStringCellValue())
                        .isEnabled(row.getCell(2).getBooleanCellValue())
                        .isMapped(row.getCell(3).getBooleanCellValue())
                        .routeCode(row.getCell(4).getStringCellValue())
                        .build();

                terminals.add(terminal);
            }

            logger.info("Read {} terminals from the Excel file.", terminals.size());
            terminalRepository.saveAll(terminals);
        }
    }
}
