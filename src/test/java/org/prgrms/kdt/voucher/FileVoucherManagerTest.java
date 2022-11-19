package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileVoucherManagerTest {

    public static final String FILE_PATH = "src/test/resources/vouchers.csv";
    private VoucherManager voucherManager;

    @BeforeEach
    void init() {
        voucherManager = new FileVoucherManager(FILE_PATH);
        voucherManager.deleteAll();
    }

    @DisplayName("0보다 크거나 같은 값으로 저장될 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {
            "fixed, 0",
            "fixed, 10",
            "fixed, 1000",
            "fixed, 10000",
            "percent, 0",
            "percent, 10",
            "percent, 100",
            "percent, 20"
    })
    void save(String type, String amount) {
        // given
        Voucher voucher = Voucher.newInstance(VoucherType.of(type), new VoucherAmount(amount));

        // when
        Voucher savedVoucher = voucherManager.save(voucher);
        Optional<Voucher> actual = voucherManager.findById(savedVoucher.getId());

        // then
        assertThat(actual.isPresent())
                .isTrue();
    }

    @DisplayName("유효하지 않은 값은 저장될 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {
            "fixed, 30.2",
            "fixed, -1",
            "percent, 10.21",
            "percent, -10",
            "percent, 105"
    })
    void saveNumberFormatException(String type, String amount) {
        assertThrows(NumberFormatException.class, () -> Voucher.newInstance(VoucherType.of(type), new VoucherAmount(amount)));
    }

    @Test
    @DisplayName("파일에 저장된 바우처를 조회할 수 있다.")
    void findAll() {
        // given
        Voucher voucher1 = Voucher.from(1L, VoucherType.of("fixed"), new VoucherAmount("10"));
        Voucher voucher2 = Voucher.from(2L, VoucherType.of("percent"), new VoucherAmount("20"));
        voucherManager.save(voucher1);
        voucherManager.save(voucher2);

        // when
        List<Voucher> actualVouchers = voucherManager.findAll();

        // then
        assertThat(actualVouchers)
                .usingRecursiveComparison()
                .isEqualTo(List.of(voucher1, voucher2));
    }

    @Test
    void findByIdTest() {
        // given
        Voucher voucher = Voucher.newInstance(VoucherType.of("fixed"), new VoucherAmount("10"));
        Voucher savedVoucher = voucherManager.save(voucher);

        // when

        // then
        assertThat(voucherManager.findById(savedVoucher.getId()).isPresent())
                .isTrue();
    }
}