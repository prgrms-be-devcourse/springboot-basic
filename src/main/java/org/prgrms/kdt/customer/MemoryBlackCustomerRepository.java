package org.prgrms.kdt.customer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryBlackCustomerRepository implements CustomerRepository, InitializingBean {
    private List<String> blackCustomerList = new ArrayList<>();

    @Value("${kdt.customerBlacklistFilePath}")
    private String customerBlacklistFilePath;

    @Override
    public List<String> getList() {
        return blackCustomerList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(customerBlacklistFilePath));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String customerInfo[] = line.split(",");
                blackCustomerList.add("고객 ID : " + customerInfo[0] + ", 고객 이름 : " + customerInfo[1]);
            }
        } catch (FileNotFoundException fileNotFoundException) {

        } finally {
            if (reader != null) reader.close();
        }
    }
}
