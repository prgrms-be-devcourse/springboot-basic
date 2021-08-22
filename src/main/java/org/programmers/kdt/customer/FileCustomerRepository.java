package org.programmers.kdt.customer;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    Map<UUID, Customer> cacheForCustomer = new ConcurrentHashMap<>();
    Map<UUID, Customer> cacheForBlackList = new ConcurrentHashMap<>();

    private final String path = "";
    private final String blackList = "customer_blacklist.csv"; // storing blacklist info
    private final String customerList = "customer_list.csv"; // storing all customer info

    public FileCustomerRepository() {
        try {
            // blacklist
            FileReader blacklistReader = new FileReader(blackList);
            CSVReader csvReader = new CSVReader(blacklistReader);

            String[] records;
            while ((records = csvReader.readNext()) != null) {
                UUID customerId = UUID.fromString(records[0]);
                String name = records[1];
                String email = records[2];

                cacheForBlackList.put(customerId, new Customer(customerId, name, email));
            }

            // customers
            FileReader customerlistReader = new FileReader(customerList);
            csvReader = new CSVReader(customerlistReader);

            while ((records = csvReader.readNext()) != null) {
                UUID customerId = UUID.fromString(records[0]);
                String name = records[1];
                String email = records[2];

                cacheForCustomer.put(customerId, new Customer(customerId, name, email));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer save(Customer customer) {
        if (null == cacheForCustomer.put(customer.getCustomerId(), customer)) {
            saveCustomerData(customer, customerList);
        }

        return customer;
    }

    @Override
    public Customer registerToBlackList(Customer customer) {
        if (null == cacheForBlackList.put(customer.getCustomerId(), customer)) {
            saveCustomerData(customer, blackList);
        }
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        if (cacheForBlackList.containsKey(customerId)) {
            return Optional.of(cacheForBlackList.get(customerId));
        }
        if (cacheForCustomer.containsKey(customerId)) {
            return Optional.of(cacheForCustomer.get(customerId));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Customer>> findByName(String name) {
        return Optional.of(cacheForCustomer.values().stream().filter(customer -> name.equals(customer.getName())).toList());
    }

    @Override
    public Optional<List<Customer>> findByEmail(String email) {
        return Optional.of(cacheForCustomer.values().stream().filter(customer -> email.equals(customer.getEmail())).toList());
    }

    @Override
    public List<Customer> findBlackListCustomers() {
        return new ArrayList<>(cacheForBlackList.values());
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(cacheForCustomer.values());
    }

    private Customer saveCustomerData(Customer customer, String customerList) {
        try {
            FileWriter fileWriter = new FileWriter(path + customerList, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.append(String.valueOf(customer.getCustomerId()))
                    .append(",")
                    .append(customer.getName())
                    .append(",")
                    .append(customer.getEmail());
            bufferedWriter.newLine();

            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return customer;
    }
}
