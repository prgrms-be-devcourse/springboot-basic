package org.programmers.springorder.repository.customer;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.constant.ErrorMessage;
import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.model.customer.CustomerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class FileBlacklistCustomerRepository {

    private final Logger logger = LoggerFactory.getLogger(FileBlacklistCustomerRepository.class);
    private final Console console;
    @Value(("${customerListFilePath}"))
    private String filePath;

    public FileBlacklistCustomerRepository(Console console) {
        this.console = console;
    }

    public List<Customer> findAllBlackList() {
        return findAll().stream()
                .filter(Customer::isBlackList)
                .toList();
    }

    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                UUID customerId = UUID.fromString(data[0]);
                String name = data[1];
                CustomerType customerType = CustomerType.valueOf(data[2]);
                Customer customer = Customer.toCustomer(customerId, name, customerType);
                customerList.add(customer);
            }
        } catch (FileNotFoundException e) {
            logger.error("errorMessage = {}", ErrorMessage.FILE_NOT_EXIST_MESSAGE);
            console.printMessage(ErrorMessage.FILE_NOT_EXIST_MESSAGE);
        } catch (IOException e) {
            logger.error("errorMessage = {}", ErrorMessage.FILE_FAIL_LOADING_MESSAGE);
            console.printMessage(ErrorMessage.FILE_FAIL_LOADING_MESSAGE);
        }

        return Collections.unmodifiableList(customerList);
    }
}
