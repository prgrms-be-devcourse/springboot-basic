package org.prgrms.dev.customer.repository;

import org.prgrms.dev.customer.domain.Customer;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private static final String PATH = "src/main/resources/customer_blacklist.csv";
    private static final String USER_DIR = "user.dir";
    private static final String SPLIT_CODE = ":";
    private static final int UUID_INDEX = 0;
    private static final int NAME_INDEX = 1;

    private final File file;
    private final Map<UUID, Customer> blacklistStore;

    public FileCustomerRepository() {
        this.file = new File(System.getProperty(USER_DIR), PATH);
        this.blacklistStore = init();
    }

    private Map<UUID, Customer> init() {
        Map<UUID, Customer> blackList = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] customerInfo = line.split(SPLIT_CODE);
                UUID customerId = UUID.fromString(customerInfo[UUID_INDEX]);
                String name = customerInfo[NAME_INDEX];
                Customer customer = new Customer(customerId, name);
                blackList.put(customerId, customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blackList;
    }

    @Override
    public List<Customer> findAllInBlackList() {
        return new ArrayList<>(blacklistStore.values());
    }
}
