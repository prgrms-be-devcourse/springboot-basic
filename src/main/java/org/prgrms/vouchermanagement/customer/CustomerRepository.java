package org.prgrms.vouchermanagement.customer;

import org.prgrms.vouchermanagement.exception.LoadFailException;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CustomerRepository {

    private final List<Customer> blackList = new ArrayList<>();
    private final String filePath = "./blacklist/blackList.csv";

    public List<Customer> load() {
        if(blackList.isEmpty()) {
            getFromFile();
        }
        return blackList;
    }

    private void getFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                UUID uuid = UUID.fromString(parts[0]);
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                Customer customer = new Customer(uuid, name, age);

                blackList.add(customer);
            }
        } catch (IOException e) {
            throw new LoadFailException("CSV 파일을 찾을 수 없습니다.");
        }
    }
}
