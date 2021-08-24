package com.org.prgrms.mission.repository;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.org.prgrms.mission.Input;
import com.org.prgrms.mission.model.ConsumerType;
import com.org.prgrms.mission.model.Customer;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileCustomerRepository implements CustomerRepository{

    @Override
    public Customer insert(Customer customer, String filePath) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder<Customer>(fileWriter).build();
        beanToCsv.write(List.of(customer));
        fileWriter.close();
        return customer;
    }

    @Override
    public List<Customer> ShowList(String filePath) {

        List<Customer> list = new ArrayList<>();

        String line = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while((line = br.readLine()) != null) {
                String[] token = line.replace("\"", "").split(",");
                UUID id = UUID.fromString(token[0]);
                String name = token[1];
                int age = Integer.parseInt(token[2]);
                if(token[3].equals("BLACK_CONSUMER")) list.add(new Customer(id, name, age, ConsumerType.BLACK_CONSUMER)) ;
                else list.add(new Customer(id, name, age, ConsumerType.WHILE_CONSUMER));
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }


}
