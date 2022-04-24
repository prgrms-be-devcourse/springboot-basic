package com.prgrms.voucher_manager.customer.repository;

//import com.prgrms.voucher_manager.customer.BlackCustomer;
import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.SimpleCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BlackCustomerRepository{

    private static final Logger logger = LoggerFactory.getLogger(BlackCustomerRepository.class);
    private static final File file = new File("customer_blacklist.csv");
    private static BufferedReader br;
    private static BufferedWriter bw;


    public Integer count() {
        int size = 0;
        String line;
        try{
            br = getBufferReader(file);
            while((line = br.readLine()) != null){
                size++;
            }
        } catch (IOException e) {
            logger.info("",e);
        }
        return size;
    }


    public Customer insert(Customer customer) {
        bw = null;
        try {
            bw = getBufferWriter(file);

            String insertData = customer.getCustomerId()
                    + "," + customer.getName()
                    + "," + customer.getEmail();

            bw.write(insertData);
            // 작성한 데이터를 파일에 넣는다
            bw.newLine(); // 개행
        } catch (IOException e) {
            logger.info("", e);
        } finally {
            try{
                if(bw != null) {
                    bw.flush();
                }
            } catch (IOException e) {
                logger.info("", e);
            }
        }
        return customer;
    }


    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try{
            String lineString = "";
            br = getBufferReader(file);
            lineString = br.readLine();
            while((lineString = br.readLine()) != null){
                String[] arr = lineString.split(",");
                Customer blackCustomer = SimpleCustomer.builder()
                        .customerId(UUID.fromString((arr[0])))
                        .name(arr[1])
                        .email(arr[2])
                        .build();
                customers.add(blackCustomer);
                System.out.println(blackCustomer.toString());
            }
        } catch (IOException e) {
            logger.info("",e);
        }
        return customers;
    }

    public Optional<Customer> findById(UUID customerId) {
        return Optional.empty();
    }

    public void deleteAll() {}

    private BufferedReader getBufferReader(File file){
        try{
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            logger.error("BlackCustomerRepo - error read File");
        }
        return br;
    }

    private BufferedWriter getBufferWriter(File file){
        try{
            bw = new BufferedWriter(new FileWriter(file, true));
        } catch (FileNotFoundException e) {
            logger.error("BlackCustomerRepo - error read File");
        } catch (IOException e) {
        }
        return bw;
    }

    @PreDestroy
    private void preDestroy() throws IOException {
        br.close();
        bw.close();
    }

}
