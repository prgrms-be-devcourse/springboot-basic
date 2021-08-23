package com.prgrm.kdt.customer.application;

import com.prgrm.kdt.customer.domain.Customer;
import com.prgrm.kdt.customer.repository.CustomerRepository;
import com.prgrm.kdt.voucher.domain.FixedAmountVoucher;
import com.prgrm.kdt.voucher.domain.PercentDiscountVoucher;
import com.prgrm.kdt.voucher.domain.Voucher;
import com.prgrm.kdt.voucher.domain.VoucherType;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.prgrm.kdt.voucher.domain.VoucherType.FIXED;
import static com.prgrm.kdt.voucher.domain.VoucherType.PERCENT;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(UUID customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a customer for {0}", customerId)));
    }

    public Map<UUID, Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    public Map<UUID, Customer> readBlackListFile(String path) {
        Map<UUID, Customer> blackList = new ConcurrentHashMap<>();
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] token = line.split(" ");
                for (int i = 0; i < token.length; i++) {
                    UUID customerId = UUID.fromString(token[0]);
                    String customerName = token[1];
                    String phoneNumber = token[2];
                    Customer customer = new Customer(customerId, customerName, phoneNumber, true);
                    blackList.put(customer.getCustomerId(), customer);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blackList;
    }

    public Customer registerCustomer(String name, String phoneNumber) {
        return new Customer(UUID.randomUUID(), name, phoneNumber);
    }

    public Customer insertCustomer(Customer customer) {
        customerRepository.insert(customer);
        return customer;
    }
}
