package org.prgrms.kdtspringorder.customer.repository.implementation;

import org.prgrms.kdtspringorder.config.YmlPropertiesLoader;
import org.prgrms.kdtspringorder.customer.domain.Customer;
import org.prgrms.kdtspringorder.customer.repository.abstraction.CustomerRepository;
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
            ioException.printStackTrace();
        }

        return bannedCustomerList;
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        Resource resource = this.resourceLoader.getResource(this.ymlPropertiesLoader.getBlackListFilePath());
        this.bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
    }

}
