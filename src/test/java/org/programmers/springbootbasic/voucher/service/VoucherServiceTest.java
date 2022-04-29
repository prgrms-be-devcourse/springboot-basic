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
    @DisplayName("getVoucher 메소드는")
    class GetVoucher_of {

        @Nested
        @DisplayName("바우처 아이디가 존재 할 때")
        class Context_with_valid_voucherId {
            Voucher voucher = voucherService.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 3000L, LocalDateTime.now());

            @Test
            @DisplayName("바우처를 반환합니다.")
            void it_returns_a_voucher() {
                Optional<Voucher> retrievedVoucher = voucherService.getVoucher(voucher.getVoucherId());

                assertThat(retrievedVoucher).isPresent();
            }
        }

        @Nested
        @DisplayName("바우처 아이디가 존재 하지 않을 때")
        class Context_with_unValid_voucherId {
            Optional<Voucher> emptyVoucher = voucherService.getVoucher(UUID.randomUUID());

            @Test
            @DisplayName("Optional.isEmpty를 반환합니다.")
            void it_returns_a_throw() {
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
            Voucher voucher = voucherService.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), 10, LocalDateTime.now());

            @Test
            @DisplayName("바우처 List를 반환합니다.")
            void it_returns_a_list() {
                final List<Voucher> voucherList = voucherService.getVoucherList();

                //then
                assertThat(voucherList.get(0).getVoucherId()).isEqualTo(voucher.getVoucherId());
            }
        }
    }
}
