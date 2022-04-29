package org.devcourse.voucher.customer.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.devcourse.voucher.configuration.FilePathProperties;
import org.devcourse.voucher.customer.Customer;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.devcourse.voucher.voucher.repository.CsvVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CsvBlacklistRepository implements BlacklistRepository {

    private final FilePathProperties filePath;
    private static final int NAME = 0;
    private static final int AGE = 1;
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
                customers.add(new Customer(record[NAME], Integer.parseInt(record[AGE])));
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

