package com.pgms.part1.domain.customer.repository;

import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.wallet.entity.Wallet;

import java.util.List;

public interface CustomerRepository {
    public List<Customer> listBlockedCustomers();
    public List<Customer> listCustomers();
    public void addCustomer(Customer customer);
    public void updateCustomerName(Long id, String name);
    public void deleteCustomer(Long id);
    public List<Customer> listCustomersByWallets(List<Wallet> wallets);
    public void updateCustomerBlocked(Long id);
    public int findCustomerByEmail(String email);
}
