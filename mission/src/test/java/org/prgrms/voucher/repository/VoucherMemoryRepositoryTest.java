package org.prgrms.voucher.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.models.FixedAmountVoucher;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;

public class VoucherMemoryRepositoryTest {

    @Nested
    @DisplayName("Repository save 메서드는")
    class DescribeCreate {

        private final VoucherRepository voucherRepository = new VoucherMemoryRepository();

        @Nested
        @DisplayName("save 기능을 테스트 할 때 바우처 객체를 null로 받을 때")
        class ContextReceiveFixedVoucherTypeAndWrongValue {

            Voucher voucher = null;

            @Test
            @DisplayName("잘못된 인자 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

                Assertions.assertThatThrownBy(() -> voucherRepository.save(voucher))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("Voucher is null");
            }
        }

        @Nested
        @DisplayName("save 기능을 테스트 할 때 바우처 객체를 인자로 받으면")
        class ContextReceiveNullVoucherType {

            Voucher voucher = new FixedAmountVoucher(1L,100, VoucherType.FIXED_AMOUNT);

            @Test
            @DisplayName("해시맵 저장소에 저장하고 저장한 바우처를 반환한다.")
            void itVoucherSaveAndReturn() {

                Voucher voucherCheck = voucherRepository.save(voucher);

                Assertions.assertThat(voucherCheck).isEqualTo(voucher);
            }
        }
    }
}

