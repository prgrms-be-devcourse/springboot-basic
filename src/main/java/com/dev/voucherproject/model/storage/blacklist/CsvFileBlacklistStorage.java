package com.dev.voucherproject.model.storage.blacklist;

import com.dev.voucherproject.model.customer.Customer;
import com.dev.voucherproject.model.storage.io.CsvFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public class CsvFileBlacklistStorage implements BlacklistStorage {
    @Value("${blacklist.path}")
    private String path;
    @Value("${blacklist.filename}")
    private String filename;
    private final CsvFileReader csvFileReader;

    public CsvFileBlacklistStorage(CsvFileReader csvFileReader) {
        this.csvFileReader = csvFileReader;
    }

    @Override
    public List<Customer> findAll() {
        return csvFileReader.readAllLines(path, filename).stream()
                .map(this::csvFileToCustomer)
                .toList();
    }

    private Customer csvFileToCustomer(final String line) {
        String[] data = line.split(",");
        return new Customer(UUID.fromString(data[0]), data[1]);
    }
}
