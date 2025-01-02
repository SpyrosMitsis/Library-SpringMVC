package org.library.library.service;

import org.library.library.dto.BookLoanSummaryDto;
import org.library.library.model.BookLoan;

import java.util.List;

public interface CsvExportService {
    byte[] ExportStatisticsToCsv(List<BookLoan> bookLoanSummaryDto);
}
