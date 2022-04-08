package org.programmers.kdt.weekly.customer.repository;

import org.programmers.kdt.weekly.customer.Customer;
import org.programmers.kdt.weekly.voucher.repository.FileBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.UUID;

@Repository
public class BlackListRepository implements CustomerRepository {
    private final static Logger logger = LoggerFactory.getLogger(BlackListRepository.class);
    private final File FILE = new File("customer_blacklist.csv");
    private final BufferedWriter BUFFER_WRITER = FileBuffer.getBufferWriter(FILE);
    private BufferedReader bufferedReader;

    @Override
    public void insert(UUID customerId, Customer customer) {
        try {
            BUFFER_WRITER.write(customer.getCustomerId() + "," + customer.getCustomerName());
            BUFFER_WRITER.newLine();
            BUFFER_WRITER.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getSize() {
        int size = 0;
        try {
            bufferedReader = FileBuffer.getBufferedReader(FILE);
            while ((bufferedReader.readLine()) != null) {
                size++;
            }
        } catch (FileNotFoundException e) {
            logger.error("file not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }

    @Override
    public void showAll() {
        String line = "";
        try {
            bufferedReader = FileBuffer.getBufferedReader(FILE);
            while ((line = bufferedReader.readLine()) != null) {
                String arr[] = line.split(",");
                System.out.println("CustomerID = " + arr[0] +
                        " CustomerName = " + arr[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
