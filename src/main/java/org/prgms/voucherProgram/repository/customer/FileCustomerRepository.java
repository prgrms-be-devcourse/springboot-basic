package org.prgms.voucherProgram.repository.customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.prgms.voucherProgram.entity.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private static final String DELIMITER = ",";
    private static final int CUSTOMER_ID_INDEX = 0;
    private static final int CUSTOMER_NAME_INDEX = 1;
    private static final int CUSTOMER_EMAIL_INDEX = 2;
    private static final int CUSTOMER_LAST_LOGIN_TIME_INDEX = 3;
    private static final int CUSTOMER_CREATED_TIME_INDEX = 4;
    private static final String ERROR_WRONG_FILE = "[ERROR] 올바른 고객 파일이 아닙니다.";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String customerFilePath;
    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();

    public FileCustomerRepository(@Value("${file.path.blacklist}") String customerFilePath) {
        this.customerFilePath = customerFilePath;
        readBlackCustomers();
    }

    private void readBlackCustomers() {
        try (BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ClassPathResource(customerFilePath).getInputStream()))) {
            addBlackCustomers(storage, bufferedReader);
        } catch (IOException e) {
            logger.error("Fail to find a blacklist file");
            throw new IllegalArgumentException(ERROR_WRONG_FILE);
        }
    }

    private void addBlackCustomers(Map<UUID, Customer> storage, BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] splitLine = line.split(DELIMITER);
            UUID customerId = UUID.fromString(splitLine[CUSTOMER_ID_INDEX].trim());
            String name = splitLine[CUSTOMER_NAME_INDEX].trim();
            String email = splitLine[CUSTOMER_EMAIL_INDEX].trim();
            LocalDateTime lastLoginTime = LocalDateTime.parse(splitLine[CUSTOMER_LAST_LOGIN_TIME_INDEX], formatter);
            LocalDateTime createdTime = LocalDateTime.parse(splitLine[CUSTOMER_CREATED_TIME_INDEX], formatter);
            storage.put(customerId, new Customer(customerId, name, email, lastLoginTime, createdTime));
        }
    }

    @Override
    public List<Customer> findBlackCustomers() {
        if (storage.isEmpty()) {
            return Collections.emptyList();
        }

        return new ArrayList<>(storage.values());
    }
}
