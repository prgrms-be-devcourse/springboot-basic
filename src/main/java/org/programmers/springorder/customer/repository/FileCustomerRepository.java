package org.programmers.springorder.customer.repository;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.consts.ErrorMessage;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.model.CustomerType;
import org.programmers.springorder.utils.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Profile({"dev", "test"})
@Repository
public class FileCustomerRepository implements CustomerRepository {

    private final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final Console console;

    public FileCustomerRepository(Console console) {
        this.console = console;
    }

    @Override
    public List<Customer> findAllBlackList() {
        return findAll().stream()
                .filter(Customer::isBlackList)
                .toList();
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Properties.getCustomerFilePath()))) {
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

    //TODO: 추후 구현 예정
    @Override
    public Optional<Customer> findByID(UUID customerId) {
        return findAll().stream()
                .filter(customer -> customer.sameCustomerId(customerId))
                .findFirst();
    }

    @Override
    public Customer insert(Customer customer) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Properties.getCustomerFilePath(), true))) {
            String data = customer.insertCustomerDataInFile();
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            logger.error("errorMessage = {}", ErrorMessage.FILE_SAVE_ERROR_MESSAGE);
            console.printMessage(ErrorMessage.FILE_SAVE_ERROR_MESSAGE);
        }
        return customer;
    }

    public void clear(){
        try (FileOutputStream fos = new FileOutputStream(Properties.getCustomerFilePath(), false)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
