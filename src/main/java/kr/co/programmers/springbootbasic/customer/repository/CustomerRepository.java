package kr.co.programmers.springbootbasic.customer.repository;

import kr.co.programmers.springbootbasic.customer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer createCustomer(Customer customer);
    Optional<Customer> findByCustomerId(String customerId);
    List<Customer> findAll();
    Customer update(Customer customer);

    void deleteWalletByWalletId(UUID walletId);

    void deleteCustomerById(String customerId);

    void deleteVoucherByWalletId(UUID walletId);

    Optional<Customer> findByVoucherId(String voucherId);
}
