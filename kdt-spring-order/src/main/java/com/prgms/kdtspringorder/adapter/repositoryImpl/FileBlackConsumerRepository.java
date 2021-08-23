package com.prgms.kdtspringorder.adapter.repositoryImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.prgms.kdtspringorder.domain.model.customer.Customer;
import com.prgms.kdtspringorder.domain.model.customer.CustomerRepository;

@Repository
public class FileBlackConsumerRepository implements CustomerRepository {
    private static final String FILEPATH =
        System.getProperty("user.dir") + "/kdt-spring-order/src/main/resources/.csv/customer_blacklist.csv";
    private static final File FILE = new File(FILEPATH);
    private static final String COMMA = ",";
    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, Customer> findAll() {
        return storage;
    }

    @PostConstruct
    private void postConstruct() {
        if (!FILE.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] voucherInfo = line.split(COMMA);
                UUID id = UUID.fromString(voucherInfo[0]);
                String name = voucherInfo[1];
                int age = Integer.parseInt(voucherInfo[2]);

                storage.put(id, new Customer(id, name, age));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
