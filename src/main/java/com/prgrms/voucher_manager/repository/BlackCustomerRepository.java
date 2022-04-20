package com.prgrms.voucher_manager.repository;

import com.prgrms.voucher_manager.customer.BlackCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class BlackCustomerRepository implements CustomerRepository{

    private static final Logger logger = LoggerFactory.getLogger(BlackCustomerRepository.class);
    private static final File file = new File("customer_blacklist.csv");
    private static BufferedReader br;

    @Override
    public int getSize() {
        int size = 0;
        String line;
        try{
            br = getBufferReader(file);
            while((line = br.readLine()) != null){
                size++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    @Override
    public void findAll() {
        try{
            String lineString = "";
            br = getBufferReader(file);
            lineString = br.readLine();
            while((lineString = br.readLine()) != null){
                String[] arr = lineString.split(",");
                System.out.println(BlackCustomer.builder()
                        .id(Integer.parseInt(arr[0]))
                        .name(arr[1])
                        .phoneNumber(arr[2])
                        .build()
                        .getInfo());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getBufferReader(File file){
        try{
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            logger.error("BlackCustomerRepo - error read File");
            e.printStackTrace();
        }
        return br;
    }


}
