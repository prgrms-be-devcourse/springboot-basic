package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VoucherServiceTest {

    class VoucherRepositoryStub implements VoucherRepository {

        @Override
        public List<Voucher> findAll() {
            return null;
        }

        @Override
        public Optional<Voucher> findById(UUID voucherId) {
            return Optional.empty();
        }

        @Override
        public Voucher insert(Voucher voucher) {
            return null;
        }

        @Override
        public Voucher update(Voucher voucher) {
            return null;
        }

        @Override
        public void deleteAll() {

        }

        @Override
        public Map<UUID, Voucher> getStorage() {
            return null;
        }
    }

    @Test
    void insert() {
        // Given
        var voucherRepository = new VoucherRepositoryStub();
        var sut = new VoucherService(voucherRepository);

        // When
        var fixedAmountVoucher = sut.createFixedAmountVoucher(100);

        // Then
        assertThat(fixedAmountVoucher.getDiscount(), is(100L));
        assertThat(voucherRepository.findAll().isEmpty(), is(false));
    }

    @Test
    void createPercentDiscountVoucher() {
    }
}