package org.prgrms.kdt.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.dao.entity.voucher.Voucher;
import org.prgrms.kdt.dao.repository.voucher.MemoryVoucherRepository;
import org.prgrms.kdt.dao.repository.voucher.VoucherRepository;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class MemoryVoucherRepositoryTest {

    private static final String FIXED_TYPE = "FixedAmountVoucher";
    private static final String PERCENT_TYPE = "PercentDiscountVoucher";

    VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @BeforeEach
    void beforeEach() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("저장된 바우처를 voucherId를 이용해 찾는 기능 검증")
    void voucherId_이용한_바우처찾기() {
        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), "1000", FIXED_TYPE);

        // when
        voucherRepository.insert(voucher);
        Voucher findVoucher = voucherRepository.findById(voucher.getVoucherId()).orElseThrow(IllegalArgumentException::new);

        // then
        assertThat(findVoucher.getVoucherId(), is(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처가 제대로 들어갔는지 repository 내부 바우처의 갯수로 검증")
    void insert() {
        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), "1000", FIXED_TYPE);

        // when
        voucherRepository.insert(voucher);
        int getVoucherCount = voucherRepository.getAllStoredVoucher().size();

        // then
        assertThat(getVoucherCount, is(1));
    }

    @Test
    @DisplayName("저장된 바우처들이 제대로 나오는지 바우처 갯수를 이용한 검증")
    void getAllStoredVoucher() {
        // given
        voucherRepository.insert(new Voucher(UUID.randomUUID(), "1000", FIXED_TYPE));
        voucherRepository.insert(new Voucher(UUID.randomUUID(), "2000", FIXED_TYPE));
        voucherRepository.insert(new Voucher(UUID.randomUUID(), "3000", FIXED_TYPE));

        // when
        int getVoucherCount = voucherRepository.getAllStoredVoucher().size();

        // then
        assertThat(getVoucherCount, is(3));
    }

}