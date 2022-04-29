package org.devcourse.voucher.customer.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.devcourse.voucher.configuration.FilePathProperties;
import org.devcourse.voucher.customer.Customer;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CsvBlacklistRepository implements BlacklistRepository {

    private final FilePathProperties filePath;
    private static final int NAME = 0;
    private static final int AGE = 1;

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
            String[] record;

            while((record = csvReader.readNext()) != null) {
                customers.add(new Customer(record[NAME], Integer.parseInt(record[AGE])));
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (CsvValidationException e) {
//            e.printStackTrace();
        }
        return customers;
    }
}

