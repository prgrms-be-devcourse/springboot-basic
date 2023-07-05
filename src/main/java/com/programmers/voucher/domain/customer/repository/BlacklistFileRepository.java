package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.global.exception.FileAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.global.util.CommonErrorMessages.CANNOT_ACCESS_FILE;

//@Repository
public class BlacklistFileRepository implements BlacklistRepository {
    private static final Logger LOG = LoggerFactory.getLogger(BlacklistFileRepository.class);

    private final File file;

    public BlacklistFileRepository(@Value("${file.blacklist.path}") String filePath,
                                   ResourceLoader resourceLoader) {
        Resource resource = resourceLoader.getResource("file:" + filePath);
        try {
            this.file = resource.getFile();
        } catch (IOException e) {
            String errorMessage = MessageFormat.format(CANNOT_ACCESS_FILE, filePath);

            LOG.error(errorMessage, e);
            throw new FileAccessException(errorMessage, e);
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader(file));
        ) {
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                Customer customer = csvToCustomer(nextLine);
                customers.add(customer);
            }
        } catch (IOException e) {
            String errorMessage = MessageFormat.format(CANNOT_ACCESS_FILE, file.getPath());

            LOG.error(errorMessage, e);
            throw new FileAccessException(errorMessage, e);
        }
        return customers;
    }

    private Customer csvToCustomer(String nextLine) {
        String[] customerInfo = nextLine.split(",");
        UUID customerId = UUID.fromString(customerInfo[0]);
        String email = customerInfo[1];
        String name = customerInfo[2];
        Customer customer = new Customer(customerId, email, name);
        return customer;
    }
}
