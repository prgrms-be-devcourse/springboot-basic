package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.order.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

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