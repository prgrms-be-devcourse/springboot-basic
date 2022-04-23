package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerGrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Primary
@Qualifier("csv")
@Repository
public class CsvCustomerRepository implements CustomerRepository {

    private static final String CSV_FILENAME = "customer_blacklist.csv";
    private static final String[] CSV_HEADER = {"customerId", "customerName", "customerGrade"};

    private static final Logger logger = LoggerFactory.getLogger(CsvCustomerRepository.class);

    @Value("${csv.file-path}")
    private String csvFilePath;

    public List<Customer> findAll() {
        return null;
    }

    @Override
    public List<Customer> findAllByCustomerGrade(CustomerGrade customerGrade) {
        List<Customer> customers = new ArrayList<>();
        try (ICsvMapReader beanReader = new CsvMapReader(new FileReader(getPathCsvFile()), CsvPreference.STANDARD_PREFERENCE)) {

            Map<String, String> readData;
            while ((readData = beanReader.read(CSV_HEADER)) != null) {
                CustomerGrade grade = CustomerGrade.valueOf(readData.get(CSV_HEADER[2]));
                if (grade != customerGrade) {
                    continue;
                }

                UUID customerId = UUID.fromString(readData.get(CSV_HEADER[0]));
                String customerName = readData.get(CSV_HEADER[1]);

                customers.add(new Customer(customerId, customerName));
            }
        } catch (IOException | SuperCsvException e) {
            logger.error("failed to get black-list in csv-file : {}", e.getMessage(), e);
        }
        return customers;
    }

    public String getPathCsvFile() {
        File file = new File(csvFilePath);
        String path = file.getParentFile().getPath();
        return Path.of(path, CSV_FILENAME).toString();
    }
}
