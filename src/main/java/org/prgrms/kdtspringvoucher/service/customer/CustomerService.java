package org.prgrms.kdtspringvoucher.service.customer;

import org.prgrms.kdtspringvoucher.entity.customer.Customer;
import org.prgrms.kdtspringvoucher.repository.customer.CustomerJDBCRepository;
import org.prgrms.kdtspringvoucher.utils.Util;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerJDBCRepository customerJDBCRepository;

    public CustomerService(CustomerJDBCRepository customerJDBCRepository) {
        this.customerJDBCRepository = customerJDBCRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerJDBCRepository.findAll();
    }

    public List<Customer> getBlackCustomers() {
        return customerJDBCRepository.findBlackCustomers();
    }

    public List<Customer> getCustomersByVoucher(String voucherId) {
        return customerJDBCRepository.findCustomerByVoucher(UUID.fromString(voucherId));
    }

    public void createCustomer(Customer customer) {
        customerJDBCRepository.insert(customer);
    }

    public boolean isExistCustomer(String customerId) {
        if (Util.isValidUUID(customerId) == false) {
            return false;
        }
        var customer = customerJDBCRepository.findById(UUID.fromString(customerId));
        return !customer.isEmpty();
    }
}
