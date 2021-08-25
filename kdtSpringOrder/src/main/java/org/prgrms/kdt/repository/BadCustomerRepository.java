package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.customer.BadCustomer;
import org.prgrms.kdt.domain.customer.Customer;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.prgrms.kdt.utils.FileUtils.getReadAllLines;
import static org.prgrms.kdt.utils.FileUtils.isExistFile;

@Repository
public class BadCustomerRepository implements CustomerRepository{

    private static final Path badCustomerFilePath = Paths.get(System.getProperty("user.dir") , "customer", "badCustomerList.csv");
    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();

    @Override
    public Customer insert(Customer customer) {
        return null;
    }

    @Override
    public Customer update(Customer Customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        if(storage.isEmpty()) {
            loadStorage();
        }
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }

    private void loadStorage() {
        if(!isExistFile(badCustomerFilePath)) {
            return;
        }

        try {
            List<String> lines = getReadAllLines(badCustomerFilePath);
            for (String line : lines) {
                List<String> badCustomerInfo = Arrays.asList(line.split(","));
                storage.put(UUID.fromString(badCustomerInfo.get(0)), new BadCustomer(UUID.fromString(badCustomerInfo.get(0)), badCustomerInfo.get(1), Integer.parseInt(badCustomerInfo.get(2))));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
