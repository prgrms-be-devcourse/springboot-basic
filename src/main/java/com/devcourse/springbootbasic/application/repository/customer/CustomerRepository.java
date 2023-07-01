package com.devcourse.springbootbasic.application.repository.customer;

import com.devcourse.springbootbasic.application.converter.CustomerConverter;
import com.devcourse.springbootbasic.application.domain.customer.Customer;
import com.devcourse.springbootbasic.application.io.CsvReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {

    @Value("${settings.blackCustomerPath}")
    private String filepath;

    private final CsvReader csvReader;

    public CustomerRepository(CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    public List<Customer> findAll() {
        System.out.println(filepath);
        return csvReader.readFile(filepath)
                .stream()
                .map(CustomerConverter::convertCsvToCustomer)
                .toList();
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
