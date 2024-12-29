package org.library.library.controller;

import org.library.library.model.LoanStatus;
import org.library.library.service.BookLoanService;
import org.library.library.service.CsvExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin")
public class CsvExportController {

    private final CsvExportService csvExportService;
    private final BookLoanService bookLoanService;

    public CsvExportController(CsvExportService csvExportService, BookLoanService bookLoanService) {
        this.csvExportService = csvExportService;
        this.bookLoanService = bookLoanService;
    }

    @GetMapping("/export-to-csv")
    public ResponseEntity<String> exportToCsv(
            @RequestParam(value = "filename", defaultValue = "loans.csv") String filename,
            @RequestParam(value = "loanStatus", defaultValue = "active") LoanStatus loanStatus,
            @RequestParam(value = "startDate", defaultValue = "") LocalDateTime startDate,
            @RequestParam(value = "endDate", defaultValue = "") LocalDateTime endDate,
            @RequestParam(value = "n", defaultValue = "10") int n
    ) {
        String filepath = "C:\\Users\\User\\IdeaProjects\\Library\\src\\main\\resources\\static\\csv\\" + filename;

        csvExportService.ExportStatisticsToCsv(bookLoanService.getTopNMostLoanedBooks(loanStatus,n, startDate, endDate), filepath);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body("CSV export completed successfully!");

    }
}
