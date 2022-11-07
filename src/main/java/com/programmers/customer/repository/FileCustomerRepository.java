package com.programmers.customer.repository;

import com.programmers.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileCustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);

    //    @Value("${kdt.customer.blacklist.save-path}")
    private final String blackFilePath = "./blacklist.csv";
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
            logger.error("블랙리스트 조회 에러 발생");
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                logger.error("리소스 정리 중 에러 발생");
            }
        }

        return customers;
    }
}
