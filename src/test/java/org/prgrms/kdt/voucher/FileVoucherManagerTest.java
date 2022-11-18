package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileVoucherManagerTest {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherManagerTest.class);
    public static final String FILE_PATH = "src/test/resources/vouchers.csv";
    private VoucherManager voucherManager;

    @BeforeEach
    void init() {
        try {
            new FileOutputStream(FILE_PATH).close();
        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }
        voucherManager = new FileVoucherManager(FILE_PATH);
    }

    @DisplayName("0보다 크거나 같은 값으로 저장될 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"fixed, 10", "fixed, 1000", "fixed, 10000", "fixed, 0", "percent, 10", "percent, 20", "percent, 100", "percent, 0"})
    void save(String type, String amount) {
        // given
        Voucher voucher = Voucher.newInstance(VoucherType.of(type), new VoucherAmount(amount));

        // when
        voucherManager.save(voucher);
        Optional<Voucher> actual = voucherManager.findById(voucher.getId());

        // then
        assertThat(actual.isPresent())
                .isTrue();
    }

    @DisplayName("유효하지 않은 값은 저장될 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"fixed, 30.2", "fixed, -1", "percent, 10.21", "percent, -10", "percent, 105"})
    void saveNumberFormatException(String type, String amount) {
        assertThrows(NumberFormatException.class, () -> Voucher.newInstance(VoucherType.of(type), new VoucherAmount(amount)));
    }

    @Test
    @DisplayName("파일에 저장된 바우처를 조회할 수 있다.")
    void findAll() {
        // given
        Voucher voucher1 = Voucher.newInstance(VoucherType.of("fixed"), new VoucherAmount("10"));
        Voucher voucher2 = Voucher.newInstance(VoucherType.of("percent"), new VoucherAmount("20"));
        voucherManager.save(voucher1);
        voucherManager.save(voucher2);

        // when
        List<Voucher> actualVouchers = voucherManager.findAll();

        // then
        assertThat(actualVouchers)
                .extracting(Voucher::getType, Voucher::getAmount)
                .containsExactlyInAnyOrder(
                        tuple(VoucherType.of("fixed"), new VoucherAmount("10")),
                        tuple(VoucherType.of("percent"), new VoucherAmount("20"))
                );
    }
}