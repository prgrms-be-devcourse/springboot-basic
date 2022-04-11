package org.prgrms.kdtspringvoucher.customer.repository;

import org.prgrms.kdtspringvoucher.customer.entity.Customer;

import java.util.List;

public interface BlackCustomerRepository {
    List<Customer> listAll();
}
