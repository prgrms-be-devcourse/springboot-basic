package com.programmers.customer.repository;


import com.programmers.customer.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class FileCustomerRepositoryTest {
    FileCustomerRepository repository = new FileCustomerRepository();

    @Test
    void 블랙리스트명단조회테스트() {
        List<Customer> blackList = repository.findAllBlackList();
        assertThat(blackList.size()).isEqualTo(5);
    }


    @Test
    void 블랙리스트명단조회테스트2() {
        List<Customer> blackList = repository.findAllBlackList();
        List<String> names = blackList.stream()
                .map(customer -> customer.getName())
                .collect(Collectors.toList());

        assertThat(names).containsExactly("kiseo", "geonwoo", "chanmi", "yeongsoo", "yeongji");
    }
}