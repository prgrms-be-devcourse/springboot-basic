package org.programmers.springbootbasic.voucher;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.programmers.springbootbasic.config.DBConfig;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig
@ContextConfiguration(classes = {DBConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("VoucherService 클래스")
class VoucherServiceTest {

    private static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        embeddedMysql = DBConfig.dbSetup();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherRepository voucherRepository;

    @Nested
    @DisplayName("VoucherType은")
    class VoucherType_Of {

        @Nested
        @DisplayName("바우처 넘버가 유효하지 않은 값 일 때")
        class Context_with_unValid_voucherNumber {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> VoucherType.findByNumber(3))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("잘못된 바우처 넘버입니다.");
            }
        }

        @Nested
        @DisplayName("바우처 넘버가 유효한 값 일 때")
        class Context_with_valid_voucherNumber {
            VoucherType voucherType = VoucherType.findByNumber(2);

            @Test
            @DisplayName("바우처 타입을 반환합니다.")
            void it_has_a_voucherType() {
                assertThat(voucherType).isEqualTo(VoucherType.PERCENT);
            }

            @Test
            @DisplayName("바우처를 생성 할 수 있습니다.")
            void it_returns_a_voucher() {
                Voucher voucher = voucherService.createVoucher(voucherType, UUID.randomUUID(), 50, LocalDateTime.now());

                assertThat(voucher.getVoucherType()).isEqualTo(voucherType);
            }
        }

        @Nested
        @DisplayName("바우처 타입이 유효하지 않은 값 일 때")
        class Context_with_unValid_voucherType {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> VoucherType.findByType("unknown"))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("잘못된 바우처 타입입니다.");
            }
        }

        @Nested
        @DisplayName("바우처 타입이 유효한 값 일 때")
        class Context_with_valid_voucherType {
            VoucherType voucherType = VoucherType.findByType("FIXED");

            @Test
            @DisplayName("바우처 타입을 반환합니다.")
            void it_has_a_voucherType() {
                assertThat(voucherType).isEqualTo(VoucherType.FIXED);
            }

            @Test
            @DisplayName("바우처를 생성 할 수 있습니다.")
            void it_returns_a_voucher() {
                Voucher voucher = voucherService.createVoucher(voucherType, UUID.randomUUID(), 50, LocalDateTime.now());

                assertThat(voucher.getVoucherType()).isEqualTo(voucherType);
            }
        }
    }

    @Nested
    @DisplayName("getVoucher 메소드는")
    class GetVoucher_of {

        @Nested
        @DisplayName("바우처 아이디가 존재 할 때")
        class Context_with_valid_voucherId {

            @Test
            @DisplayName("바우처를 반환합니다.")
            void it_returns_a_voucher() {
                Voucher voucher = voucherService.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 3000L, LocalDateTime.now());

                Optional<Voucher> retrievedVoucher = voucherService.getVoucher(voucher.getVoucherId());

                assertThat(retrievedVoucher).isPresent();
            }
        }

        @Nested
        @DisplayName("바우처 아이디가 존재 하지 않을 때")
        class Context_with_unValid_voucherId {

            @Test
            @DisplayName("Optional.isEmpty를 반환합니다.")
            void it_returns_a_throw() {
                Optional<Voucher> emptyVoucher = voucherService.getVoucher(UUID.randomUUID());

                assertThat(emptyVoucher).isEmpty();
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
                //given
                voucherService.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), 10, LocalDateTime.now());

                //when
                final List<Voucher> voucherList = voucherService.getVoucherList();

                //then
                assertThat(voucherList).hasSize(1);
            }
        }
    }
}
