package org.prgms.kdtspringweek1.customer.repository;

import jakarta.annotation.PostConstruct;
import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.exception.FileException;
import org.prgms.kdtspringweek1.exception.FileExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CsvFileCustomerRepository implements CustomerRepository {
    private Map<UUID, Customer> blackCustomers;
    private File blackCustomerInfoCsv;
    @Value("${spring.file.black-customer.path}")
    private String blackCustomerInfoCsvPath;
    private final static Logger logger = LoggerFactory.getLogger(CsvFileCustomerRepository.class);

    @PostConstruct
    private void init() {
        blackCustomerInfoCsv = new File(blackCustomerInfoCsvPath);
    }

    @Override
    public List<Customer> findAllBlackConsumer() {
        blackCustomers = getAllBlackCustomersFromCSV();
        logger.info("Success to findAllVouchers");
        return new ArrayList<>(blackCustomers.values());
    }

    private Map<UUID, Customer> getAllBlackCustomersFromCSV() {
        Map<UUID, Customer> blackCustomers = new ConcurrentHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(blackCustomerInfoCsv))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] blackCustomerInfo = line.split(", ");
                UUID blackCustomerId = UUID.fromString(blackCustomerInfo[0]);
                String name = blackCustomerInfo[1];

                blackCustomers.put(blackCustomerId, new Customer(blackCustomerId, name, true));
            }
        } catch (IOException e) {
            logger.error("Fail to read file when getAllVouchersFromCSV");
            throw new FileException(FileExceptionCode.FAIL_TO_READ_DATA_FROM_CSV);
        }

        return blackCustomers;
    }
}
