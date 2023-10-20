package org.prgrms.vouchermanager.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("production")
@RequiredArgsConstructor
@Slf4j
public class CsvCustomerRepository implements CustomerRepositroy{

    @Value("${csv.file-path}")
    private String csvFilePath;





    @Override
    public List<Customer> findAll() {
        BufferedReader br = null;
        List<Customer> result = new ArrayList<>();
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
            log.error("Fail to read file");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }
}
