package com.zerozae.voucher.repository.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class CustomerRepository {

    private static final String FILE_PATH = System.getProperty("user.home") + "/customer_blacklist.csv";
    private static final String DELIMITER = ",";
    private final Map<UUID, Customer> customers;

    public CustomerRepository() {
        createFile();
        this.customers = loadBlacklistFromCsvFile();
    }

    public Customer save(Customer customer) {
        CustomerType customerType = customer.getCustomerType();
        if (customerType.equals(CustomerType.BLACKLIST)) {
            try {
                String voucherInfo = getCustomerInfo(customer) + System.lineSeparator();
                Files.writeString(Path.of(FILE_PATH), voucherInfo, StandardOpenOption.APPEND);
            }catch (IOException e) {
                throw ErrorMessage.error("파일에 쓰기 중 문제가 발생했습니다.");
            }
        }
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }

    public List<Customer> findAll() {
        return customers.values().stream().toList();
    }

    private Map<UUID, Customer> loadBlacklistFromCsvFile() {
        Map<UUID, Customer> loadedBlacklist = new ConcurrentHashMap<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(FILE_PATH), StandardCharsets.UTF_8);

            for (String line : lines) {
                String[] customerInfo = line.split(DELIMITER);
                UUID customerId = UUID.fromString(customerInfo[0]);
                String customerName = customerInfo[1];
                CustomerType customerType = CustomerType.valueOf(customerInfo[2]);

                Customer customer = new Customer(customerId, customerName, customerType);

                loadedBlacklist.put(customerId, customer);
            }
        } catch (IOException e) {
            throw ErrorMessage.error("파일을 읽어오던 중 문제가 발생했습니다.");
        }
        return loadedBlacklist;
    }

    private String getCustomerInfo(Customer customer) {
        String customerId = String.valueOf(customer.getCustomerId());
        String customerName = customer.getCustomerName();
        String customerType = String.valueOf(customer.getCustomerType());

        return String.join(DELIMITER, customerId,customerName, customerType);
    }

    private void createFile(){
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw ErrorMessage.error("파일 생성 중 문제가 발생했습니다.");
            }
        }
    }
}
