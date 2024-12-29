package org.library.library.service.impl;

import com.opencsv.CSVWriter;
import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.service.CsvExportService;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CsvExportServiceImpl implements CsvExportService {

    @Override
    public void ExportStatisticsToCsv(List<BookLoanSummaryDto> bookLoanSummaryDto, String filePath) {
        String[] header = bookLoanSummaryDto.stream().map(dto -> dto.getClass().getDeclaredFields())
                .flatMap(Stream::of)
                .map(Field::getName)
                .distinct()
                .toArray(String[]::new);

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(header);

            for (BookLoanSummaryDto loan : bookLoanSummaryDto) {
                String[] record = {
                        loan.getIsbn(),
                        loan.getTitle(),
                        String.valueOf(loan.getLoanCount())
                };
                writer.writeNext(record);
            }
            System.out.println("CSV file has been successfully exported.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to CSV file.");
        }
    }
}
