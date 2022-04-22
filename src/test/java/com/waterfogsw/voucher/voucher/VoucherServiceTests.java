package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import com.waterfogsw.voucher.voucher.service.VoucherManageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTests {

    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherManageService voucherService;

    @Nested
    @DisplayName("addVoucher 메소드는")
    class Describe_addVoucher {

        @Nested
        @DisplayName("type 이 FIXED_AMOUNT 인 Voucher 의 value 가 음수이면")
        class Context_with_negative_fixedAmount {

            @Test
            @DisplayName("IllegalArgumentException 예외를 발생시킨다.")
            void it_throw_IllegalArgumentException() {
                Voucher voucher = new Voucher(VoucherType.FIXED_AMOUNT, -1000);

                try {
                    voucherService.saveVoucher(voucher);
                    fail();
                } catch (IllegalArgumentException e) {
                    //pass
                }
            }
        }

        @Nested
        @DisplayName("type 이 PERCENT_DISCOUNT 인 Voucher 의 value 가 1 ~ 100 사이의 수가 아니면")
        class Context_with_out_of_range_percent_discount {

            @ParameterizedTest
            @ValueSource(ints = {-1, 101})
            @DisplayName("IllegalArgumentException 예외를 발생시킨다.")
            void it_throw_IllegalArgumentException(int percent) {
                Voucher voucher = new Voucher(VoucherType.PERCENT_DISCOUNT, percent);

                try {
                    voucherService.saveVoucher(voucher);
                    fail();
                } catch (IllegalArgumentException e) {
                    //pass
                }
            }
        }

        @Nested
        @DisplayName("Voucher 가 정상적으로 저장되어 생성되면")
        class Context_with_saved_success {

            @Test
            @DisplayName("저장한 Voucher 를 return 한다")
            void it_throw_IllegalArgumentException() {
                Voucher voucher = new Voucher(VoucherType.FIXED_AMOUNT, 1000);

                when(voucherRepository.save(any(Voucher.class))).thenReturn(voucher);

                Voucher saved = voucherService.saveVoucher(voucher);

                assertThat(voucher.getType(), is(saved.getType()));
                assertThat(voucher.getValue(), is(saved.getValue()));
            }
        }
    }
}
