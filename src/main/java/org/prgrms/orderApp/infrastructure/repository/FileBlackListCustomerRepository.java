package org.prgrms.orderApp.infrastructure.repository;

import org.prgrms.orderApp.domain.customer.model.Customer;
import org.prgrms.orderApp.domain.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileBlackListCustomerRepository implements CustomerRepository {
    private static String storagePath = "src/main/resources/storage/customer_blacklist.csv";

    private final static Logger logger = LoggerFactory.getLogger(FileBlackListCustomerRepository.class);

    @Override
    public List<Customer> findAll() {
        File blackListFromFile = new File(storagePath);
        BufferedReader blackListContentsFromFile = null;
        List<Customer> blackList = new ArrayList<Customer>();
        try{
            blackListContentsFromFile = new BufferedReader(new FileReader(blackListFromFile));
            Charset.forName("UTF-8");
            String contentsFromFile = "";

            int index =0;
            while((contentsFromFile=blackListContentsFromFile.readLine()) != null) {
                index += 1;
                String[] contents = contentsFromFile.split(",");

                if (index == 1){
                    continue;
                }
                // Content Column => blackListCustomerId
                UUID blackListCustomerId = UUID.fromString(contents[0]);
                blackList.add(new Customer(blackListCustomerId));

            }

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            try {
                if(blackListContentsFromFile != null) {blackListContentsFromFile.close();}
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return blackList;

    }

}
