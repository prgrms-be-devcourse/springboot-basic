package org.programmers.kdt.customer.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.programmers.kdt.customer.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
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

    // FIXME : YAML 파일로부터 경로 및 파일명을 읽어들여 전달해주는 CustomerPropeties 클래스를 정의하고 이곳을 통해 path와 file을 받아오도록 수정하기
    private final File blacklistCSV = new File("customer_blacklist.csv");
    private final File customersCSV = new File("customers.csv");

    public FileCustomerRepository() throws IOException, CsvValidationException {
        if (!blacklistCSV.exists()) {
            blacklistCSV.createNewFile();
        }
        if (!customersCSV.exists()) {
            customersCSV.createNewFile();
        }

        // read blacklist
        FileReader blacklistReader = new FileReader(blacklistCSV);
        CSVReader csvReader = new CSVReader(blacklistReader);

        String[] records;
        while (null != (records = csvReader.readNext())) {
            UUID customerId = UUID.fromString(records[0]);
            String name = records[1];
            String email = records[2];
            LocalDateTime lastLoginAt = LocalDateTime.parse(records[3]);
            LocalDateTime createdAt = LocalDateTime.parse(records[4]);

            cache4Blacklist.put(customerId, new Customer(customerId, name, email, lastLoginAt, createdAt));
        }

        // read customer list
        FileReader customersReader = new FileReader(customersCSV);
        csvReader = new CSVReader(customersReader);

        while (null != (records = csvReader.readNext())) {
            UUID customerId = UUID.fromString(records[0]);
            String name = records[1];
            String email = records[2];
            LocalDateTime lastLoginAt = LocalDateTime.parse(records[3]);
            LocalDateTime createdAt = LocalDateTime.parse(records[4]);

            cache4Customers.put(customerId, new Customer(customerId, name, email, lastLoginAt, createdAt));
        }

    }

    @Override
    public Customer insert(Customer customer) {
        cache4Customers.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(cache4Customers.get(customerId));
    }

    @Override
    public List<Customer> findByName(String name) {
        return cache4Customers.values().stream().filter(customer -> name.equals(customer.getName())).toList();
    }

    @Override
    public Optional<Customer> fineByEmail(String email) {
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
    public Customer registerToBlacklist(Customer customer) {
        cache4Blacklist.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public List<Customer> findAllBlacklistCustomer() {
        return new ArrayList<>(cache4Blacklist.values());
    }

    @Override
    public Optional<Customer> deleteCustomer(UUID customerId) {
        cache4Blacklist.remove(customerId);
        return Optional.ofNullable(cache4Customers.remove(customerId));
    }


    @PreDestroy
    private void updateData() throws IOException {
        // FIXME : 매번 처음부터 다시 써야 하는데, 제거된 부분만 찾아서 없애는 방법을 찾아보자.
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(customersCSV, false));
        for (Customer customer : cache4Customers.values()) {
            bufferedWriter.write(MessageFormat.format("{0},{1},{2},{3},{4}",
                    customer.getCustomerId(), customer.getName(), customer.getEmail(),
                    customer.getLastLoginAt(), customer.getCreatedAt()));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();

        bufferedWriter = new BufferedWriter(new FileWriter(blacklistCSV, false));
        for (Customer customer : cache4Blacklist.values()) {
            bufferedWriter.write(MessageFormat.format("{0},{1},{2},{3},{4}",
                    customer.getCustomerId(), customer.getName(), customer.getEmail(),
                    customer.getLastLoginAt(), customer.getCreatedAt()));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}
