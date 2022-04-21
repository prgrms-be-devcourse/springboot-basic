package org.prgms.voucher.repository;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.EmbeddedTestDbInitializer;
import org.prgms.TestDbConfig;
import org.prgms.voucher.FixedAmountVoucher;
import org.prgms.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig(value = {TestDbConfig.class}, initializers = EmbeddedTestDbInitializer.class)
class VoucherRepositoryTest {
    @Autowired
    private VoucherRepository voucherRepository;

    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);

    @BeforeEach
    void deleteAll() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 저장 테스트")
    void saveTest() {
        voucherRepository.save(voucher);
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers, Matchers.contains(Matchers.samePropertyValuesAs(voucher)));
    }

    @Test
    @DisplayName("바우처 조회 테스트")
    void findAllTest() {
        for (int i = 0; i < 3; i++) {
            voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        }
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers, Matchers.hasSize(3));
    }

    @Test
    @DisplayName("바우처 id로 조회 테스트")
    void findByIdTest() {
        voucherRepository.save(voucher);
        var unknown = voucherRepository.findById(UUID.randomUUID());
        assertThat(unknown.isEmpty(), Matchers.is(true));

        var foundVoucher = voucherRepository.findById(voucher.getVoucherId());
        assertThat(foundVoucher.get(), Matchers.samePropertyValuesAs(voucher));
    }
}