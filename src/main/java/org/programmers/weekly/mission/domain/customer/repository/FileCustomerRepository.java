package org.programmers.weekly.mission.domain.customer.repository;

import org.programmers.weekly.mission.domain.customer.model.BlackCustomer;
import org.programmers.weekly.mission.domain.customer.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private final String blackListFilePath;

    public FileCustomerRepository(@Value("${file.blackList}") String blackListFilePath) {
        this.blackListFilePath = blackListFilePath;
    }

    @Override
    public List<BlackCustomer> getBlackList() {
        List<BlackCustomer> blackCustomers = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(blackListFilePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                blackCustomers.add(new BlackCustomer(Integer.parseInt(customerInfo[0]), customerInfo[1]));
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return blackCustomers;
    }

    @Override
    public Customer insert(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Optional<Customer> getCustomerById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void delete(UUID customerId) {
    }
}
