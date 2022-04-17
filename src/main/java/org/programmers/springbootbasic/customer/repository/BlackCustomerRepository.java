package org.programmers.springbootbasic.customer.repository;

import org.programmers.springbootbasic.customer.model.BlackListCustomer;
import org.programmers.springbootbasic.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class BlackCustomerRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(BlackCustomerRepository.class);

    private final File file = new File("customer_blacklist.csv");
    private BufferedReader bufferedReader;

    @Override
    public List<Customer> findAll() {
        List<Customer> blackCustomerList = new ArrayList<>();

        String line;
        try {
            bufferedReader = getBufferedReader(file);
            while ((line = bufferedReader.readLine()) != null) {
                String[] blackListCustomerInfo = line.split(" ");
                if (blackListCustomerInfo[0].equals("BlackListCustomer")) {
                    BlackListCustomer blackListCustomer =
                            new BlackListCustomer(UUID.fromString(blackListCustomerInfo[2]), blackListCustomerInfo[4]);
                    blackCustomerList.add(blackListCustomer);
                }
            }
        } catch (IOException e) {
            logger.error("find IO exception error");
        }
        return blackCustomerList;
    }

    private BufferedReader getBufferedReader(File file) {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException error");
        }
        return bufferedReader;
    }
}


