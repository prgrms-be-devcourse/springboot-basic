package org.prgrms.voucher.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.prgrms.voucher.repository.VoucherRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

    @Nested
    @DisplayName("Service create 메서드는")
    class DescribeCreate {

        @Mock
        VoucherRepository voucherRepositoryMock;

        @InjectMocks
        VoucherService voucherService;

        @Nested
        @DisplayName("create 기능을 테스트 할 때 바우처 타입 Fixed, 할인값 -100을 인자로 받으면")
        class ContextReceiveFixedVoucherTypeAndWrongValue {

            VoucherDto.VoucherRequest requestDto = new VoucherDto.VoucherRequest(-100, VoucherType.FIXED_AMOUNT);

            @Test
            @DisplayName("잘못된 할인값 메시지와 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

                Assertions.assertThatThrownBy(() -> voucherService.create(requestDto))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("bad discountValue..");
            }
        }

        @Nested
        @DisplayName("create 기능을 테스트 할 때 바우처 타입이 null이면")
        class ContextReceiveNullVoucherType {

            VoucherDto.VoucherRequest requestDto = new VoucherDto.VoucherRequest(100, null);

            @Test
            @DisplayName("잘못된 타입 메시지와 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

                Assertions.assertThatThrownBy(() -> voucherService.create(requestDto))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("VoucherType is null");
            }
        }

        @Nested
        @DisplayName("create 기능을 테스트할 때 바우처 타입 Fixed, 할인값 100을 인자로 받으면")
        class ContextReceiveFixedVoucherTypeAndValue {

            VoucherDto.VoucherRequest requestDto = new VoucherDto.VoucherRequest(100, VoucherType.FIXED_AMOUNT);

            @Test
            @DisplayName("저장하기 위해 바우처 repository의 save 메서드를 호출한다.")
            void itCallRepositorySave() {

                Voucher voucher = voucherService.create(requestDto);

                verify(voucherRepositoryMock).save(any(Voucher.class));
            }

            @Test
            @DisplayName("생성된 바우처를 반환한다.")
            void itCreateVoucherAndReturn() {

                Voucher voucher = VoucherType.FIXED_AMOUNT.createVoucher(100, VoucherType.FIXED_AMOUNT);

                when(voucherRepositoryMock.save(any(Voucher.class))).thenReturn(voucher);

                Voucher checkVoucher = voucherService.create(requestDto);

                Assertions.assertThat(voucher.getVoucherType()).isEqualTo(checkVoucher.getVoucherType());
                Assertions.assertThat(voucher.getDiscountValue()).isEqualTo(checkVoucher.getDiscountValue());
            }
        }
    }
}
