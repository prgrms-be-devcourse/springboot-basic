package org.devcourse.voucher.customer.repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.devcourse.voucher.configuration.FilePathProperties;
import org.devcourse.voucher.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CsvBlacklistRepository implements BlacklistRepository {

    private final FilePathProperties filePath;
    private static final int CUSTOMER_ID = 0;
    private static final int NAME = 1;
    private final Logger logger = LoggerFactory.getLogger(CsvBlacklistRepository.class);

    public CsvBlacklistRepository(FilePathProperties filePath) {
        this.filePath = filePath;
    }

    @Override
    public Customer insert(Customer customer) {
        logger.info("Repository : Record a blacklist write");
        File file = new File(filePath.getBlacklist());
        try (
                FileWriter outputFile = new FileWriter(file, true);
                CSVWriter writer = new CSVWriter(outputFile);
        ) {
            String[] customerInfo = {
                    String.valueOf(customer.getCustomerId()), customer.getName()
            };
            writer.writeNext(customerInfo);
        } catch (IOException e) {
            logger.error(MessageFormat.format("Failed to write data to file -> {0}", e.getMessage()));
        }
        return customer;
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
                customers.add(new Customer(UUID.fromString(record[CUSTOMER_ID]), record[NAME]));
            }
        }  catch (FileNotFoundException e) {
            logger.error(MessageFormat.format("File not found -> {0}", e.getMessage()));
        } catch (CsvValidationException e) {
            logger.error(MessageFormat.format("This is not a valid format CSV file -> {0}", e.getMessage()));
        } catch (IOException e) {
            logger.error(MessageFormat.format("Failed to read data from file -> {0}", e.getMessage()));
        }
        return customers;
    }
}

