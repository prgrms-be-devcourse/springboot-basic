package com.dev.voucherproject.voucher;

import com.dev.voucherproject.model.storage.voucher.CsvFileVoucherStorage;
import com.dev.voucherproject.model.voucher.Voucher;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.dev.voucherproject.model.voucher.VoucherPolicy.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles({"csv", "default"})
class CsvFileVoucherStorageTest {

    @Autowired
    CsvFileVoucherStorage csvFileVoucherStorage;

    @BeforeEach
    void init() {
        csvFileVoucherStorage.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("voucherSource")
    @DisplayName("바우처 정보를 voucher.csv 파일에 저장할 수 있다.")
    void insert(Voucher voucher) {
        csvFileVoucherStorage.insert(voucher);
    }

    @Test
    @DisplayName("voucher.csv 파일을 읽을 수 있다.")
    void findAll() {
        //GIVEN
        Voucher voucher = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 5000);
        csvFileVoucherStorage.insert(voucher);

        //WHEN
        List<Voucher> vouchers = csvFileVoucherStorage.findAll();

        //THEN
        assertThat(vouchers).hasSize(1);
    }

    @ParameterizedTest
    @MethodSource("voucherSource")
    @DisplayName("특정 ID의 바우처를 조회할 수 있다")
    void findById(Voucher voucher) {
        //GIVEN
        csvFileVoucherStorage.insert(voucher);

        //WHEN
        Voucher findVoucher = csvFileVoucherStorage.findById(voucher.getVoucherId()).get();

        //THEN
        assertThat(findVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    static Stream<Arguments> voucherSource() {
        return Stream.of(
            Arguments.arguments(Voucher.of(UUID.fromString("7b8b86ac-cf92-4504-893c-74c3ed2ed9ef"), FIXED_AMOUNT_VOUCHER, 500)),
            Arguments.arguments(Voucher.of(UUID.fromString("fa64c8aa-26be-4d0c-a039-4b6875dff2ab"), FIXED_AMOUNT_VOUCHER, 1000)),
            Arguments.arguments(Voucher.of(UUID.fromString("6be31698-ad78-4145-a5bf-48a26c5553a2"), PERCENT_DISCOUNT_VOUCHER, 25))
        );
    }
}
