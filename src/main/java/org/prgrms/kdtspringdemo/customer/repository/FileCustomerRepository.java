package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.BlackCustomer;
import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.customer.NormalCustomer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Profile("dev2")
@Repository
public class FileCustomerRepository implements CustomerRepository, InitializingBean {
    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();
    private final Map<UUID, Customer> blacklist_storage = new ConcurrentHashMap<>();
    private final String FILE_NAME = "customer_blacklist.csv";

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(storage.get(customerId));
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
    public Optional<Customer> findByType(String email) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Customer insert(Customer customer) {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<Customer> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public List<Customer> findBlacklist() {
        return blacklist_storage.values().stream().toList();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(FILE_NAME))) {
            reader.lines().forEach(line -> {
                String[] dataArray = line.split(",");
                String customerType = dataArray[0];
                String uuid = dataArray[1];
                String name = dataArray[2];
                String email = "NoneInFile@aaa.com";

                if (customerType.equals("normal")) {
                    var customer = new NormalCustomer(UUID.fromString(uuid), name, email, "Normal", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
                    storage.put(customer.getId(), customer);
                } else if (customerType.equals("black")) {
                    var customer = new BlackCustomer(UUID.fromString(uuid), name, email, "Black", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
                    storage.put(customer.getId(), customer);
                    blacklist_storage.put(customer.getId(), customer);
                } else {
                    System.out.println("None CustomerType!!! : " + customerType);
                }
            });
        } catch (IOException e) {
            System.out.println("Doesn't exist file.");
        }
    }
}
