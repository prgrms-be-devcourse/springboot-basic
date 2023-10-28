package org.prgms.kdtspringweek1.customer.repository;

import jakarta.annotation.PostConstruct;
import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.exception.FileException;
import org.prgms.kdtspringweek1.exception.FileExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Profile("local")
public class CsvFileCustomerRepository implements CustomerRepository {
    private Map<UUID, Customer> customers;
    private File customerInfoCsv;
    @Value("${spring.file.customer.path}")
    private String customerInfoCsvPath;
    private final static Logger logger = LoggerFactory.getLogger(CsvFileCustomerRepository.class);

    @PostConstruct
    private void init() {
        customerInfoCsv = new File(customerInfoCsvPath);
        prepareCsv();
        try {
            customers = getAllCustomersFromCSV();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
    }

    @Override
    public Customer save(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        updateCustomersInfoOnCsv();
        logger.info("Success to create customer -> {}, {}, {}",
                customer.getCustomerId(),
                customer.getName(),
                customer.getIsBlackCustomer());
        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> allCustomers = new ArrayList<>(customers.values());
        logger.info("Success to findAllCustomers");
        return allCustomers;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

    @Override
    public Customer update(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        updateCustomersInfoOnCsv();
        return customer;
    }

    @Override
    public void deleteAll() {
        customers.clear();
    }

    @Override
    public void deleteById(UUID customerId) {
        customers.remove(customerId);
    }

    @Override
    public List<Customer> findAllBlackConsumer() {
        return new ArrayList<>(customers.values()).stream()
                .filter(Customer::getIsBlackCustomer)
                .collect(Collectors.toList());
    }

    private void prepareCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(customerInfoCsv, true))) {
            if (customerInfoCsv.length() == 0) {
                bw.write("id, name, is black Consumer");
                bw.newLine();
            }
        } catch (IOException ioException) {
            System.out.println("파일 쓰기 에러가 발생했습니다.");
            logger.error("Fail to write file when prepareCsv");
        }
    }

    private Map<UUID, Customer> getAllCustomersFromCSV() {
        Map<UUID, Customer> customers = new ConcurrentHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(customerInfoCsv))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] customerInfo = line.split(", ");
                UUID customerId = UUID.fromString(customerInfo[0]);
                String name = customerInfo[1];
                boolean isBlackConsumer = Boolean.parseBoolean(customerInfo[2]);
                this.customers.put(customerId, Customer.createWithIdAndNameAndIsBlackCustomer(customerId, name, isBlackConsumer));
            }
        } catch (IOException e) {
            logger.error("Fail to read file when getAllVouchersFromCSV");
            throw new FileException(FileExceptionCode.FAIL_TO_READ_DATA_FROM_CSV);
        }

        return customers;
    }

    private void updateCustomersInfoOnCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(customerInfoCsv))) {
            bw.write("id, name, is black Consumer");
            bw.newLine();
            for (Customer customer : new ArrayList<>(this.customers.values())) {
                bw.write(customer.getCustomerId() + ", " + customer.getName() + ", " + customer.getIsBlackCustomer());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.error("Fail to write file when updateVouchersInfoOnCsv");
            throw new FileException(FileExceptionCode.FAIL_TO_UPDATE_DATA_ON_CSV);
        }
    }
}
