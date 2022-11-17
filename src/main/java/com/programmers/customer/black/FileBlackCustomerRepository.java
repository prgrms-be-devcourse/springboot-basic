package com.programmers.customer.black;

import com.programmers.config.properties.BlackCustomerProperties;
import com.programmers.customer.Customer;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Profile("file")
@Repository
public class FileBlackCustomerRepository implements BlackCustomerRepository {
    private final Logger logger = getLogger(FileBlackCustomerRepository.class);

    private final String blackFilePath;
    private BufferedReader bufferedReader;

    public FileBlackCustomerRepository(BlackCustomerProperties properties) {
        this.blackFilePath = properties.getSavePath();
    }

    @Override
    public List<Customer> findAllBlackList() {

        List<Customer> customers = new ArrayList<>();

        try {
            bufferedReader = Files.newBufferedReader(Paths.get(blackFilePath));
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                customers.add(new Customer(UUID.randomUUID(), line));
            }

        } catch (IOException e) {
            logger.error("블랙리스트 조회 에러 발생");
        } finally {
            clear();
        }
        return customers;
    }


    private void clear() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            logger.error("리소스 정리 중 에러 발생");
        }
    }
}
