package org.prgrms.kdtspringdemo.file;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

@Component
public class CsvFileHandler {
    private final String filePath;
    private final Logger logger = LoggerFactory.getLogger(CsvFileHandler.class);

    public CsvFileHandler() {
        this.filePath = "src/main/resources/voucherList.csv";
    }

    public List<CSVRecord> readCSV() throws IOException {
        FileReader fileReader = new FileReader(filePath);
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());

        return csvParser.getRecords();
    }

    public void writeCSV(List<String[]> data) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT
                .withHeader(data.get(0)));

        for (int i = 1; i < data.size(); i++) {
            csvPrinter.printRecord(data.get(i));
        }

        csvPrinter.close();
    }

}
