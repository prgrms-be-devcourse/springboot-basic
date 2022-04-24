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
import org.prgrms.voucher.models.FixedAmountVoucher;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.prgrms.voucher.repository.VoucherRepository;

import java.util.List;

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
        @DisplayName("create 기능을 테스트 할 때 바우처 타입 Percent, 할인값 101 을 인자로 받으면")
        class ContextReceivePercentVoucherTypeAndWrongValue {

            VoucherDto.VoucherRequest request = new VoucherDto.VoucherRequest(101, VoucherType.PERCENT_DISCOUNT);

            @Test
            @DisplayName("잘못된 할인값 메시지와 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

                Assertions.assertThatThrownBy(() -> voucherService.create(request))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("bad discountValue");
            }
        }

        @Nested
        @DisplayName("create 기능을 테스트 할 때 바우처 타입 Percent, 할인값 -1 을 인자로 받으면")
        class ContextReceivePercentVoucherTypeAndNegativeValue {

            VoucherDto.VoucherRequest request = new VoucherDto.VoucherRequest(-1, VoucherType.PERCENT_DISCOUNT);

            @Test
            @DisplayName("잘못된 할인값 메시지와 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

                Assertions.assertThatThrownBy(() -> voucherService.create(request))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("bad discountValue");
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

        @Nested
        @DisplayName("create 기능을 테스트 할 때 바우처 타입 Percent, 할인값 50 을 인자로 받으면")
        class ContextReceivePercentVoucherTypeAndValue {

            VoucherDto.VoucherRequest requestDto = new VoucherDto.VoucherRequest(50, VoucherType.PERCENT_DISCOUNT);

            @Test
            @DisplayName("생성된 바우처를 반환한다.")
            void itCreateVoucherAndReturn() {

                Voucher voucher = VoucherType.PERCENT_DISCOUNT.createVoucher(50, VoucherType.PERCENT_DISCOUNT);

                when(voucherRepositoryMock.save(any(Voucher.class))).thenReturn(voucher);

                Voucher checkVoucher = voucherService.create(requestDto);

                Assertions.assertThat(voucher.getVoucherType()).isEqualTo(checkVoucher.getVoucherType());
                Assertions.assertThat(voucher.getDiscountValue()).isEqualTo(checkVoucher.getDiscountValue());
            }
        }
    }

    @Nested
    @DisplayName("Service list 메서드는")
    class DescribeList {

        @Mock
        VoucherRepository voucherRepositoryMock;

        @InjectMocks
        VoucherService voucherService;

        @Nested
        @DisplayName("호출이 되면")
        class ContextCallThis {

            @Test
            @DisplayName("Repository의 findAll 메서드를 호출한다.")
            void itCallRepositoryFindAll() {

                voucherService.list();

                verify(voucherRepositoryMock).findAll();
            }

            @Test
            @DisplayName("바우처를 담은 리스트를 반환한다.")
            void itReturnVoucherList() {

                Voucher voucher1 = new FixedAmountVoucher(1L, 100, VoucherType.FIXED_AMOUNT);
                Voucher voucher2 = new FixedAmountVoucher(2L, 101, VoucherType.FIXED_AMOUNT);

                List<Voucher> list = List.of(voucher1, voucher2);

                when(voucherRepositoryMock.findAll()).thenReturn(list);

                List<Voucher> listCheck = voucherService.list();

                Assertions.assertThat(list).isEqualTo(listCheck);
            }
        }
    }
}
