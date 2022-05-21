package org.prgrms.kdt.customer.repository;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.error.CustomerFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

@Repository
public class BlackListCustomerRepository implements BlackListRepository {

    private static final Logger log = LoggerFactory.getLogger(BlackListCustomerRepository.class);

    @Value("${path.blacklist}")
    private String fileName;
    private final ResourceLoader resourceLoader;

    public BlackListCustomerRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Customer> findAll() {
        return read();
    }

    private List<Customer> read() {
        Resource resource = resourceLoader.getResource(fileName);
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(resource.getInputStream()));) {
            customers = br.lines()
                .map(l -> l.split(","))
                .map(s -> new Customer(UUID.fromString(s[0]), s[1],
                    LocalDateTime.parse(s[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))))
                .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("파일에 읽어오는데 문제가 생겼습니다.");
            throw new CustomerFileException();
        }

        return customers;
    }

}
