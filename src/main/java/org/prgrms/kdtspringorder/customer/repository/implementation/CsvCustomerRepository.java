package org.prgrms.kdtspringorder.customer.repository.implementation;

import org.prgrms.kdtspringorder.config.YmlPropertiesLoader;
import org.prgrms.kdtspringorder.customer.domain.Customer;
import org.prgrms.kdtspringorder.customer.repository.abstraction.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CsvCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CsvCustomerRepository.class);
    private final YmlPropertiesLoader ymlPropertiesLoader;
    private final ResourceLoader resourceLoader;
    private BufferedReader bufferedReader;

    public CsvCustomerRepository(YmlPropertiesLoader ymlPropertiesLoader, ResourceLoader resourceLoader) {
        this.ymlPropertiesLoader = ymlPropertiesLoader;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Customer> getBannedCustomers() {
        List<Customer> bannedCustomerList = new ArrayList<>();

        try {
            String newLine;
            while ((newLine = this.bufferedReader.readLine()) != null) {
                UUID customerId = UUID.fromString(newLine);
                bannedCustomerList.add(new Customer(customerId));
            }
        } catch (IOException ioException) {
            logger.error("banned-list.csv file 읽기 실패", ioException);
            throw new RuntimeException();
        }

        return bannedCustomerList;
    }

    @PostConstruct
    public void postConstruct() {
        Resource resource = this.resourceLoader.getResource(this.ymlPropertiesLoader.getBlackListFilePath());
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        } catch (IOException ioException) {
            logger.error("banned-list.csv file이 존재하지 않습니다.", ioException);
            throw new RuntimeException();
        }
    }

}
