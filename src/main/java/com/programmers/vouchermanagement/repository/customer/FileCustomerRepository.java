package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.common.ErrorMessage;
import com.programmers.vouchermanagement.domain.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private static final String CSV_SEPARATOR = ",";

    private final String blacklistFilePath;
    private final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);

    public FileCustomerRepository(@Value("${csv.file.customer.blacklist_path}") String blacklistFilePath) {
        this.blacklistFilePath = blacklistFilePath;
    }

    @Override
    public List<Customer> findAllBannedCustomers() {
        return readBlackListFile();
    }

    private List<Customer> readBlackListFile() {
        String line;
        List<Customer> bannedCustomers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(blacklistFilePath))) {
            while ((line = br.readLine()) != null) {
                String[] strings = line.split(CSV_SEPARATOR);
                Customer customer = new Customer(
                        UUID.fromString(strings[0]),
                        strings[1],
                        LocalDateTime.parse(strings[2]),
                        Boolean.parseBoolean(strings[3])
                );
                bannedCustomers.add(customer);
            }
        } catch (FileNotFoundException e) {
            logger.warn(MessageFormat.format("{0} : {1}", ErrorMessage.FILE_NOT_FOUND_MESSAGE.getMessage(), blacklistFilePath));
        } catch (IOException e) {
            logger.error("Error occurred at FileReader: ", e);
        }
        return bannedCustomers;
    }
}
