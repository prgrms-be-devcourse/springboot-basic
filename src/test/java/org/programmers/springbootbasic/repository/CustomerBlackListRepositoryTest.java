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
     * FILE DATA 목록
     * 1, 김영빈
     * 2, 모던자바
     * 3, 이펙티브
     * 4, 오브젝트
     * 5, 토비
     * 6, 헤드
     * 7, HTTP 완벽
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