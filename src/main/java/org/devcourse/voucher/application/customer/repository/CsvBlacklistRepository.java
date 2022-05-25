package org.devcourse.voucher.application.customer.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.devcourse.voucher.core.configuration.FilePathProperties;
import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.model.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CsvBlacklistRepository implements BlacklistRepository {

    private final FilePathProperties filePath;
    private static final int CUSTOMER_ID = 0;
    private static final int NAME = 1;
    private static final int EMAIL = 2;
    private final Logger logger = LoggerFactory.getLogger(CsvBlacklistRepository.class);

    public CsvBlacklistRepository(FilePathProperties filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        logger.info("Repository : Blacklist File Inquiry");
        try (
                FileReader fileReader = new FileReader(filePath.getBlacklist());
                CSVReader csvReader = new CSVReader(fileReader);
        ){
            String[] record;

            while((record = csvReader.readNext()) != null) {
                customers.add(new Customer(UUID.fromString(record[CUSTOMER_ID]),
                        record[NAME],
                        new Email(record[EMAIL])));
            }
        }  catch (FileNotFoundException e) {
            logger.error("File not found -> {}", e.getMessage());
        } catch (CsvValidationException e) {
            logger.error("This is not a valid format CSV file -> {}", e.getMessage());
        } catch (IOException e) {
            logger.error("Failed to read data from file -> {}", e.getMessage());
        }
        return customers;
    }
}

