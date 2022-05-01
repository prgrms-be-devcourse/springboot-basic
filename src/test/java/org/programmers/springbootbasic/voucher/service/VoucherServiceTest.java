package org.programmers.springbootbasic.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.exception.NotUpdateException;
import org.programmers.springbootbasic.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.voucher.model.PercentDiscountVoucher;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.repository.JdbcVoucherRepository;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
@DisplayName("VoucherService 클래스")
class VoucherServiceTest {
    VoucherRepository voucherRepositoryMock = mock(JdbcVoucherRepository.class);
    VoucherService voucherService = new VoucherService(voucherRepositoryMock);

    @Nested
    @DisplayName("getVoucher 메소드는")
    class GetVoucher_of {

        @Nested
        @DisplayName("바우처 아이디가 존재 할 때")
        class Context_with_valid_voucherId {

            @Test
            @DisplayName("바우처를 반환합니다.")
            void it_returns_a_voucher() {
                Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());
                given(voucherService.getVoucher(fixedAmountVoucher.getVoucherId())).willReturn(Optional.of(fixedAmountVoucher));

                var voucher = voucherService.getVoucher(fixedAmountVoucher.getVoucherId());

                then(voucherRepositoryMock).should(times(1)).findById(fixedAmountVoucher.getVoucherId());
                assertThat(voucher.orElseThrow()).isEqualTo(fixedAmountVoucher);
            }
        }

        @Nested
        @DisplayName("바우처 아이디가 존재 하지 않을 때")
        class Context_with_unValid_voucherId {

            @Test
            @DisplayName("Optional.isEmpty를 반환합니다.")
            void it_returns_a_throw() {
                Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());
                given(voucherService.getVoucher(fixedAmountVoucher.getVoucherId())).willReturn(Optional.empty());

                var voucher = voucherService.getVoucher(fixedAmountVoucher.getVoucherId());

                then(voucherRepositoryMock).should(times(1)).findById(fixedAmountVoucher.getVoucherId());
                assertThat(voucher).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("createVoucher 메소드는")
    class CreateVoucher_of {

        @Nested
        @DisplayName("정상적인 바우처 입력값이 주어질 때")
        class ContextWithSuccess {

            @Test
            @DisplayName("바우처를 반환합니다.")
            void it_returns_a_Voucher() {
                Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());
                given(voucherRepositoryMock.insert(fixedAmountVoucher)).willReturn(fixedAmountVoucher);

                voucherService.createVoucher(VoucherType.FIXED, fixedAmountVoucher.getVoucherId(), 100, fixedAmountVoucher.getCreatedAt());

                then(voucherRepositoryMock).should(times(1)).insert(fixedAmountVoucher);
            }
        }

        @Nested
        @DisplayName("바우처 생성에 실패할 때")
        class ContextWithFail {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> voucherService.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), 1000, LocalDateTime.now()))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    @DisplayName("getVoucherList 메소드는")
    class Get_voucherList_of {

        @Nested
        @DisplayName("바우처가 존재할 때")
        class Context_with_exist_voucher {

            @Test
            @DisplayName("바우처 List를 반환합니다.")
            void it_returns_a_list() {
                List<Voucher> voucherList = new ArrayList<>();
                voucherList.add(new FixedAmountVoucher(UUID.randomUUID(), 1000L, LocalDateTime.now()));
                voucherList.add(new PercentDiscountVoucher(UUID.randomUUID(), 10, LocalDateTime.now()));
                given(voucherRepositoryMock.findAll()).willReturn(voucherList);

                voucherService.getVoucherList();

                then(voucherRepositoryMock).should(times(1)).findAll();
            }
        }
    }

    @Nested
    @DisplayName("updateVoucher 메소드는")
    class Get_UpdateVoucher_of {

        @Nested
        @DisplayName("바우처가 존재하지 않을 때")
        class ContextWithFail {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> voucherService.updateVoucher(UUID.randomUUID(), 1000))
                        .isInstanceOf(NotUpdateException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteVoucher 메소드는")
    class Get_DeleteVoucher_of {

        @Nested
        @DisplayName("바우처가 존재할 때")
        class Context_with_exist_voucher {

            @Test
            @DisplayName("바우처를 삭제 합니다.")
            void it_returns_void() {
                Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());

                voucherService.deleteVoucher(fixedAmountVoucher.getVoucherId());

                then(voucherRepositoryMock).should(times(1)).deleteById(fixedAmountVoucher.getVoucherId());
            }
        }
    }
}

