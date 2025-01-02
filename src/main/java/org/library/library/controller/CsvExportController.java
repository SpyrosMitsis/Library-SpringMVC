package org.library.library.controller;

import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.model.BookLoan;
import org.library.library.model.LoanStatus;
import org.library.library.service.BookLoanService;
import org.library.library.service.CsvExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class CsvExportController {

    private final CsvExportService csvExportService;
    private final BookLoanService bookLoanService;

    public CsvExportController(CsvExportService csvExportService, BookLoanService bookLoanService) {
        this.csvExportService = csvExportService;
        this.bookLoanService = bookLoanService;
    }

    @GetMapping("/loans/statistics")
    @ResponseBody
    public ResponseEntity<byte[]> exportStatistics(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        Date sqlStartDate = (startDate != null && !startDate.isEmpty())
                ? Date.valueOf(startDate)
                : Date.valueOf(LocalDate.now().minusYears(50));
        Date sqlEndDate = (endDate != null && !endDate.isEmpty())
                ? Date.valueOf(endDate)
                : Date.valueOf(LocalDate.now());

        List<BookLoan> bookLoans = bookLoanService.getAllLoans(sqlStartDate, sqlEndDate);
        System.out.println(bookLoans);
        byte[] csv = csvExportService.ExportStatisticsToCsv(bookLoans);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "statistics.csv");

        return new ResponseEntity<>(csv, headers, HttpStatus.OK);
    }
}
