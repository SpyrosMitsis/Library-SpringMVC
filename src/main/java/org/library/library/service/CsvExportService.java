package org.library.library.service;

import org.library.library.dto.BookLoanSummaryDto;

import java.util.List;

public interface CsvExportService {
    void ExportStatisticsToCsv(List<BookLoanSummaryDto> bookLoanSummaryDto, String fileName);
}
