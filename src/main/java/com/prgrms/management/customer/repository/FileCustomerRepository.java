package com.prgrms.management.customer.repository;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("local")
public class FileCustomerRepository implements CustomerRepository {
    private final String BLACK_LIST_FILE_NAME = "src/main/resources/customer_blacklist.csv";

    @Override
    public Customer insert(Customer customer) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(BLACK_LIST_FILE_NAME, true))) {
            bufferedWriter.write(customer.getCustomerId() + "," + customer.getCustomerType());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customer;
    }


    @Override
    public List<Customer> findBlackList() {
        List<Customer> blackCustomerList = new ArrayList<>();
        //try-with-resource
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(BLACK_LIST_FILE_NAME))) {
            String reader;
            while ((reader = bufferedReader.readLine()) != null) {
                String[] voucherInfo = reader.split(",");
                CustomerType type = CustomerType.of(voucherInfo[1]);
                if (type.equals(CustomerType.BLACKLIST)) blackCustomerList.add(new Customer(CustomerType.BLACKLIST));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blackCustomerList;
    }
}
