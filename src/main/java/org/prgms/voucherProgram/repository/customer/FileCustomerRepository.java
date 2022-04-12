package org.prgms.voucherProgram.repository.customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
    private static final String ERROR_WRONG_FILE = "[ERROR] 올바른 고객 파일이 아닙니다.";
    private static final int USER_ID_INDEX = 0;
    private static final int USER_NAME_INDEX = 1;

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
            UUID userId = UUID.fromString(splitLine[USER_ID_INDEX].trim());
            String userName = splitLine[USER_NAME_INDEX].trim();
            storage.put(userId, new Customer(userId, userName));
        }
    }

    @Override
    public List<Customer> findBlackCustomers() {
        return new ArrayList<>(storage.values());
    }
}
