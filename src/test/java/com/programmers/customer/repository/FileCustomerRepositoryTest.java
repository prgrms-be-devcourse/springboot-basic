package com.programmers.customer.repository;


import com.programmers.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class FileCustomerRepositoryTest {
    FileCustomerRepository repository = new FileCustomerRepository();

    @Test
    @DisplayName("블랙리스트에 저장된 인원 수와 레포지토리에서 검색한 인원의 수가 동일해야 한다.")
    void 블랙리스트명단조회테스트() {
        List<Customer> blackList = repository.findAllBlackList();
        assertThat(blackList.size()).isEqualTo(5);
    }


    @Test
    @DisplayName("레포지토리에서 조회한 명단이 블랙리스트와 정확히 일치하는지 검사한다.")
    void 블랙리스트명단조회테스트2() {
        List<Customer> blackList = repository.findAllBlackList();
        List<String> names = blackList.stream()
                .map(customer -> customer.getName())
                .collect(Collectors.toList());

        assertThat(names).containsExactly("kiseo", "geonwoo", "chanmi", "yeongsoo", "yeongji");
    }
}