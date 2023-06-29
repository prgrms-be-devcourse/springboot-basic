package kr.co.programmers.springbootbasic.customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> listAllBlackCustomer();
}
