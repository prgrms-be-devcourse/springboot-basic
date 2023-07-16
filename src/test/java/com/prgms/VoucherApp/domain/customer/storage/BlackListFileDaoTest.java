package com.prgms.VoucherApp.domain.customer.storage;

import com.prgms.VoucherApp.domain.customer.model.BlackListFileDao;
import com.prgms.VoucherApp.domain.customer.model.Customer;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@JdbcTest
@Import(BlackListFileDao.class)
class BlackListFileDaoTest {

    @Autowired
    private BlackListFileDao blackListFileDao;

    @Test
    @DisplayName("블랙리스트를 조회 할 수 있다.")
    void findBlackListTest() {
        // given

        // when
        List<Customer> findBlacklist = blackListFileDao.findBlacklist();

        List<CustomerStatus> findCustomerStatus = findBlacklist.stream()
            .map(Customer::getCustomerStatus)
            .toList();

        Assertions.assertThat(findCustomerStatus).allMatch(CustomerStatus::isBlackList);
    }
}