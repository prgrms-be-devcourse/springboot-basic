package org.programmers.weekly.mission.domain.customer.repository;

import org.programmers.weekly.mission.domain.customer.model.BlackCustomer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private final String blackListFilePath;

    public FileCustomerRepository(@Value("${file.blackList}") String blackListFilePath) {
        this.blackListFilePath = blackListFilePath;
    }

    @Override
    public List<BlackCustomer> getBlackList() throws IOException {
        List<BlackCustomer> blackCustomers = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(blackListFilePath));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] customerInfo = line.split(",");
            blackCustomers.add(new BlackCustomer(Integer.parseInt(customerInfo[0]), customerInfo[1]));
        }
        return blackCustomers;
    }
}
