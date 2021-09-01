package org.prgrms.kdt.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VoucherServiceTest {

    @Test
    @DisplayName("바우처 생성 (stub)")
    void createVoucherByStub() {
        var voucherRepository = new VoucherRepositoryStub();
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        voucherRepository.insert(fixedAmountVoucher);

        assertThat(fixedAmountVoucher.getAmount(), is(100L));
        assertThat(fixedAmountVoucher.discount(1000L), is(900L));
    }

    class VoucherRepositoryStub implements VoucherRepository {
        @Override
        public Optional<Voucher> findById(UUID voucherId) {
            return Optional.empty();
        }

        @Override
        public List<Voucher> findAll() {
            return null;
        }

        @Override
        public Voucher insert(Voucher voucher) {
            return null;
        }
    }
}
