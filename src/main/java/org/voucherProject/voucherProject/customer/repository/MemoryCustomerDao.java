package org.voucherProject.voucherProject.customer.repository;

import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryCustomerDao implements CustomerDao {

    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(storage.get(customerId));
    }

    @Override
    public Optional<Customer> findByName(String customerName) {
        return storage.values().stream().filter(c -> c.getCustomerName().equals(customerName)).findFirst();
    }

    @Override
    public Optional<Customer> findByEmail(String customerEmail) {
        return storage.values().stream().filter(c -> c.getCustomerEmail().equals(customerEmail)).findFirst();
    }

    @Override
    public List<Customer> findByVoucherType(VoucherType voucherType) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Customer save(Customer customer) {
        if (findById(customer.getCustomerId()).isPresent()) {
            throw new RuntimeException("동일한 아이디가 존재합니다.");
        }
        storage.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
