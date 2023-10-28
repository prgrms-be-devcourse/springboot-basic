package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.mapper.CustomerMapper;
import com.programmers.vouchermanagement.utils.CsvFileIoManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"file", "memory"})
public class FileCustomerRepository implements CustomerRepository {

    private static final File FILE = new File(System.getProperty("user.dir") + "/src/main/resources/customer_blacklist.csv");

    private final CsvFileIoManager csvFileIoManager;
    private final Map<UUID, Customer> storage;

    public FileCustomerRepository(CsvFileIoManager csvFileIoManager) {
        this.csvFileIoManager = csvFileIoManager;
        this.storage = addCustomerByFile(csvFileIoManager.readCsv(FILE));
    }

    @Override
    public List<Customer> findAllBlack() {
        return storage.values()
                .stream()
                .filter(customer -> customer.getCustomerType().equals(CustomerType.BLACK))
                .toList();
    }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {

            String data;

            while ((data = br.readLine()) != null) {

                String[] singleData = data.split(",");

                Customer customer = CustomerMapper.toEntity(singleData);
                customers.add(customer);
            }
        } catch (FileNotFoundException e) {
            throw new FileIOException("File not found. ");

        } catch (IOException e) {
            throw new FileIOException("Customer not read due to file issue. ");
        }

        return customers;
    }
}
