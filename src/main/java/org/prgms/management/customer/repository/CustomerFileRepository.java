package org.prgms.management.customer.repository;

import org.prgms.management.customer.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local-file", "test"})
public class CustomerFileRepository implements CustomerRepository {
    @Value("${filedb.customer}")
    private String filePath;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<UUID, Customer> getAll() {
        Map<UUID, Customer> map = new ConcurrentHashMap<>();

        try (
                BufferedReader bufferedReader = new BufferedReader(
                        new FileReader(filePath))
        ) {
            bufferedReader.lines().forEach(
                    line -> {
                        String[] str = line.split(",");
                        UUID customerId = UUID.fromString(str[0]);
                        String name = str[1];
                        map.put(customerId, new Customer(customerId, name));
                    }
            );
        } catch (IOException e) {
            logger.error("{} can't read customer file", e.getMessage());
        }

        return map;
    }

    @Override
    public Optional<Customer> insert(Customer customer) {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new FileWriter(filePath, true)
                );
        ) {
            String customerStr = MessageFormat.format("{0},{1}\r\n",
                    customer.getCustomerId(), customer.getName());

            bufferedWriter.write(customerStr);
            return Optional.of(customer);
        } catch (IOException e) {
            logger.error("{} can't insert customer file", e.getMessage(), e);
        }
        return Optional.empty();
    }
}
