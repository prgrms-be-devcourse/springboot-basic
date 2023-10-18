package org.programmers.springorder;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@Disabled
@SpringBootTest
class VoucherApplicationTest {

    @Autowired
    private VoucherApplication voucherApplication;

    @Test
    @DisplayName("Voucher Application 구동 테스트")
    void startVoucherApplicationTest() {
        voucherApplication.run();
    }

//    //TODO 입출력 관련 테스트 추가

}