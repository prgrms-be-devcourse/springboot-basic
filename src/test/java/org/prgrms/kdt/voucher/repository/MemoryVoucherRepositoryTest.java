package org.prgrms.kdt.voucher.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringJUnitConfig // ()
@ActiveProfiles("test")
class MemoryVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @DisplayName("id로 조회할 수 있어야 한다.")
    @Test
    void testFindById() {
        // given
        UUID givenUUID1 = UUID.randomUUID();
        UUID givenUUID2 = UUID.randomUUID();
        Voucher voucher1 = new PercentDiscountVoucher(givenUUID1, 20);
        Voucher voucher2 = new FixedAmountVoucher(givenUUID2, 1000);
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);

        // when
        Optional<Voucher> resultVoucher1 = voucherRepository.findById(givenUUID1);
        Optional<Voucher> resultVoucher2 = voucherRepository.findById(givenUUID2);

        // then
        assertThat(resultVoucher1.get()).isEqualTo(voucher1);
        assertThat(resultVoucher2.get()).isEqualTo(voucher2);
    }

    @DisplayName("유효하지 않은 id로 조회하면 Optional Empty가 나온다.")
    @Test
    void testFindByIdNotFound() {
        // given
        UUID givenUUID = UUID.randomUUID();
        UUID correctUUID = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(correctUUID, 20);
        voucherRepository.insert(voucher);

        // when
        Optional<Voucher> resultVoucher1 = voucherRepository.findById(givenUUID);

        // then
        assertThat(resultVoucher1).isEmpty();
    }

    @Test
    @DisplayName("Voucher를 insert 할 수 있어야 한다.")
    void testInsert() {
        // given
        UUID givenUUID = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(givenUUID, 20);

        // when
        Voucher returnVoucher = voucherRepository.insert(voucher);

        // then
        assertThat(returnVoucher).isEqualTo(voucher);
    }

    @Test
    @DisplayName("똑같은 Voucher를 insert하면 안된다.")
    void testInsertSamevoucher() {
        // given
        UUID givenUUID = UUID.randomUUID();
        Voucher voucher1 = new PercentDiscountVoucher(givenUUID, 20);
        Voucher voucher2 = new PercentDiscountVoucher(givenUUID, 40);

        // Voucher returnVoucher2 = voucherRepository.insert(voucher2);

        Voucher returnVoucher1 = voucherRepository.insert(voucher1);

        assertThatThrownBy(() -> {
            // when
            Voucher returnVoucher2 = voucherRepository.insert(voucher2);
            // then
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("현재 메모리에 저장되어있는 Voucher 리스트를 받아온다")
    void testList() {
        // given
        UUID givenUUID1 = UUID.randomUUID();
        UUID givenUUID2 = UUID.randomUUID();
        Voucher voucher1 = new PercentDiscountVoucher(givenUUID1, 20);
        Voucher voucher2 = new FixedAmountVoucher(givenUUID2, 1000);
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);

        // when
        var resultVoucherList = voucherRepository.find();

        // then
        assertThat(resultVoucherList).contains(voucher1, voucher2);
    }

    @Configuration
    @ComponentScan(basePackages = {
            "org.prgrms.kdt.voucher"
    })
    static class Config {
    }
}