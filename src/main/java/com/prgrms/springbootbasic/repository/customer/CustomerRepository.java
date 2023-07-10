package com.prgrms.springbootbasic.repository.customer;

import com.prgrms.springbootbasic.domain.customer.Customer;
import java.util.List;

public interface CustomerRepository {

    //생성(create)
    Customer insert(Customer customer);

    //업데이트(update)
    void update(Customer customer);

    //조회(Read) - 리스트 출력
    List<Customer> findAll();

    //customer 모두 삭제(Delete)
    void deleteAll();
}
