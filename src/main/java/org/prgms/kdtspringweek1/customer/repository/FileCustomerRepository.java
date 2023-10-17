package org.prgms.kdtspringweek1.customer.repository;

import jakarta.annotation.PostConstruct;
import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private Map<UUID, Customer> blackCustomers;
    private File blackCustomerInfoCsv;
    @Value("${spring.file.black-customer.path}")
    private String blackCustomerInfoCsvPath;

    @PostConstruct
    private void init() {
        blackCustomerInfoCsv = new File(blackCustomerInfoCsvPath);
    }

    @Override
    public List<Customer> findAllBlackConsumer() {
        blackCustomers = getAllBlackCustomersFromCSV();
        return new ArrayList<>(blackCustomers.values());
    }

    private Map<UUID, Customer> getAllBlackCustomersFromCSV() {
        Map<UUID, Customer> blackCustomers = new ConcurrentHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(blackCustomerInfoCsv))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] blackCustomerInfo = line.split(", ");
                UUID blackCustomerId = UUID.fromString(blackCustomerInfo[0]);
                String name = blackCustomerInfo[1];

                blackCustomers.put(blackCustomerId, new Customer(blackCustomerId, name, true));
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 에러가 발생했습니다.");
        }

        return blackCustomers;
    }
}
