package org.prgrms.voucherapplication.repository;

import org.prgrms.voucherapplication.entity.Customer;

import java.io.IOException;
import java.util.List;

/**
 * 고객 데이터를 관리하는 레포지포리
 */
public interface CustomerRepository {

    List<Customer> findAll() throws IOException;
}
