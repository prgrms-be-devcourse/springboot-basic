package com.program.commandLine.service;

import com.program.commandLine.voucher.Voucher;
import com.program.commandLine.repository.VoucherRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VoucherServiceTest {

    static final String FIXED_AMOUNT_VOUCHER = "1";
    static final String PERCENT_DISCOUNT_VOUCHER = "2";

    class VoucherRepositoryStub implements VoucherRepository {

        Map<UUID, Voucher> stubMap = new HashMap<>();

        @Override
        public Optional<Voucher> findById(UUID voucherId) {
            return Optional.empty();
        }

        @Override
        public Voucher insertVoucher(Voucher voucher) {
            return voucher;
        }

        @Override
        public List<Voucher> findAll() {
            return stubMap.values().stream().toList();
        }
    }


    @Test
    @DisplayName("Fixed amount voucher가 생성된다.")
    void createFixedAmountVoucher() {
        // Given
        UUID voucherId = UUID.randomUUID();
        int discountAmount = 2000;
        var sut = new VoucherService(new VoucherRepositoryStub(), new VoucherFactory());
        // When
        Voucher fixedAmountVoucher = sut.createVoucher(FIXED_AMOUNT_VOUCHER, voucherId, discountAmount);
        // Then
        assertThat(fixedAmountVoucher.getVoucherId(), is(voucherId));
        assertThat(fixedAmountVoucher.getVoucherType(), is("Fixed_amount"));
        assertThat(fixedAmountVoucher.getVoucherDiscount(), is(discountAmount));
    }

    @Test
    @Disabled
    @DisplayName("Percent discount voucher가 생성된다.")
    void createPercentDiscountVoucher() {
        // Given
        UUID voucherId = UUID.randomUUID();
        int discountPercent = 30;
        var sut = new VoucherService(new VoucherRepositoryStub(), new VoucherFactory());
        // When
        Voucher PercentDiscountVoucher = sut.createVoucher(PERCENT_DISCOUNT_VOUCHER, voucherId, discountPercent);
        // Then
        assertThat(PercentDiscountVoucher.getVoucherId(), is(voucherId));
        assertThat(PercentDiscountVoucher.getVoucherType(), is("Percent_discount"));
        assertThat(PercentDiscountVoucher.getVoucherDiscount(), is(discountPercent));
    }

    @Test
    @DisplayName("voucher map를 꺼내올 수 있다.(mock)")
    void getAllVoucher() {
        // Given

        // When

        // Then
    }
}