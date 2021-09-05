package org.programmers.customer.repository;

import org.programmers.customer.model.BlackListCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("prod")
public class FileBlackListCustomerRepository implements BlackListCustomerRepository {

    private final Logger logger = LoggerFactory.getLogger(FileBlackListCustomerRepository.class);

    private final Map<UUID, BlackListCustomer> blackListMap = new ConcurrentHashMap<>();

    private final String CustomerfilePath = "C:\\Users\\skyey\\Desktop\\";
    private final String CustomerfileName = "customer_blackList.csv";
    File customerFile = new File(CustomerfilePath + CustomerfileName);

    @Override
    public List<BlackListCustomer> findAllBlackList() {
        return new ArrayList<>(blackListMap.values());
    }

    @Override
    public void save(BlackListCustomer blackListCustomer) {
        blackListMap.put(blackListCustomer.getCustomerId(), blackListCustomer);
    }

    @PostConstruct
    public void loadBlackList() {
        try {
            BufferedReader blackListBr = new BufferedReader(new FileReader(customerFile));

            blackListBr.lines().map(loadLine -> loadLine.split(","))
                    .forEach(loadLineArr -> {
                        UUID customerId = UUID.fromString(loadLineArr[0]);
                        String name = loadLineArr[1];
                        save(new BlackListCustomer(customerId, name));
                    });
        } catch (Exception e) {
            logger.info("Got error ->  {0} ", e);
        }
    }
}
