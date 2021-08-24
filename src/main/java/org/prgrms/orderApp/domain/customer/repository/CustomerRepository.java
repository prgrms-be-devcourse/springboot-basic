package org.prgrms.orderApp.domain.customer.repository;

import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.domain.customer.model.Customer;
import org.prgrms.orderApp.domain.customer.model.CustomerModel;
import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface CustomerRepository {
    List<Customer> findAll() ;

}
