package com.programmers.voucher;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("콘솔 애플리케이션 무한 루프로 인해 스프링 부트 테스트 일시적으로 비활성화")
class VoucherApplicationTests {

    @Test
    void contextLoads() {
    }

}
