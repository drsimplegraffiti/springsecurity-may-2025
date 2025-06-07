package com.drsimple.jwtsecurity.excelreader;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/terminals")
public class TerminalController {

    private final TerminalExcelService terminalExcelService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTerminals(@RequestParam("file") MultipartFile file) {
        try {
            terminalExcelService.readAndSaveFromExcel(file);
            return ResponseEntity.ok("Terminals uploaded and saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process file: " + e.getMessage());
        }
    }
}
