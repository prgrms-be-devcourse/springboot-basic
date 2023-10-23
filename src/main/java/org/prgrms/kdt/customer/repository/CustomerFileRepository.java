package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.Customer;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CustomerFileRepository implements CustomerRepository {

    @Override
    public List<Customer> readFile(String filePath) {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 5) {
                    UUID id = UUID.fromString(data[0]);
                    String name = data[1];
                    String email = data[2];
                    LocalDateTime joinedAt = LocalDateTime.parse(data[3]);
                    boolean isBlacked = Boolean.parseBoolean(data[4]);

                    Customer customer = new Customer(id, name, email, joinedAt, isBlacked);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customers;
    }
}
