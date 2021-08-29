package org.prgrms.orderapp.repository;

import org.prgrms.orderapp.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.prgrms.orderapp.io.IOUtils.loadCSV;
import static org.prgrms.orderapp.model.Customer.createFromString;

@Repository
public class BlacklistCustomerRepository implements CustomerRepository {

    private final static Logger logger = LoggerFactory.getLogger(BlacklistCustomerRepository.class);

    private String prefix;

    private String filename;

    private final Map<UUID, Customer> blacklist = new ConcurrentHashMap<>();

    public BlacklistCustomerRepository(@Value("${data.customer-blacklist.prefix}") String prefix, @Value("${data.customer-blacklist.name}") String filename) {
        this.prefix = prefix;
        this.filename = filename;
    }

    @Override
    public List<Customer> getBlacklist() {
        logger.info("CsvCustomerRepository.getBlacklist() is called.");
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
        logger.info("CsvCustomerRepository.getBlacklist() is returning.");
        return blacklistedCustomers;
    }

    public Customer save(Customer customer) {
        blacklist.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public int size() {
        return blacklist.size();
    }
}
