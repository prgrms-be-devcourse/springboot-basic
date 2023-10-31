package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.utils.CsvFileUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class FileCustomerRepository implements CustomerRepository {
    private static final String CSV_SEPARATOR = ",";

    private final String csvFilePath;
    private final ConcurrentHashMap<UUID, Customer> customers = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);

    public FileCustomerRepository(@Value("${csv.file.customer.path}") String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @PostConstruct
    public void init() {
        readFile();
    }

    @Override
    public List<Customer> findAll() {
        return customers.values().stream().toList();
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return Optional.ofNullable(customers.get(id));
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return customers.values().stream()
                .filter(customer -> customer.getName().equals(name)).findFirst();
    }

    @Override
    public List<Customer> findByNameLike(String name) {
        return customers.values().stream()
                .filter(customer -> customer.getName().contains(name))
                .toList();
    }

    @Override
    public List<Customer> findBannedCustomers() {
        return customers.values().stream()
                .filter(Customer::isBanned)
                .toList();
    }

    @Override
    public Customer save(Customer customer) {
        customers.put(customer.getId(), customer);
        updateFile();
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        return save(customer);
    }

    @Override
    public int delete(UUID id) {
        Customer customer = customers.remove(id);
        updateFile();
        return customer != null ? 1 : 0;
    }

    private void readFile() {
        final List<String> lines = CsvFileUtil.readCsvFile(csvFilePath);
        lines.forEach(line -> {
            String[] customerInfo = line.split(CSV_SEPARATOR);
            final Customer customer = new Customer(customerInfo);
            customers.put(customer.getId(), customer);
        });
    }

    private void updateFile() {
        CsvFileUtil.updateCsvFile(csvFilePath, customers.values().stream().toList());
    }
}
