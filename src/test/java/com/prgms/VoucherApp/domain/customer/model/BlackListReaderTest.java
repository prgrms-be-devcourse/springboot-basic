package com.prgms.VoucherApp.domain.customer.model;

import com.prgms.VoucherApp.domain.customer.CustomerStatus;
import com.prgms.VoucherApp.domain.customer.dto.CustomerDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlackListReaderTest {

    @Autowired
    private BlackListReader blackListReader;

    @Test
    @DisplayName("readBlackList")
    void readBlackListTest() {
        List<CustomerStatus> list = blackListReader.readBlackLists()
                .stream()
                .map(CustomerDto::getCustomerStatus)
                .toList();

        Assertions.assertThat(list).allMatch(CustomerStatus::isBlackList);
    }
}