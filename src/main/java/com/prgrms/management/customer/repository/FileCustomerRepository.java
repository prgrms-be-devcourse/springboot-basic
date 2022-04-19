package com.prgrms.management.customer.repository;

import com.prgrms.management.config.ErrorMessageType;
import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerType;
import com.prgrms.management.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
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
            if(customer.getCustomerType().equals(CustomerType.BLACKLIST)) {
                bufferedWriter.write(customer.getCustomerId() + "," + customer.getCustomerType());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            logger.info("{}:{}",e.getClass(), ErrorMessageType.IO_EXCEPTION.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        //미구현
        return null;
    }

    @Override
    public void deleteAll() {
        //미구현
    }

    @Override
    public void deleteById(UUID customerId) {
        //미구현
    }

    @Override
    public Customer updateName(Customer customer) {
        //미구현
        return null;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        //미구현
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        //미구현
        return Optional.empty();
    }


    @Override
    public List<Customer> findBlackList() {
        //TODO 미구현
        return null;
    }
}
