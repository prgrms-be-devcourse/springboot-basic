package org.prgrms.kdt.Customers;

import org.prgrms.kdt.Voucher.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BlackCustomersRepository  implements CustomersRepository{

    @Value("${black-list.path}")
    String Filename;

    //Map에 담아 놓아 Thread-free
    private final Map<String, Customers> storage=new ConcurrentHashMap<>();
//
    public void load(){
        List<Customers> customers=new ArrayList<>();

        Path Filepath = Paths.get(System.getProperty("user.dir") + "/"+Filename);
        try {
            var strings = Files.readAllLines(Filepath);
            for(String string:strings)
            {
                System.out.println(string);
                List<String> arr= Arrays.asList(string.split(","));

                storage.put(arr.get(1),new Customers(Long.parseLong(arr.get(0)),arr.get(1),arr.get(2)));
            }

        }

        catch (IOException n){
            System.out.println("파일을 찾지 못하였습니다");
            n.printStackTrace();
        }

    }

    @Override
    public List<Customers> findAll() {
        if (storage.isEmpty()){
           load();
        }
        return new ArrayList<>(storage.values());
    }
}
