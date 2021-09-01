package com.prgrms.devkdtorder.customer.repository;

import com.prgrms.devkdtorder.customer.domain.BlackCustomers;
import com.prgrms.devkdtorder.customer.domain.Customer;
import com.prgrms.devkdtorder.customer.domain.CustomerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FileCustomerRepository implements CustomerRepository {

    private final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);

    @Value("${kdt.customer.blacklist.csv-path}")
    private String BLACK_LIST_PATH;

    @Override
    public BlackCustomers findAllBlackCustomers() {
        File file = new File(BLACK_LIST_PATH);
        if (file.exists()) {
            try {
                Stream<String> lines = Files.lines(Path.of(file.toURI()));
                List<Customer> customers = lines.map(this::toCustomer).collect(Collectors.toList());
                return BlackCustomers.valueOf(customers);
            } catch (Exception e) {
                logger.info("",e.fillInStackTrace());
            }
        }
        return BlackCustomers.empty();
    }

    private Customer toCustomer(String csvLine) {
        String[] split = csvLine.split(",");
        return new Customer(
                UUID.fromString(split[0]),
                split[1],
                split[2],
                LocalDateTime.parse(split[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                LocalDateTime.parse(split[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                CustomerType.valueOf(split[2]));
    }
}
