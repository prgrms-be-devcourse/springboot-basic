package org.prgrms.kdt.command;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.prgrms.kdt.voucher.*;
import org.prgrms.kdt.voucher.utils.VoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class VoucherExecutorTest {

    private VoucherExecutor voucherExecutor;
    private VoucherManager voucherManager;

    @Nested
    @DisplayName("메모리를 통해,")
    class memory {

        @BeforeEach
        void init() {
            voucherManager = new InMemoryVoucherManager();
            voucherExecutor = new VoucherExecutor(voucherManager, new VoucherMapper());
        }

        @DisplayName("바우처를 저장할 수 있다.")
        @ParameterizedTest
        @CsvSource(value = {"FIXED, 0", "FIXED, 10", "FIXED, 1000", "FIXED, 10000", "PERCENT, 0", "PERCENT, 10", "PERCENT, 100", "PERCENT, 20"})
        void saveTest(String type, String amount) {
            // given


            // when
            Voucher createdVoucher = voucherExecutor.create(type, amount);
            Optional<Voucher> actual = voucherManager.findById(createdVoucher.getId());

            // then
            assertThat(actual.isPresent())
                    .isTrue();
        }

        @DisplayName("저장된 바우처를 조회할 수 있다.")
        @Test
        void listTest() {
            // given
            Voucher voucher1 = Voucher.newInstance(VoucherType.of("Fixed"), new VoucherAmount("10"));
            Voucher voucher2 = Voucher.newInstance(VoucherType.of("percent"), new VoucherAmount("20"));
            voucherManager.save(voucher1);
            voucherManager.save(voucher2);

            // when
            List<Voucher> actualVouchers = voucherExecutor.list();

            // then
            assertThat(actualVouchers)
                    .containsExactlyElementsOf(List.of(voucher1, voucher2));

        }
    }

    @Nested
    @DisplayName("파일을 통해,")
    class file {
        private static final Logger logger = LoggerFactory.getLogger(file.class);
        public static final String FILE_PATH = "src/test/resources/vouchers.csv";

        @BeforeEach
        void init() {
            try {
                new FileOutputStream(FILE_PATH).close();
            } catch (IOException exception) {
                logger.error(exception.getMessage());
            }
            voucherManager = new FileVoucherManager(FILE_PATH);
            voucherExecutor = new VoucherExecutor(voucherManager, new VoucherMapper());
        }

        @DisplayName("바우처를 저장할 수 있다.")
        @ParameterizedTest
        @CsvSource(value = {"FIXED, 0", "FIXED, 10", "FIXED, 1000", "FIXED, 10000", "PERCENT, 0", "PERCENT, 10", "PERCENT, 100", "PERCENT, 20"})
        void saveTest(String type, String amount) {
            // given

            // when
            Voucher createdVoucher = voucherExecutor.create(type, amount);
            Optional<Voucher> actual = voucherManager.findById(createdVoucher.getId());

            // then
            assertThat(actual.isPresent())
                    .isTrue();
        }

        @DisplayName("저장된 바우처를 조회할 수 있다.")
        @Test
        void listTest() {
            // given
            Voucher voucher1 = Voucher.from(1L, VoucherType.of("Fixed"), new VoucherAmount("10"));
            Voucher voucher2 = Voucher.from(2L, VoucherType.of("percent"), new VoucherAmount("20"));
            voucherManager.save(voucher1);
            voucherManager.save(voucher2);

            // when
            List<Voucher> actualVouchers = voucherExecutor.list();

            // then
            assertThat(actualVouchers)
                    .usingRecursiveComparison()
                    .isEqualTo(List.of(voucher1, voucher2));
        }
    }

}