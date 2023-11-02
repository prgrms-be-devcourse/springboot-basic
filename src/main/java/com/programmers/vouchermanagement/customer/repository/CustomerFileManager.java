package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.properties.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.programmers.vouchermanagement.constant.Constant.COMMA_SEPARATOR;
import static com.programmers.vouchermanagement.constant.Message.FILE_EXCEPTION;
import static com.programmers.vouchermanagement.constant.Message.IO_EXCEPTION;

@Component
public class CustomerFileManager {
    private static final Logger logger = LoggerFactory.getLogger(CustomerFileManager.class);
    public final Map<UUID, Customer> customers;
    private final String filePath;

    public CustomerFileManager(AppProperties appProperties) {
        this.filePath = appProperties.resources().path() + appProperties.domains().get("customer").fileName();
        this.customers = new HashMap<>();
        loadFile();
    }

    private void loadFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip the first line
            String str;
            while ((str = br.readLine()) != null) {
                Customer customer = stringToCustomer(str);
                customers.put(customer.getId(), customer);
            }
        } catch (IOException e) {
            logger.warn(IO_EXCEPTION);
            throw new UncheckedIOException(e);
        }
    }

    private Customer stringToCustomer(String line) {
        String[] customerInfo = line.split(COMMA_SEPARATOR);
        UUID customerId = UUID.fromString(customerInfo[0]);
        String name = customerInfo[1];
        boolean isBlack = Boolean.parseBoolean(customerInfo[2]);
        return new Customer(customerId, name, isBlack);
    }

    public void saveFile() {
        File csvOutputFile = new File(filePath);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            customers.values().forEach((customer) -> pw.println(customerToString(customer)));
        } catch (FileNotFoundException e) {
            logger.warn(FILE_EXCEPTION);
            throw new RuntimeException(e);
        }
    }

    private String customerToString(Customer customer) {
        return customer.getId().toString() + COMMA_SEPARATOR
                + customer.getName() + COMMA_SEPARATOR
                + customer.isBlack();
    }
}
