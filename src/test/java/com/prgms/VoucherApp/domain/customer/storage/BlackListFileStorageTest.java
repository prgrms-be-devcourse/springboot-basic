package com.prgms.VoucherApp.domain.customer.storage;

import com.prgms.VoucherApp.domain.customer.Customer;
import com.prgms.VoucherApp.domain.customer.CustomerStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlackListFileStorageTest {

    @Autowired
    private BlackListFileStorage blackListFileStorage;

    @Test
    @DisplayName("블랙리스트를 조회 할 수 있다.")
    void findBlackListTest() {
        // given

        // when
        List<Customer> findBlacklist = blackListFileStorage.findBlacklist();

        List<CustomerStatus> findCustomerStatus = findBlacklist.stream()
                .map(Customer::getCustomerStatus)
                .toList();

        Assertions.assertThat(findCustomerStatus).allMatch(CustomerStatus::isBlackList);
    }
}