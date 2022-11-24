package org.programmers.springbootbasic.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.domain.customer.model.Customer;
import org.programmers.springbootbasic.domain.customer.dto.CustomerOutputDto;
import org.programmers.springbootbasic.domain.customer.repository.CustomerBlackListRepository;

import java.util.List;

class CustomerBlackListRepositoryTest {

    CustomerBlackListRepository customerBlackListRepository = new CustomerBlackListRepository("src/test/resources/customer_blacklist.csv");

    /**
     1, 김영빈, a@gmail.com, 2021-11-05 13:47:13.248233
     2, 모던자바, b@gmail.com, 2021-11-06 13:47:13.248233
     3, 이펙티브, c@gmail.com, 2021-11-07 14:47:13.248233
     4, 오브젝트, d@gmail.com, 2021-11-07 16:47:13.248233
     5, 토비, a@gmail.com, 2021-11-08 13:50:13.248233
     6, 헤드, a@gmail.com, 2021-11-10 17:47:13.248233
     7, HTTP 완벽, a@gmail.com, 2022-11-05 13:47:13.248233
     */


    @Test
    void 블랙리스트_전체조회_테스트() throws Exception {
        //given
        // when
        List<Customer> blackList = customerBlackListRepository.findAll();
        //then
        Assertions.assertThat(blackList.size()).isEqualTo(7);
    }

    @Test
    void 블랙리스트_전체조회_1개_데이터_검증_테스트() throws Exception {
        //given
        CustomerOutputDto customer = new CustomerOutputDto(1, "김영빈");
        //when
        List<Customer> blackList = customerBlackListRepository.findAll();
        //then

        Assertions.assertThat(blackList.get(0).getName()).isEqualTo(customer.name());
        Assertions.assertThat(blackList.get(0).getCustomerId()).isEqualTo(customer.id());
    }

}