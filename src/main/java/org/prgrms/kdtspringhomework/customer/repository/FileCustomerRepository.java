package org.prgrms.kdtspringhomework.customer.repository;

import org.prgrms.kdtspringhomework.customer.domain.Customer;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private static final String USER_DIR = "user.dir";
    private static final String FILE_PATH = "src/main/resources/blackList.csv";
    private static final Path FILE_DIR = Paths.get(System.getProperty(USER_DIR), FILE_PATH);
    private static final String SEPARATION_CRITERIA = ",";
    private static final int ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private final Map<UUID, Customer> FILE_STORAGE;

    public FileCustomerRepository() {
        FILE_STORAGE = seperateCustomers();
    }

    private Map<UUID, Customer> seperateCustomers() {
        Map<UUID, Customer> customerMap = new ConcurrentHashMap<>();
        try {
            List<String> blackCustomerList = Files.readAllLines(FILE_DIR);
            for (String line : blackCustomerList) {
                String[] customerArray = line.split(SEPARATION_CRITERIA);
                UUID customerId = UUID.fromString(customerArray[ID_INDEX]);
                String customerName = customerArray[NAME_INDEX];

                customerMap.put(customerId, new Customer(customerId, customerName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerMap;
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(FILE_STORAGE.values());
    }
}
