package org.prgrms.kdt.devcourse.customer;

import org.prgrms.kdt.devcourse.io.FileLoader;
import org.prgrms.kdt.devcourse.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository{
    private static final Logger customerRepositoryImplLogger = LoggerFactory.getLogger(CustomerRepositoryImpl.class);
    @Value("${file.customer.blacklist}")
    private String blacklistFileName;

    @Override
    public List<Customer> getBlackCustomers() {
        Map<UUID, Customer> BlackCustomers = new ConcurrentHashMap<>();
        List<String> fileLines = FileLoader.loadFile(blacklistFileName);

        for (String line : fileLines){
            String [] oneLineDataArr = line.split(",");
            try {
                UUID blackCustomerUUID = UUID.fromString(oneLineDataArr[0]);
                String blackCustomerName = oneLineDataArr[1];
                BlackCustomers.put(blackCustomerUUID,new Customer(blackCustomerUUID,blackCustomerName,true));
            }catch (IllegalArgumentException e){
                customerRepositoryImplLogger.info("readBlackListFile - IllegalArgument(error input : {})", line);
            }

        }
        return BlackCustomers.values().stream().toList();
    }


}
