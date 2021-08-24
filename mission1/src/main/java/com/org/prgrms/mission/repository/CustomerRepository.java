package com.org.prgrms.mission.repository;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.org.prgrms.mission.model.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerRepository {
    Customer insert(Customer customer, String filePath) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;

    List<Customer> ShowList(String filePath);


}
