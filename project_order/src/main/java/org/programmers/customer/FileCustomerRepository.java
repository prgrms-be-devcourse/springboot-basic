package org.programmers.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileCustomerRepository implements CustomerRepository{

    @Value("${prgms.file.blacklist}")
    private String blackFile;

    private BufferedReader br;

    @Override
    public List<Customer> getAllCustomersOnBlacklist() {
        List<Customer> customerList = new ArrayList<>();
        String str;

        try {
            br = Files.newBufferedReader(Path.of(blackFile));

            while((str = br.readLine())!=null){
                String[] customerInfo = str.split(" ");
                customerList.add(new Customer(UUID.fromString(customerInfo[0]), customerInfo[1], Gender.valueOf(customerInfo[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customerList;
    }

}
