package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.kdtspringorder.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;

@Profile("dev")
@SpringJUnitConfig
@DisplayName("VoucherRepository 단위 테스트")
class VoucherRepositoryTest {

    private MemoryVoucherRepository voucherRepository;

    @BeforeEach
    void setUp(){
        voucherRepository = new MemoryVoucherRepository();
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {

        @Nested
        @DisplayName("만약 목록이 비어있지 않으면")
        class Context_not_empty{

            private Voucher newVoucher;
            private UUID newVoucherId = UUID.randomUUID();
            private long newVoucherDiscount = 100;

            @BeforeEach
            void setUp() {
                newVoucher = new FixedAmountVoucher(newVoucherId, newVoucherDiscount);
                voucherRepository.insert(newVoucher);
            }

            @Test
            @DisplayName("비어있지 않은 바우처 목록을 반환합니다.")
            void It_return_not_empty_list() {

                final List<Voucher> vouchers = voucherRepository.findAll();
                assertThat(vouchers, not(hasSize(0)));

            }

        }

//        @Nested
//        @DisplayName("만약 목록이 비어있으면")
//        class Context_empty{
//
//            @Test
//            @DisplayName("빈 바우처 목록을 반환합니다.")
//            void It_return_empty_list() {
//                final List<Voucher> vouchers = voucherRepository.findAll();
//                assertThat(vouchers, notNullValue());
//                assertThat(vouchers, hasSize(0));
//            }
//
//        }

    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_getVoucher {

        @Nested
        @DisplayName("VoucherId로 바우처를 조회한다면")
        class Context_voucher_id{

            private UUID invalidVoucherId = UUID.randomUUID();

            @Test
            @DisplayName("Optional<Voucher> 객체를 반환합니다.")
            void it_return_voucher_optional() {
                final Optional<Voucher> foundVoucherOptional = voucherRepository.findById(invalidVoucherId);
                assertThat(foundVoucherOptional, not(nullValue()));
                assertThat(foundVoucherOptional, instanceOf(Optional.class));
            }

        }

    }

    @Nested
    @DisplayName("insert 메서드는")
    class Describe_insert {

        @Nested
        @DisplayName("등록할 Voucher를 인자로 넘겨서 바우처를 등록하면")
        class Context_voucher_id{

            private Voucher newVoucher;
            private UUID newVoucherId = UUID.randomUUID();
            private long newVoucherDiscount = 100;

            @BeforeEach
            void setUp() {
                newVoucher = new FixedAmountVoucher(newVoucherId, newVoucherDiscount);
            }

            @Test
            @DisplayName("등록한 VoucherID를 반환합니다.")
            void it_return_voucher_optional() {
                final UUID createdVoucherId = voucherRepository.insert(newVoucher);
                assertThat(createdVoucherId, equalTo(newVoucherId));
            }

        }

    }


}
