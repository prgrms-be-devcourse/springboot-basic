package com.prgms.voucher.voucherproject.repository.customer;

import com.prgms.voucher.voucherproject.domain.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository {

    public void save(Customer customer);

    public List<Customer> findAll();

    public Optional<Customer> findByEmail(String email);

    public void deleteByEmail(String email);

}
