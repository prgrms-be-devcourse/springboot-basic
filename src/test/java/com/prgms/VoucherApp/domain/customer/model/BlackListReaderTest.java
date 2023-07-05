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
    @DisplayName("블랙리스트를 조회 할 수 있다.")
    void readBlackListTest() {
        //given

        //when
        List<CustomerDto> findBlackList = blackListReader.readBlackLists();

        //then
        Assertions.assertThat(findBlackList)
                .extracting(CustomerDto::getCustomerStatus)
                .allMatch(CustomerStatus::isBlackList);
    }
}