package com.prgrms.management.customer.repository;

import com.prgrms.management.config.ErrorMessageType;
import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile({"local", "dev"})
public class FileCustomerRepository implements CustomerRepository {
    private final String BLACK_LIST_FILE_NAME = "src/main/resources/customer_blacklist.csv";
    private static final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);

    @Override
    public Customer save(Customer customer) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(BLACK_LIST_FILE_NAME, true))) {
            if (customer.getCustomerType().equals(CustomerType.BLACKLIST)) {
                bufferedWriter.write(customer.getCustomerId() + "," + customer.getCustomerType());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            logger.info("{}:{}", e.getClass(), ErrorMessageType.IO_EXCEPTION.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Customer> findBlackList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Customer updateName(Customer customer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(UUID customerId) {
        throw new UnsupportedOperationException();
    }
}
