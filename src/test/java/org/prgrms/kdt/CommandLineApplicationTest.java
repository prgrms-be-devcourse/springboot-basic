package org.prgrms.kdt;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.config.MissionConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 1:41 오전
 */
class CommandLineApplicationTest {

    @Test
    @DisplayName("인메모리 바우처 레포지토리 빈 주입 확인 테스트")
    void is_existed_in_memory_voucher_repository_bean() {
        String inMemoryVoucherRepository = "voucherRepository";
        var context = new AnnotationConfigApplicationContext(MissionConfiguration.class);

        assertTrue(context.containsBean(inMemoryVoucherRepository));
    }

    @Test
    @DisplayName("바우처 서비스 빈 주입 확인 테스트")
    void is_existed_voucher_service_bean() {
        String voucherServiceBeanName = "voucherService";
        var context = new AnnotationConfigApplicationContext(MissionConfiguration.class);

        assertTrue(context.containsBean(voucherServiceBeanName));
    }
}