package org.library.library.service.impl;

import com.opencsv.CSVWriter;
import org.library.library.dto.BookLoanDto;
import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.model.BookLoan;
import org.library.library.service.CsvExportService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CsvExportServiceImpl implements CsvExportService {

    @Override
    public byte[] ExportStatisticsToCsv(List<BookLoan> bookLoans) {
        String[] header = {"Id", "Borrowed at", "Due date", "Returned at", "ISBN", "Title", "Borrower", "Status"};

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(out))) {

            writer.writeNext(header);

            for (BookLoan loan : bookLoans) {
                String[] record = {
                        loan.getId().toString(),
                        loan.getBorrowedAt() != null ? loan.getBorrowedAt().toString() : "",
                        loan.getDueDate() != null ? loan.getDueDate().toString() : "",
                        loan.getReturnedAt() != null ? loan.getReturnedAt().toString() : "Not returned",
                        loan.getBook().getIsbn(),
                        loan.getBook().getTitle(),
                        loan.getBorrower().getUsername(),
                        loan.getStatus() != null ? loan.getStatus().name() : ""
                };
                writer.writeNext(record);
            }
            writer.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to export CSV", e);
        }
    }

}
