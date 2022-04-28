package org.devcourse.voucher.customer.repository;

import com.opencsv.CSVReader;
import org.devcourse.voucher.configuration.FilePathProperties;
import org.devcourse.voucher.customer.Customer;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CsvBlacklistRepository implements BlacklistRepository {

    private final FilePathProperties filePath;

    public CsvBlacklistRepository(FilePathProperties filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        try (
                FileReader fileReader = new FileReader(filePath.getBlacklist());
                CSVReader csvReader = new CSVReader(fileReader);
        ){

        } catch (FileNotFoundException e) {
            // logging
        } catch (IOException e) {
            // logging
        }
    }
}

