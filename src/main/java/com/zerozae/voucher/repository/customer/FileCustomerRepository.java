package com.zerozae.voucher.repository.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.util.FileUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("file")
@Repository
public class FileCustomerRepository implements CustomerRepository {

    private static final String FILE_PATH = System.getProperty("user.home") + "/customer.csv";
    private static final String DELIMITER = ",";
    private final Map<UUID, Customer> customers;
    private final FileUtil fileUtil;

    public FileCustomerRepository(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
        this.customers = initData();
    }

    public Customer save(Customer customer) {
        String voucherInfo = getCustomerInfo(customer) + System.lineSeparator();
        fileUtil.saveToCsvFile(voucherInfo,FILE_PATH);
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }

    public List<Customer> findAll() {
        return customers.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

    @Override
    public void deleteById(UUID customerId) {
        customers.remove(customerId);
        fileUtil.deleteFileDataById(customerId, FILE_PATH);
    }

    @Override
    public void deleteAll() {
        customers.clear();
        fileUtil.clearDataFile(FILE_PATH);
    }

    @Override
    public void update(UUID customerId, CustomerRequest customerRequest) {
        Customer customer = customers.get(customerId);
        customer.updateCustomerInfo(customerRequest);
        fileUtil.updateFile(getCustomerInfo(customer), customerId, FILE_PATH);
    }

    private String getCustomerInfo(Customer customer) {
        String customerId = String.valueOf(customer.getCustomerId());
        String customerName = customer.getCustomerName();
        String customerType = String.valueOf(customer.getCustomerType());

        return String.join(DELIMITER, customerId,customerName, customerType);
    }

    private Map<UUID, Customer> initData() {
        fileUtil.createFile(FILE_PATH);
        Map<UUID,Customer> loadedCustomer = new ConcurrentHashMap<>();
        List<String> loadedData = fileUtil.loadFromCsvFile(FILE_PATH);

        for (String data : loadedData) {
            String[] customerInfo = data.split(DELIMITER);
            UUID customerId = UUID.fromString(customerInfo[0]);
            String customerName = customerInfo[1];
            CustomerType customerType = CustomerType.valueOf(customerInfo[2]);

            Customer customer = new Customer(customerId, customerName, customerType);

            loadedCustomer.put(customerId, customer);
        }
        return loadedCustomer;
    }
}