package org.prgrms.vouchermanager.Repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("production")
public class CsvCustomerRepository implements CustomerRepositroy{

    String csvFilePath = "D:\\Users\\programmers\\vouchermanager\\src\\main\\java\\org\\prgrms\\vouchermanager\\Repository\\blacklist.csv";
    List<Customer> result = new ArrayList<>();
    BufferedReader br = null;


    @Override
    public List<Customer> findAll() {
        try{
            br = Files.newBufferedReader(Paths.get(csvFilePath));
            String line = "";
            while((line = br.readLine()) != null){
                List<String> stringList = new ArrayList<>();
                String arr[] = line.split(",");
                Customer customer = new Customer(arr[0], arr[1]);
                result.add(customer);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }
}
