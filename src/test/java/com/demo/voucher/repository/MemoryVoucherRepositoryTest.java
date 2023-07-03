package com.demo.voucher.repository;

import com.demo.voucher.domain.FixedAmountVoucher;
import com.demo.voucher.domain.PercentDiscountVoucher;
import com.demo.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MemoryVoucherRepositoryTest {
    private final VoucherRepository repository = new MemoryVoucherRepository();

    @Test
    @DisplayName("새로 생성한 바우처가 Voucher Repository로부터 findById를 통해 잘 탐색 되는지 확인하는 테스트")
    void findById() {
        // given
        FixedAmountVoucher voucher = new FixedAmountVoucher(2000);
        repository.insert(voucher);

        // when
        Voucher foundVoucher = repository.findAll().get(0);
        UUID uuid = foundVoucher.getVoucherId();

        // then
        assertThat(repository.findById(uuid).get(), equalTo(foundVoucher));
    }

    @Test
    @DisplayName("Voucher Repository에서 findAll를 통해 전체 바우처를 모두 잘 가져오는지 확인하는 테스트")
    void findAll() {
        // given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1000);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(20);

        repository.insert(fixedAmountVoucher);
        repository.insert(percentDiscountVoucher);

        // when
        List<Voucher> vouchers = repository.findAll();

        // then
        assertThat(vouchers, hasSize(2));
        assertThat(vouchers, containsInAnyOrder(fixedAmountVoucher, percentDiscountVoucher));
    }

    @Test
    @DisplayName("Voucher Repository에 바우처가 없는 경우")
    void findAll_when_no_voucher() {
        List<Voucher> vouchers = repository.findAll();

        assertThat(vouchers, empty());
    }

    @Test
    @DisplayName("Voucher Repository에 바우처가 정상적으로 추가되는지 확인하는 테스트")
    void insert() {
        FixedAmountVoucher voucher = new FixedAmountVoucher(2000);

        repository.insert(voucher);

        assertThat(repository.findAll(), hasSize(1));
        assertThat(repository.findAll(), contains(voucher));
    }
}