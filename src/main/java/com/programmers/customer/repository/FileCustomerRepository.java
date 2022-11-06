package com.programmers.customer.repository;

import com.programmers.customer.Customer;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class FileCustomerRepository {
//    @Value("${kdt.customer.blacklist.save-path}")
    private String blackFilePath = "./blacklist.csv";
    private BufferedReader bufferedReader;

    public List<Customer> findAllBlackList() {
        List<Customer> customers = new ArrayList<>();

        try {
            bufferedReader = Files.newBufferedReader(Paths.get(blackFilePath));
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                customers.add(new Customer(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return customers;
    }
}
