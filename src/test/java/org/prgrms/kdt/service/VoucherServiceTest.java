package org.prgrms.kdt.service;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherRepository;
import org.prgrms.kdt.domain.voucher.VoucherType;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VoucherServiceTest {

    @Test
    @DisplayName("바우처 생성 (stub)")
    void createVoucherByStub() {
        var voucherRepository = new VoucherRepositoryStub();
        var fixedAmountVoucher = new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.FIXED_AMOUNT, 100L);
        voucherRepository.insert(fixedAmountVoucher);

        assertThat(fixedAmountVoucher.getAmount(), is(100L));
        assertThat(fixedAmountVoucher.discount(1000L), is(900L));
    }

    class VoucherRepositoryStub implements VoucherRepository {
        @Override
        public Optional<Voucher> findById(Long voucherId) {
            return Optional.empty();
        }

        @Override
        public List<Voucher> findByType(VoucherType type) {
            return null;
        }

        @Override
        public List<Voucher> findAll() {
            return null;
        }

        @Override
        public Voucher insert(Voucher voucher) {
            return null;
        }

        @Override
        public Voucher update(Voucher voucher, Customer customer) {
            return null;
        }

        @Override
        public void deleteAll() {

        }

        @Override
        public int count() {
            return 0;
        }
    }
}
