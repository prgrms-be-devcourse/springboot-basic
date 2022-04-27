package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.PercentDiscountVoucher;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.repository.VoucherFileRepository;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VoucherFileRepositoryTests {

    private VoucherRepository voucherRepository = new VoucherFileRepository("./repository/voucher");

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("인자로 전달받은 Voucher 가 null 이면")
        class Context_with_null_argument {

            @Test
            @Order(1)
            @DisplayName("IllegalArgumentException 예외를 발생시킨다")
            void it_throw_error() {
                assertThrows(IllegalArgumentException.class, () -> voucherRepository.save(null));
            }
        }

        @Nested
        @DisplayName("Voucher 가 정상적으로 저장되면")
        class Context_with_saved_success {

            @Test
            @Order(2)
            @DisplayName("저장한 Voucher 를 return 한다")
            void it_return_saved_voucher() {
                Voucher voucher1 = new FixedAmountVoucher(1000);
                Voucher voucher2 = new PercentDiscountVoucher(50);

                Voucher savedVoucher1 = voucherRepository.save(voucher1);
                Voucher savedVoucher2 = voucherRepository.save(voucher2);

                assertThat(savedVoucher1.getType(), is(voucher1.getType()));
                assertThat(savedVoucher1.getValue(), is(voucher1.getValue()));
                assertThat(savedVoucher2.getType(), is(voucher2.getType()));
                assertThat(savedVoucher2.getValue(), is(voucher2.getValue()));
            }
        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {

        @Nested
        @DisplayName("호출되면")
        class Context_with_call {

            @Test
            @Order(3)
            @DisplayName("저장된 모든 Voucher 에 대한 List 를 리턴한다")
            void it_return_saved_voucherList() {
                Voucher voucher1 = new FixedAmountVoucher(1000);
                Voucher voucher2 = new PercentDiscountVoucher(50);
                Voucher voucher3 = new PercentDiscountVoucher(30);

                voucherRepository.save(voucher1);
                voucherRepository.save(voucher2);
                voucherRepository.save(voucher3);

                final var list = voucherRepository.findAll();
                assertThat(list.size(), is(3));
            }
        }
    }
}
