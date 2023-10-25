package org.prgrms.prgrmsspring.repository.user;

import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class FileCustomerRepository implements CustomerRepository {

    private final Map<UUID, Customer> store = new ConcurrentHashMap<>();
    private final String filePath;
    private static final String CSV_SEPARATOR = ",";

    public FileCustomerRepository(@Value("${file.path.blacklist}") String filePath) {
        this.filePath = filePath;
        readAndStoreInMemory();
    }

    private void readAndStoreInMemory() {
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            List<String> fileStrings = readFile(inputStream);
            fileStrings.stream().filter(s -> !s.equals(fileStrings.get(0)))
                    .map(str -> str.split(CSV_SEPARATOR)).forEach(split -> {
                        String name = split[0];
                        Boolean isBlack = Boolean.valueOf(split[1]);
                        Customer customer = new Customer(UUID.randomUUID(), name, isBlack, null);
                        store.put(customer.getCustomerId(), customer);
                    });
        } catch (IOException e) {
            throw new NotFoundException(ExceptionMessage.NOT_FOUND_FILE.getMessage());
        }
    }

    private List<String> readFile(InputStream inputStream) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    @Override
    public List<Customer> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public List<Customer> findBlackAll() {
        return store.values().stream().filter(Customer::getIsBlack).toList();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(store.get(customerId));
    }

    @Override
    public Customer insert(Customer customer) {
        store.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void delete(UUID customerId) {
        store.remove(customerId);
    }
}
