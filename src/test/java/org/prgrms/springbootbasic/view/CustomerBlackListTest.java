package org.prgrms.springbootbasic.view;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
class CustomerBlackListTest {

    @Autowired
    ApplicationContext applicationContext;

    @DisplayName("블랙리스트 조회 테스트")
    @Test
    void printCustomerBlackList() throws IOException {
        //given
        //when
        //then
        new CustomerBlackList(applicationContext).printCustomerBlackList();
    }
}