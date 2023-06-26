package com.prgms.VoucherApp.domain.customer.storage;

import com.prgms.VoucherApp.domain.customer.Customer;
import com.prgms.VoucherApp.domain.customer.CustomerStatus;
import com.prgms.VoucherApp.domain.customer.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BlackListFileStorage implements BlackListStorage {

    private static final Logger log = LoggerFactory.getLogger(BlackListFileStorage.class);
    private final Map<UUID, Customer> blackListMap = new ConcurrentHashMap<>();

    @Value("${customer.file.path}")
    private String filePath;

    @PostConstruct
    public void initBlackListMap() {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                CustomerDto customerDto = createBlackListDto(line);
                Customer customer = Customer.createCustomer(customerDto);
                blackListMap.put(customerDto.getCustomerId(), customer);
            }
        } catch (IOException e) {
            log.error("init blackListMap() method Exception, message : {}", e.getMessage());
        }
    }

    private CustomerDto createBlackListDto(String line) {
        CustomerDto customerDto = new CustomerDto(line, CustomerStatus.BLACKLIST);
        return customerDto;
    }

    @Override
    public List<Customer> findAll() {
        return blackListMap.values()
                .stream()
                .toList();
    }
}
