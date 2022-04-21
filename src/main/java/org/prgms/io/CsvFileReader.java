package org.prgms.io;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.prgms.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CsvFileReader implements FileReader {
    private final String CSV_FILE_NAME = "customer_blacklist.csv";

    @Autowired
    private ApplicationContext context;

    @Override
    public List<Customer> readFile() throws CsvValidationException, IOException {
        List<Customer> users = new ArrayList<>();
        CSVReader reader = new CSVReader(new java.io.FileReader(context.getResource(CSV_FILE_NAME).getFile()));
        return readContentAndParse(reader);
    }

    @Override
    public List<Customer> readFile(File file) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new java.io.FileReader(file));
        return readContentAndParse(reader);
    }

    private List<Customer> readContentAndParse(CSVReader reader) throws CsvValidationException, IOException {
        String[] nextLine;
        List<Customer> users = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            users.add(new Customer(UUID.randomUUID(), nextLine[0], nextLine[2]));
        }
        return users;
    }
}
