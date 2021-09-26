package org.programmers.kdt.customer.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.programmers.kdt.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class FileCustomerRepository implements CustomerRepository {
    // FIXME : 데이터 양이 많아지면 메모리 관련 문제 발생 가능 -> FileVoucherRepository와 같은 문제
    Map<UUID, Customer> cache4Customers = new ConcurrentHashMap<>();
    Map<UUID, Customer> cache4Blacklist = new ConcurrentHashMap<>();

    private final File blacklistCSV;
    private final File customersCSV;

    // TODO : 각 class마다 logger를 두지 않고 AOP 적용
    private static final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);

    public FileCustomerRepository(String customerFile, String customerBlacklistFile) {
        blacklistCSV = new File(customerBlacklistFile);
        if (!blacklistCSV.exists()) {
            try {
                blacklistCSV.createNewFile();
            } catch (IOException e) {
                logger.error("Cannot create csv file for blacklist -> {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }

        customersCSV = new File(customerFile);
        if (!customersCSV.exists()) {
            try {
                customersCSV.createNewFile();
            } catch (IOException e) {
                logger.error("Cannot create csv file for customers -> {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }

        // read blacklist
        try (
            FileReader blacklistReader = new FileReader(blacklistCSV);
            CSVReader csvReader = new CSVReader(blacklistReader);
        ) {
            String[] records;
            while (null != (records = csvReader.readNext())) {
                UUID customerId = UUID.fromString(records[0]);
                String name = records[1];
                String email = records[2];
                LocalDateTime lastLoginAt = LocalDateTime.parse(records[3]);
                LocalDateTime createdAt = LocalDateTime.parse(records[4]);

                cache4Blacklist.put(customerId, new Customer.Builder(customerId, email, createdAt).name(name).lastLoginAt(lastLoginAt).build());
            }
        } catch (CsvValidationException e) {
            logger.error("CSV file is damaged! -> {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("Something went wrong during opening CSV file for blacklist -> {}", e.getMessage());
            throw new RuntimeException(e);
        }

        // read customer list
        try (
            FileReader customersReader = new FileReader(customersCSV);
            CSVReader csvReader = new CSVReader(customersReader);
        ) {
            String[] records;
            while (null != (records = csvReader.readNext())) {
                UUID customerId = UUID.fromString(records[0]);
                String name = records[1];
                String email = records[2];
                LocalDateTime lastLoginAt = LocalDateTime.parse(records[3]);
                LocalDateTime createdAt = LocalDateTime.parse(records[4]);

                cache4Customers.put(customerId, new Customer.Builder(customerId, email, createdAt).name(name).lastLoginAt(lastLoginAt).build());
            }
        } catch (FileNotFoundException e) {
            logger.error("CSV file for customer list does not exist! -> {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            logger.error("CSV file is damaged! -> {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("Something went wrong during opening CSV file for customers -> {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer insert(Customer customer) {
        if (null == cache4Customers.put(customer.getCustomerId(), customer)) {
            updateFile(customersCSV, Map.of(customer.getCustomerId(), customer), true);
        }
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(cache4Customers.get(customerId));
    }

    @Override
    public List<Customer> findByName(String name) {
        return cache4Customers.values()
                .stream()
                .filter(customer -> name.equals(customer.getName()))
                .toList();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return cache4Customers.values()
                .stream()
                .filter(customer -> email.equals(customer.getEmail()))
                .findAny();
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(cache4Customers.values());
    }

    @Override
    public Optional<Customer> updateName(UUID customerId, String newString) {
        // TODO: Implement
        return Optional.empty();
    }

    @Override
    public Customer registerToBlacklist(Customer customer) {
        if (null == cache4Blacklist.put(customer.getCustomerId(), customer)) {
            updateFile(blacklistCSV, Map.of(customer.getCustomerId(), customer), true);
        }
        return customer;
    }

    @Override
    public List<Customer> findAllBlacklistCustomer() {
        return new ArrayList<>(cache4Blacklist.values());
    }

    @Override
    public Optional<Customer> findCustomerOnBlacklistById(UUID customerId) {
        // TODO: Implement
        return Optional.empty();
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        // FIXME : 매번 전체 파일을 다시 써야 하므로 성능저하 발생. 해결 필요
        if (null != cache4Blacklist.remove(customerId)) {
            updateFile(blacklistCSV, cache4Blacklist, false);
        }

        if (null != cache4Customers.remove(customerId)) {
            updateFile(customersCSV, cache4Customers, false);
        }
    }

    @Override
    public void deleteAll() {
        cache4Blacklist.clear();
        updateFile(blacklistCSV, cache4Blacklist, false);

        cache4Customers.clear();
        updateFile(customersCSV, cache4Customers, false);
    }

    private void updateFile(File csvFile, Map<UUID, Customer> cache, boolean appendToFile) {
        try (
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(csvFile, appendToFile))
        ) {
            for (Customer customer : cache.values()) {
                bufferedWriter.write(MessageFormat.format("{0},{1},{2},{3},{4}",
                        customer.getCustomerId(), customer.getName(), customer.getEmail(),
                        customer.getLastLoginAt(), customer.getCreatedAt()));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            logger.error("Updating CSV file Fails -> {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
