package com.prgms.VoucherApp.domain.customer.storage;

import com.prgms.VoucherApp.domain.customer.Customer;
import com.prgms.VoucherApp.domain.customer.CustomerStatus;
import com.prgms.VoucherApp.domain.customer.dto.CustomerResDto;
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
public class BlackListFileStorage {

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
                CustomerResDto customerResDto = createBlackListDto(line);
                Customer customer = createCustomer(customerResDto);
                blackListMap.put(customerResDto.getCustomerId(), customer);
            }
        } catch (IOException e) {
            log.error("init blackListMap() method Exception, message : {}", e.getMessage());
        }
    }

    private CustomerResDto createBlackListDto(String line) {
        CustomerResDto customerResDto = new CustomerResDto(line, CustomerStatus.BLACKLIST);
        return customerResDto;
    }

    private Customer createCustomer(CustomerResDto customerResDto) {
        Customer customer = new Customer(customerResDto.getCustomerId(), customerResDto.getCustomerStatus());
        return customer;
    }

    public List<Customer> findBlacklist() {
        return blackListMap.values()
                .stream()
                .toList();
    }
}
