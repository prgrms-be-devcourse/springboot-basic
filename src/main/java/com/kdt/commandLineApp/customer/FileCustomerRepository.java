package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toCollection;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private final int COLUMN_COUNT = 3;
    private ArrayList<Customer> customerList = new ArrayList<>();

    public FileCustomerRepository(@Value("${customer_blacklist_info}") String fileName) {
        try {
            loadFile(fileName);
        }
        catch (Exception e) {
            //no need to throw exception (it can use empty array list)
        }
    }

    private void loadFile(String fileName) throws Exception {
        int i = 0;
        Scanner sc = new Scanner(new File(fileName));

        try {
            while (sc.hasNext()) {
                String str = sc.nextLine();
                ArrayList<String> params = Arrays.stream(str.split(",")).collect(toCollection(ArrayList::new));
                String name = params.get(0);
                String age = params.get(1);
                String sex = params.get(2);
                customerList.add(new Customer(name, age, sex));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            sc.close();
            throw new WrongCustomerParamsException();
        }
        sc.close();
    }

    @Override
    public List<Customer> getAll() {
        return customerList;
    }
}
