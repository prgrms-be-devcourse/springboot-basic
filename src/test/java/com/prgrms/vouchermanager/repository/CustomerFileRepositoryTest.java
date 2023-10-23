package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


class CustomerFileRepositoryTest {

    private final BlacklistFileRepository repository = new BlacklistFileRepository("src/main/resources/customer_blacklist.csv");

    @Test
    @DisplayName("블랙리스트 조회")
    void blackList() {
        List<Customer> blacklist = repository.blacklist();
        Assertions.assertThat(blacklist.size()).isEqualTo(3);
    }

}
