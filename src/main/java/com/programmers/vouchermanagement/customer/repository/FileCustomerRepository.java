package com.programmers.vouchermanagement.customer.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.configuration.profiles.FileEnabledCondition;
import com.programmers.vouchermanagement.configuration.properties.file.FileProperties;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.util.JSONFileManager;

@Repository
@Conditional(FileEnabledCondition.class)
public class FileCustomerRepository implements CustomerRepository {
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
        customerObject.put(CUSTOMER_ID_KEY, customer.getCustomerId().toString());
        customerObject.put(NAME_KEY, customer.getName());
        customerObject.put(CUSTOMER_TYPE_KEY, customer.getCustomerType().name());
        return customerObject;
    };

    private final String customerFilePath;
    private final JSONFileManager<UUID, Customer> jsonFileManager;
    private final Map<UUID, Customer> customers;

    public FileCustomerRepository(FileProperties fileProperties, @Qualifier("customer") JSONFileManager<UUID, Customer> jsonFileManager) {
        customerFilePath = fileProperties.getJSONCustomerFilePath();
        this.jsonFileManager = jsonFileManager;
        this.customers = loadCustomersFromJSON();
        loadBlacklist(fileProperties.getCSVCustomerFilePath());
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

    private Map<UUID, Customer> loadCustomersFromJSON() {
        return jsonFileManager.loadFile(customerFilePath, objectToCustomer, Customer::getCustomerId);
    }

    private void saveFile() {
        jsonFileManager.saveFile(customerFilePath, customers, customerToObject);
    }

    private void loadBlacklist(String blacklistFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(blacklistFilePath))) {
            br.readLine(); // skip the first line
            String str;
            while ((str = br.readLine()) != null) {
                String[] line = str.split(COMMA_SEPARATOR);

                UUID blackCustomerId = UUID.fromString(line[0]);
                String name = line[1];

                Customer blackCustomer = new Customer(blackCustomerId, name, CustomerType.BLACK);
                customers.put(blackCustomerId, blackCustomer);

            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
