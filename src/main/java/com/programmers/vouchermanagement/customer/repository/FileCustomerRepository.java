package com.programmers.vouchermanagement.customer.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.configuration.properties.file.FileProperties;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.util.JSONFileManager;

@Repository
@Profile({"file", "test"})
public class FileCustomerRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);
    private static final String COMMA_SEPARATOR = ", ";
    private static final String IO_EXCEPTION_LOG_MESSAGE = "Error raised while reading blacklist";
    private static final String CUSTOMER_ID_KEY = "customerId";
    private static final String NAME_KEY = "name";
    private static final String CUSTOMER_TYPE_KEY = "customerType";

    private final Function<Map, Customer> objectToCustomer = (customerObject) -> {
        UUID customerId = UUID.fromString((String) customerObject.get(CUSTOMER_ID_KEY));
        String name = (String) customerObject.get(NAME_KEY);
        String customerTypeName = (String) customerObject.get(CUSTOMER_TYPE_KEY);
        CustomerType customerType = CustomerType.findCustomerType(customerTypeName);

        return new Customer(customerId, name, customerType);
    };

    private final Function<Customer, HashMap<String, Object>> customerToObject = (customer) -> {
        HashMap<String, Object> customerObject = new HashMap<>();
        customerObject.put("customerId", customer.getCustomerId().toString());
        customerObject.put("name", customer.getName());
        customerObject.put("customerType", customer.getCustomerType().name());
        return customerObject;
    };

    private final String blacklistFilePath;
    private final String customerFilePath;
    private final JSONFileManager<UUID, Customer> jsonFileManager;
    private final Map<UUID, Customer> customers;

    public FileCustomerRepository(FileProperties fileProperties, @Qualifier("customer") JSONFileManager<UUID, Customer> jsonFileManager) {
        this.customerFilePath = fileProperties.getJSONCustomerFilePath();
        this.blacklistFilePath = fileProperties.getCSVCustomerFilePath();
        this.jsonFileManager = jsonFileManager;
        this.customers = new HashMap<>();
        loadBlacklist();
        loadCustomersFromJSON();
    }

    @Override
    public Customer save(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        saveFile();
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        if (customers.isEmpty()) {
            return Collections.emptyList();
        }
        return customers.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

    @Override
    public List<Customer> findBlackCustomers() {
        return customers.values()
                .stream()
                .filter(Customer::isBlack)
                .toList();
    }

    @Override
    public void deleteById(UUID customerId) {
        customers.remove(customerId);
        saveFile();
    }

    @Override
    @Profile("test")
    public void deleteAll() {
        customers.clear();
        saveFile();
    }

    private void loadBlacklist() {
        List<Customer> blacklist = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(blacklistFilePath))) {
            br.readLine(); // skip the first line
            String str;
            while ((str = br.readLine()) != null) {
                String[] line = str.split(COMMA_SEPARATOR);

                UUID blackCustomerId = UUID.fromString(line[0]);
                String name = line[1];

                Customer blackCustomer = new Customer(blackCustomerId, name, CustomerType.BLACK);

                blacklist.add(blackCustomer);
            }
        } catch (IOException e) {
            logger.error(IO_EXCEPTION_LOG_MESSAGE);
            throw new UncheckedIOException(e);
        }

        blacklist.forEach(blackCustomer -> customers.put(blackCustomer.getCustomerId(), blackCustomer));
    }

    private void loadCustomersFromJSON() {
        List<Customer> loadedCustomer = jsonFileManager.loadFile(customerFilePath, objectToCustomer);
        loadedCustomer.forEach(customer -> customers.put(customer.getCustomerId(), customer));
    }

    private void saveFile() {
        jsonFileManager.saveFile(customerFilePath, customers, customerToObject);
    }
}
