package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.dto.ReadVoucherDto;
import com.programmers.springweekly.repository.voucher.FileVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileCustomerRepository implements CustomerRepository{

    @Value("${file.customer.path}")
    private String FILE_PATH;
    private final Map<UUID, Customer> customerMap = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);

    @Override
    public Map<UUID, Customer> getCustomerTypeBlackList() {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(FILE_PATH));
            String line = "";

            while((line = bufferedReader.readLine()) != null){
                String[] readLine = line.split(",");
                checkCustomerBlack(readLine);
            }
        } catch (Exception e){
            logger.error("error message: {}", e.getMessage());
        }

        return customerMap;
    }

    private void checkCustomerBlack(String[] readLine){
        if(readLine[1].equals("black")){
            Customer customer = new Customer(UUID.fromString(readLine[0]), readLine[1]);

            customerMap.put(customer.getCustomerId(), customer);
        }
    }
}
