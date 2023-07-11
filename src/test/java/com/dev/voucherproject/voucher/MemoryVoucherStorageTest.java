package com.dev.voucherproject.voucher;


import com.dev.voucherproject.model.storage.voucher.MemoryVoucherStorage;
import com.dev.voucherproject.model.voucher.Voucher;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.dev.voucherproject.model.voucher.VoucherPolicy.FIXED_AMOUNT_VOUCHER;
import static com.dev.voucherproject.model.voucher.VoucherPolicy.PERCENT_DISCOUNT_VOUCHER;
import static org.assertj.core.api.Assertions.assertThat;


public class MemoryVoucherStorageTest {
    MemoryVoucherStorage voucherStorage = new MemoryVoucherStorage();

    @Test
    @DisplayName("바우처를 저장할 수 있다.")
    void insert() {
        //GIVEN
        Voucher voucher = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 5000);

        //WHEN
        voucherStorage.insert(voucher);

        //THEN
        Voucher findVoucher = voucherStorage.findById(voucher.getVoucherId()).get();
        assertThat(findVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void findAll() {
        //GIVEN
        Voucher voucherA = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 5000);
        Voucher voucherB = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 6000);
        voucherStorage.insert(voucherA);
        voucherStorage.insert(voucherB);

        //WHEN
        List<Voucher> vouchers = voucherStorage.findAll();

        //THEN
        assertThat(vouchers).hasSize(2);
    }

    @ParameterizedTest
    @MethodSource("voucherSource")
    @DisplayName("특정 ID의 바우처를 조회할 수 있다")
    void findById(Voucher voucher) {
        //GIVEN
        voucherStorage.insert(voucher);

        //WHEN
        Voucher findVoucherById = voucherStorage.findById(voucher.getVoucherId()).get();

        //THEN
        assertThat(findVoucherById.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    static Stream<Arguments> voucherSource() {
        return Stream.of(
                Arguments.arguments(Voucher.of(UUID.fromString("7b8b86ac-cf92-4504-893c-74c3ed2ed9ef"), FIXED_AMOUNT_VOUCHER, 500)),
                Arguments.arguments(Voucher.of(UUID.fromString("fa64c8aa-26be-4d0c-a039-4b6875dff2ab"), FIXED_AMOUNT_VOUCHER, 1000)),
                Arguments.arguments(Voucher.of(UUID.fromString("6be31698-ad78-4145-a5bf-48a26c5553a2"), PERCENT_DISCOUNT_VOUCHER, 25))
        );
    }
}
