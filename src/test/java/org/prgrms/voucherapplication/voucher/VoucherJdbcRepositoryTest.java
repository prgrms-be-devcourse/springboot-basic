package org.prgrms.voucherapplication.voucher;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucherapplication.domain.voucher.entity.Voucher;
import org.prgrms.voucherapplication.domain.voucher.entity.VoucherType;
import org.prgrms.voucherapplication.domain.voucher.repository.VoucherJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoucherJdbcRepositoryTest {

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @Test
    @DisplayName("특정 타입별 조회 성공")
    void findByType() {
        int beforeSize = voucherJdbcRepository.findByType(VoucherType.FIXED).size();
        voucherJdbcRepository.save(VoucherType.FIXED.createVoucher(UUID.randomUUID(), 30, LocalDateTime.now()));

        List<Voucher> byType = voucherJdbcRepository.findByType(VoucherType.FIXED);
        assertThat(byType).isNotEmpty();
        MatcherAssert.assertThat(byType, Matchers.hasSize(beforeSize + 1));
    }
}