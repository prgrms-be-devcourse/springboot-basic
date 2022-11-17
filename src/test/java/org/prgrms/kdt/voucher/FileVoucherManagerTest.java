package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class FileVoucherManagerTest {

    private final FileVoucherManager fileVoucherManager = new FileVoucherManager();

    @Nested
    @DisplayName("Fixed 타입의,")
    class fixed {
        @Test
        @DisplayName("바우처는 파일에 저장될 수 있다.")
        @ParameterizedTest
        @ValueSource(strings = {"10", "100", "10000", "0"})
        void saveTest(String value) {
            // given
            Voucher voucher = Voucher.newInstance(VoucherType.FIXED, new VoucherAmount(value));

            // when
            List<Voucher> beforeSavedVouchers = fileVoucherManager.findAll();
            fileVoucherManager.save(voucher);
            List<Voucher> afterSavedVouchers = fileVoucherManager.findAll();

            // then
            assertThat(afterSavedVouchers)
                    .containsAll(beforeSavedVouchers);


        }


    }

}