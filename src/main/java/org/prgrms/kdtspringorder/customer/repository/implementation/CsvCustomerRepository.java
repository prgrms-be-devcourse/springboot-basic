package org.prgrms.kdtspringorder.customer.repository.implementation;

import org.prgrms.kdtspringorder.customer.domain.Customer;
import org.prgrms.kdtspringorder.customer.repository.abstraction.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ResourceLoader resourceLoader;

    private BufferedReader bufferedReader;

    @Override
    public List<Customer> getBannedCustomers() {
        List<Customer> bannedCustomerList = new ArrayList<>();

        try {
            String newLine;
            while ((newLine = bufferedReader.readLine()) != null) {
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
        Resource resource = resourceLoader.getResource("file:files/banned-customers.csv");
        this.bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
    }

}
