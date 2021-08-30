package org.prgrms.kdt.kdtspringorder.voucher.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.common.exception.VoucherNotFoundException;
import org.prgrms.kdt.kdtspringorder.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdt.kdtspringorder.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Profile("dev")
@SpringJUnitConfig
@DisplayName("VoucherService 단위 테스트")
class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    private VoucherService voucherService;

    private Logger logger = LoggerFactory.getLogger(VoucherServiceTest.class);

    @BeforeEach
    public void setUp() {
        voucherService = new VoucherService(voucherRepository);
    }

    @Nested
    @DisplayName("getVouchers() 메서드는")
    class Describe_getVouchers {

        @Nested
        @DisplayName("만약 목록이 비어있으면")
        class Context_empty_list{

            @BeforeEach
            void setUp() {
                when(voucherRepository.findAll()).thenReturn(new ArrayList<Voucher>());
            }

            @Test
            @DisplayName("빈 바우처 목록을 반환합니다.")
            void It_return_empty_list() {
                final List<Voucher> vouchers = voucherService.getVouchers();
                assertThat(vouchers, notNullValue());
                assertThat(vouchers, hasSize(0));
            }

        }

        @Nested
        @DisplayName("만약 목록이 비어있지 않으면")
        class Context_not_empty{

            @BeforeEach
            void setUp() {
                when(voucherRepository.findAll()).thenReturn(List.of(
                    new FixedAmountVoucher(UUID.randomUUID(), 100),
                    new PercentDiscountVoucher(UUID.randomUUID(), 20)
                ));

            }

            @Test
            @DisplayName("비어있지 않은 바우처 목록을 반환합니다.")
            void It_return_not_empty_list() {

                final List<Voucher> vouchers = voucherService.getVouchers();
                assertThat(vouchers, not(hasSize(0)));

            }

        }

    }

    @Nested
    @DisplayName("getVoucher 메서드는")
    class Describe_getVoucher {

        @Nested
        @DisplayName("만약 바우처 목록에 없는 VoucherId로 바우처를 조회한다면")
        class Context_invalid_voucher_id{

            private UUID invalidVoucherId;

            @BeforeEach
            void setUp() {
                invalidVoucherId = UUID.randomUUID();
                when(voucherRepository.findById(invalidVoucherId)).thenReturn(Optional.ofNullable(null));
            }

            @Test
            @DisplayName("VoucherNotFound Exception 을 던집니다.")
            void it_return_throw_voucher_not_found_exception() {
                Assertions.assertThatThrownBy( () -> voucherService.getVoucher(invalidVoucherId))
                    .isInstanceOf(VoucherNotFoundException.class);
            }

        }

        @Nested
        @DisplayName("만약 바우처 목록에 있는 VoucherId로 바우처를 조회한다면")
        class Context_valid_voucher_id{

            private UUID validVoucherId;

            @BeforeEach
            void setUp() {
                validVoucherId = UUID.randomUUID();
                when(voucherRepository.findById(validVoucherId))
                    .thenReturn(Optional.ofNullable(
                        new FixedAmountVoucher(validVoucherId, 100)
                    ));
            }

            @Test
            @DisplayName("조회한 바우처를 반환합니다.")
            void It_return_found_voucher() {
                final Voucher foundVoucher = voucherService.getVoucher(validVoucherId);
                assertThat(foundVoucher.getVoucherId(), equalTo(validVoucherId));
            }

        }
    }

    // 아직 미완..
    @Nested
    @DisplayName("saveVoucher 메서드는")
    class Describe_saveVoucher {

        @Nested
        @DisplayName("VoucherType과 할인금액/퍼센트를 인자로 넘기면")
        class Context_call_method{

            private Voucher createdVoucher;
            private UUID newVoucherId;
            private VoucherType newVoucherType;
            private long newVoucherDiscount;

            @BeforeEach
            void setUp() {
                newVoucherId = UUID.randomUUID();
                newVoucherType = VoucherType.FIX;
                newVoucherDiscount = 100;

                createdVoucher = new FixedAmountVoucher(newVoucherId, newVoucherDiscount);
                when(voucherRepository.insert(createdVoucher)).thenReturn(createdVoucher.getVoucherId());
            }

            @Test
            @DisplayName("저장한 바우처의 ID를 반환합니다.")
            void it_return_saved_voucher_id() {
                final UUID savedVoucherID = voucherService.saveVoucher(newVoucherType, newVoucherDiscount);
//                verify(voucherRepository).findById(newVoucherId);
                assertThat(savedVoucherID, equalTo(newVoucherId));
            }

        }

    }

}
