package org.prgrms.voucher.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.models.FixedAmountVoucher;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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

    @Nested
    @DisplayName("Repository findAll 메서드는")
    class DescribeFindAll {

        private final VoucherRepository voucherRepository = new VoucherMemoryRepository();
        private Field store;

        @Nested
        @DisplayName("호출이 되면")
        class ContextCallThis {

            @Test
            @DisplayName("저장소의 바우처 정보를 리스트로 반환한다.")
            void itReturnVoucherList() {

                try {
                    store = voucherRepository.getClass().getDeclaredField("store");
                    store.setAccessible(true);
                } catch (NoSuchFieldException testException) {
                    throw new RuntimeException(testException.getMessage());
                }

                try {
                    Map<Long, Voucher> map = (Map<Long, Voucher>) store.get(voucherRepository);

                    Voucher firstVoucher = new FixedAmountVoucher(1L, 100, VoucherType.FIXED_AMOUNT);
                    Voucher secondVoucher = new FixedAmountVoucher(2L, 100, VoucherType.FIXED_AMOUNT);

                    map.put(firstVoucher.getVoucherId(), firstVoucher);
                    map.put(secondVoucher.getVoucherId(), secondVoucher);

                    List<Voucher> list = voucherRepository.findAll();

                    Assertions.assertThat(list.size()).isEqualTo(2);
                    Assertions.assertThat(firstVoucher).isEqualTo(list.get(0));
                    Assertions.assertThat(secondVoucher).isEqualTo(list.get(1));
                } catch (IllegalAccessException testException) {
                    throw new RuntimeException(testException.getMessage());
                }
            }
        }
    }
}

