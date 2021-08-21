package com.programmers.voucher.service.customer;

import com.programmers.voucher.entity.customer.Customer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlacklistCustomerReader implements CustomerInitializer {

    private final Path file;
    List<Customer> list = new LinkedList<>();

    public BlacklistCustomerReader() throws IOException {
        String filename = "customer_blacklist.csv";

        Path fileDirectory = Paths.get("consumer");
        if(!Files.exists(fileDirectory)) {
            Files.createDirectory(fileDirectory);
        }

        this.file = fileDirectory.resolve(filename);
        if(!Files.exists(file)) {
            Files.createFile(file);
        }
    }

    @Override
    public void loadCustomers() {
        try {
            List<String> customers = Files.readAllLines(file);
            list = customers.stream()
                    .map(customerString -> customerString.split(","))
                    .map(customerElement -> new Customer(
                            Long.parseLong(customerElement[0]),
                            customerElement[1],
                            customerElement[2],
                            true))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            System.err.printf("%s - IOException occur when reading file.%n", ex.getLocalizedMessage());
            list = new ArrayList<>(0);
        } catch (Exception ex) {
            System.err.printf("Unknown error occur - %s%n", ex.getLocalizedMessage());
            list = new ArrayList<>(0);
        }
    }

    @Override
    public List<Customer> readCustomers() {
        return list;
    }
}
