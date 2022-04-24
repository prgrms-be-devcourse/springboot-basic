package org.programs.kdt.Customer;

import lombok.Getter;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Exception.ErrorCode;
import org.programs.kdt.Utils.FileUtil;
import org.programs.kdt.Voucher.configure.FileProperty;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Profile("staging")
@Repository
@Getter
@EnableConfigurationProperties
public class FileCustomerRepository implements CustomerRepository, InitializingBean, DisposableBean {

    public FileCustomerRepository(FileProperty fileProperty) {
        this.filePath = fileProperty.getBlacklist();
    }

    private Map<UUID, Customer> storage = new LinkedHashMap<UUID, Customer>();

    private String filePath;

    @Override
    public Customer insert(Customer customer) {
        synchronized (this) {
            storage.put(customer.getCustomerId(), customer);
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        storage.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public int count() {
        return storage.size();
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(storage.get(customerId));
    }

    @Override
    public List<Customer> findByName(String name) {
        return storage.values().stream()
                .filter(customer -> customer.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return storage.values().stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void deleteAll() {
        storage = new LinkedHashMap<UUID, Customer>();
    }

    @Override
    public List<Customer> findByType(String type) {
        return storage.values().stream()
                .filter(customer -> customer.getCustomerType().getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<Customer> findEmailCustomer = storage.values().stream().filter(customer -> customer.getEmail().equals(email)).findFirst();
        if (findEmailCustomer.isPresent()) {
            storage.remove(findEmailCustomer.get().getCustomerId());
        } else {
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND_EMAIL);
        }
    }

    @Override
    public boolean existEmail(String email) {
        return storage.values().stream().anyMatch(customer -> customer.getEmail().equals(email));
    }

    @Override
    public boolean existId(UUID customerId) {
        return storage.containsKey(customerId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.storage = fileReadToCustomerMap();
    }

    private Map<UUID, Customer> fileReadToCustomerMap() {
        List<String> customerStringList = FileUtil.readFileByList(filePath);
        Map<UUID, Customer> customerMap = new LinkedHashMap<>();

        for(String customerString : customerStringList) {
            String[] split = customerString.split(",");
            UUID customerId= UUID.fromString(split[0]);
            String name = split[1];
            String email = split[2];
            CustomerType customerType = CustomerType.find(split[3]);
            LocalDateTime createdAt =  LocalDateTime.parse(split[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            Customer customer = new Customer(customerId, name, email, createdAt, customerType);
            customerMap.put(customerId, customer);
        }
        return customerMap;
    }

    private void fileWriteByCustomerMap() {
        StringBuilder customerMapToString = new StringBuilder();
        storage.values().stream().forEach(customer -> customerMapToString.append(customerToCsvString(customer) + "\n"));
        FileUtil.fileWriteLine(filePath, customerMapToString.toString());
    }

    public String customerToCsvString(Customer customer) {
        return customer.getCustomerId().toString() + "," + customer.getName() + "," + customer.getEmail() +
                customer.getCustomerType().getType() + ","  + customer.getCreatedAt();
    }

    @Override
    public void destroy() throws Exception {
        fileWriteByCustomerMap();
    }
}
