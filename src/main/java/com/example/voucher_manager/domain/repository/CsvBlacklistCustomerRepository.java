package com.example.voucher_manager.domain.repository;

import com.example.voucher_manager.domain.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CsvBlacklistCustomerRepository implements CustomerRepository{
    private static final Logger log = LoggerFactory.getLogger(CsvBlacklistCustomerRepository.class);
    private final ClassLoader classLoader = getClass().getClassLoader();

    private final String IO_EXCEPTION_MESSAGE = "I/O ERROR : Error occured during reading blacklist csv file.";

    @Value("${file.path.blacklist}")
    private String blacklistFileName;

    @Override
    public List<Customer> findAll() {
        InputStream inputStream = classLoader.getResourceAsStream(blacklistFileName);
        List<Customer> customerList = new ArrayList<>();

        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            Integer lineCount = 0;
            while ((line = reader.readLine()) != null) {
                if (lineCount == 0){
                    lineCount += 1;
                    continue;
                }
                String[] customerInformation = line.split(",");
                customerList.add(new Customer(UUID.fromString(customerInformation[0]), customerInformation[1]));
            }
        } catch (IOException e) {
            log.error(IO_EXCEPTION_MESSAGE);
            return List.of();
        }
        return customerList;
    }
}
