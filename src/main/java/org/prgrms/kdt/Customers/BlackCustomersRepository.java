package org.prgrms.kdt.Customers;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlackCustomersRepository  implements CustomersRepository{
    private final Path Filepath = Paths.get(System.getProperty("user.dir") + "/customer_blacklist.csv");



    @Override
    public List<Customers> findAll() {
        List<Customers> customers=new ArrayList<>();
        try {
            var strings = Files.readAllLines(Filepath);
            for(String string:strings){
                String[]arr=string.split(",");


                long num=Long.parseLong(arr[0]);
                customers.add(new Customers(num,arr[1],arr[2]));
            }

        }
        //질문
        catch (NumberFormatException n){
            return customers;

        }
        catch (IOException n){
            n.printStackTrace();
        }

        return customers;

    }
}
