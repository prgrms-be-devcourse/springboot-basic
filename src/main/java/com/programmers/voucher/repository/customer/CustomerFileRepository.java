package com.programmers.voucher.repository.customer;

import com.programmers.voucher.model.customer.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerFileRepository implements CustomerRepository {

    private final String fileName;

    public CustomerFileRepository(@Value("${file.path.black}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Customer> findAllBlack() {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
            String content;
            while ((content = buffer.readLine()) != null) {
                customers.add(toCustomer(content));
            }
        } catch (IOException e1) {
            throw new RuntimeException("파일에서 바우처를 불러오지 못했습니다.");
        }
        return customers;
    }

    private Customer toCustomer(String content) {
        String[] contents = content.split("   ");
        return new Customer(contents[0], contents[1]);
    }
}
