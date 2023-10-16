package com.prgrms.voucher_manage.util;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FileManager{

    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();

    public Map<UUID, Customer> loadData(String path){
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Customer customer = new Customer(UUID.fromString(data[0]), data[1]);
                storage.put(UUID.fromString(data[0]), customer);
            }
        }  catch (Exception e){
            System.out.println(e.getMessage());
        }
        return storage;
    }
}
