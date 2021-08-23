package org.prgrms.kdt.devcourse.customer;

import org.prgrms.kdt.devcourse.voucher.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository{
    private Map<UUID, Customer> BlackCustomers = new ConcurrentHashMap<>();
    @Value("${file.customer.blacklist}")
    private String filename;

    @Override
    public List<Customer> getBlackCustomers() {
        try {
            return loadFileData();
        } catch (IOException e) {
            return BlackCustomers.values().stream().toList();
        }
    }

    public List<Customer> loadFileData() throws IOException {


        final String filePath = System.getProperty("user.dir")+"/"+filename;
        final File csvFile = new File(filePath);


        BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
        String line;
        while ((line = bufferedReader.readLine())!=null){
            String [] oneLineDataArr = line.split(",");

            UUID blackCustomerUUID = UUID.fromString(oneLineDataArr[0]);
            String blackCustomerName = oneLineDataArr[1];
            BlackCustomers.put(blackCustomerUUID,new Customer(blackCustomerUUID,blackCustomerName,true));
        }

        return BlackCustomers.values().stream().toList();
    }


}
