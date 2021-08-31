package org.prgms.order.customer.repository;

import org.prgms.order.customer.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

@Repository
@Profile({"default", "dev"})
public class FileCustomerRepository implements CustomerRepository {
    private static final File file = new File(".");


    @Value("${app.file.blackList}")
    private String filename;

    private String filePath;
    private static final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);

    @Override
    public List<Customer> findBlackList() {
        filePath = MessageFormat.format("{0}/{1}", file.getAbsolutePath(), filename);
        List<Customer> blackList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new BufferedReader(new FileReader(filePath)));
            String customer = "";
            customer = br.readLine();
            while ((customer = br.readLine()) != null) {
                String[] customerInfo = customer.replace(" ", "").split(",");
                UUID customerId = UUID.fromString(customerInfo[0]);
                String name = customerInfo[1];
                String email = customerInfo[2];
                blackList.add(new Customer(customerId, name, email));
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(MessageFormat.format("file read Exception{0}", e.getMessage()));
        }
        return blackList;
    }


}
