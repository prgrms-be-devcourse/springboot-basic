package org.prgrms.kdt.devcourse.customer;

import org.prgrms.kdt.devcourse.io.FileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BlackCustomerRepository {
    private static final Logger customerRepositoryImplLogger = LoggerFactory.getLogger(BlackCustomerRepository.class);
    @Value("${file.customer.blacklist}")
    private String blacklistFileName;


    public List<Customer> getBlackCustomers() {
        Map<UUID, Customer> BlackCustomers = new ConcurrentHashMap<>();
        List<String> fileLines = FileLoader.loadFile(blacklistFileName);

        for (String line : fileLines){
            String [] oneLineDataArr = line.split(",");
            try {
                UUID blackCustomerUUID = UUID.fromString(oneLineDataArr[0]);
                String blackCustomerName = oneLineDataArr[1];
                String blackCustomerEmail = oneLineDataArr[2];
                BlackCustomers.put(blackCustomerUUID,new Customer(blackCustomerUUID, blackCustomerName, blackCustomerEmail, LocalDateTime.now()));
            }catch (IllegalArgumentException e){
                customerRepositoryImplLogger.info("readBlackListFile - IllegalArgument(error input : {})", line);
            }

        }
        return BlackCustomers.values().stream().toList();
    }


}
