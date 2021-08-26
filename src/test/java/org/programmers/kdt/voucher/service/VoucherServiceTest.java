package org.programmers.kdt.voucher.service;

import org.junit.jupiter.api.*;
import org.programmers.kdt.AppConfiguration;
import org.programmers.kdt.voucher.FixedAmountVoucher;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherServiceTest {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    VoucherService voucherService;

    private List<Voucher> testVouchers = new ArrayList<>();

    @BeforeAll
    void setUp() {
        context.register(AppConfiguration.class);
        ConfigurableEnvironment environment = context.getEnvironment();
        // environment.setActiveProfiles("test");
        environment.setActiveProfiles("dev");
        context.refresh();
        voucherService = context.getBean(VoucherService.class);
    }

    @AfterAll
    void tearDown() {
        for (Voucher voucher : testVouchers) {
            voucherService.removeVoucher(voucher);
        }
    }

    @Test
    @Order(1)
    @DisplayName("바우처 생성하기")
    void createVoucher() {
        for (int i = 1; i <= 100; i++) {
            Voucher voucher = voucherService.createVoucher(VoucherType.FIXED, UUID.randomUUID(), i * 1000);
            testVouchers.add(voucher);
            assertThat(voucher).isNotNull();
        }
    }

    @Test
    @Order(2)
    @DisplayName("모든 바우처 정보 가져오기")
    void getAllVouchers() {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        for (Voucher voucher : testVouchers) {
            assertThat(voucher).isIn(vouchers);

            Voucher notYourVoucher = new FixedAmountVoucher(UUID.randomUUID(), new Random().nextInt());
            assertThat(notYourVoucher).isNotIn(testVouchers);
        }
    }

    @Test
    @Order(3)
    @DisplayName("저장된 바우처 가져오기")
    void getVoucher() {
        // 성공
        for (Voucher voucher : testVouchers) {
            assertThat(voucherService.getVoucher(voucher.getVoucherId()).get()).isEqualTo(voucher);
        }

        // 실패
        assertThat(voucherService.getVoucher(UUID.randomUUID())).isNotPresent();
    }

    @Test
    @DisplayName("바우처 사용하기")
    @Disabled
    void useVoucher() {
        // pass
        assertThat(true).isTrue();
    }

    @Test
    @Order(4)
    @DisplayName("바우처 제거하기")
    void deleteVoucher() {
        // 성공
        for (Voucher voucher : testVouchers) {
            assertThat(voucherService.removeVoucher(voucher)).isPresent();
        }

        // 실패
        Voucher notYourVoucher = new FixedAmountVoucher(UUID.randomUUID(), new Random().nextInt());
        assertThat(voucherService.removeVoucher(notYourVoucher)).isNotPresent();
    }

}