package org.prgrms.orderapp.repository;

import org.prgrms.orderapp.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.prgrms.orderapp.CsvParsingUtil.removeSideQuotes;
import static org.prgrms.orderapp.CsvParsingUtil.splitCSV;
import static org.prgrms.orderapp.io.IOUtils.loadCSV;

@Repository
public class CsvCustomerRepository implements CustomerRepository {

    @Value("${data.customer-blacklist.prefix}")
    private String prefix;

    @Value("${data.customer-blacklist.name}")
    private String filename;

    private final Map<UUID, Customer> blacklist = new ConcurrentHashMap<>();

    @Override
    public List<Customer> getBlacklist() {
        String path = MessageFormat.format("{0}/{1}/{2}", System.getProperty("user.dir"), prefix, filename);
        var lines = loadCSV(path);
        List<Customer> blacklistedCustomers = new ArrayList<>();
        for (String line:lines) {
            var c = createFromString(line);
            if (c.isPresent()) {
                var customer = c.get();
                customer.setBlacklisted(true);
                save(customer);
                blacklistedCustomers.add(customer);
            }
        }
        return blacklistedCustomers;
    }

    private void save(Customer customer) {
        blacklist.put(customer.getCustomerId(), customer);
    }

    public Optional<Customer> createFromString(String s) {
        try {
            String[] args = splitCSV(s);
            UUID customerId = UUID.fromString(args[0]);
            String name = removeSideQuotes(args[1]);
            String address = removeSideQuotes(args[2]);
            int age = Integer.parseInt(args[3]);
            return Optional.of(new Customer(customerId, name, address, age));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
