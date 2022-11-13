package com.programmers.voucher.repository.customer;

import com.programmers.voucher.model.customer.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerRepository {

    private final String fileName;

    public CustomerRepository(@Value("${file.path.black}") String fileName) {
        this.fileName = fileName;
    }

    public List<Customer> findAllBlack() throws IOException{
        BufferedReader buffer = new BufferedReader(new FileReader(fileName));
        return buffer.lines()
                .map(line -> line.split("   "))
                .map(content -> new Customer(content[0], content[1]))
                .collect(Collectors.toList());
    }
}
