package org.programmers.springbootbasic.voucher.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.*;
import org.programmers.springbootbasic.exception.NotUpdateException;
import org.programmers.springbootbasic.voucher.VoucherConverter;
import org.programmers.springbootbasic.voucher.controller.api.CreateVoucherRequest;
import org.programmers.springbootbasic.voucher.controller.api.UpdateVoucherRequest;
import org.programmers.springbootbasic.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.voucher.model.PercentDiscountVoucher;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.repository.JdbcVoucherRepository;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@DisplayName("VoucherService 클래스")
class DefaultVoucherServiceTest {
    VoucherRepository voucherRepositoryMock = mock(JdbcVoucherRepository.class);

    VoucherConverter voucherConverterMock = mock(VoucherConverter.class);
    DefaultVoucherService defaultVoucherService = new DefaultVoucherService(voucherRepositoryMock, voucherConverterMock);

    @Nested
    @DisplayName("getVoucher 메소드는")
    class GetVoucher_of {

        @Nested
        @DisplayName("바우처 아이디가 존재 할 때")
        class Context_with_valid_voucherId {

            @Test
            @DisplayName("바우처를 반환합니다.")
            void it_returns_a_voucher() throws NotFoundException {
                Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());
                given(voucherRepositoryMock.findById(fixedAmountVoucher.getVoucherId())).willReturn(Optional.of(fixedAmountVoucher));

                defaultVoucherService.getVoucher(fixedAmountVoucher.getVoucherId());

                then(voucherRepositoryMock).should(times(1)).findById(fixedAmountVoucher.getVoucherId());
            }
        }

        @Nested
        @DisplayName("바우처 아이디가 존재 하지 않을 때")
        class Context_with_unValid_voucherId {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> defaultVoucherService.getVoucher(UUID.randomUUID()))
                        .isInstanceOf(NotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createVoucher 메소드는")
    class CreateVoucher_of {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 정상적인_바우처_입력값이_주어질_때 {

            @Test
            @DisplayName("바우처를 반환합니다.")
            void it_returns_a_Voucher() {
                Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());
                var createVoucherRequest = new CreateVoucherRequest(100L, "FIXED");
                given(voucherRepositoryMock.insert(fixedAmountVoucher)).willReturn(fixedAmountVoucher);
                given(voucherConverterMock.convertVoucher(createVoucherRequest)).willReturn(fixedAmountVoucher);

                defaultVoucherService.createVoucher(createVoucherRequest);

                then(voucherRepositoryMock).should(times(1)).insert(fixedAmountVoucher);
            }
        }

        @Nested
        @DisplayName("비정상적인 입력값이 주어질 때")
        class ContextWithFail {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                var createVoucherRequest = new CreateVoucherRequest(100000L, "FIXED");
                given(voucherConverterMock.convertVoucher(createVoucherRequest)).willThrow(IllegalArgumentException.class);

                assertThatThrownBy(() -> defaultVoucherService.createVoucher(createVoucherRequest))
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

                defaultVoucherService.getVoucherList();

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
                UpdateVoucherRequest updateVoucherRequest = new UpdateVoucherRequest(UUID.randomUUID(), 1000L);

                assertThatThrownBy(() -> defaultVoucherService.updateVoucher(updateVoucherRequest))
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

                defaultVoucherService.deleteVoucher(fixedAmountVoucher.getVoucherId());

                then(voucherRepositoryMock).should(times(1)).deleteById(fixedAmountVoucher.getVoucherId());
            }
        }
    }
}

