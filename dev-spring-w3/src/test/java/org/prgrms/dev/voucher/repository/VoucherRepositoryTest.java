package org.prgrms.dev.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.dev.config.DBConfig;
import org.prgrms.dev.voucher.domain.PercentDiscountVoucher;
import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.prgrms.dev.config.DBConfig.dbSetup;


@SpringJUnitConfig
@ContextConfiguration(classes = {DBConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = "dev")
class VoucherRepositoryTest {

    private static EmbeddedMysql embeddedMysql;

    @Autowired
    private VoucherRepository voucherRepository;

    @BeforeAll
    static void setup() {
        embeddedMysql = dbSetup();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @DisplayName("바우처를 생성할 수 있다.")
    @Test
    void createTest() {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 50L, LocalDateTime.now());
        voucherRepository.insert(voucher);

        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucher.getVoucherId());
        assertThat(retrievedVoucher).isNotEmpty();
    }

    @DisplayName("바우처의 할인정보를 수정할 수 있다.")
    @Test
    void updateDiscountValueTest() {
        UUID voucherId = UUID.fromString("6b20f733-628c-431e-b8ae-d76b81175554");
        Voucher voucher = new PercentDiscountVoucher(voucherId, 20L, LocalDateTime.now());

        voucherRepository.update(voucher);

        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucherId);
        assertThat(retrievedVoucher.get().getDiscountValue()).isEqualTo(20L);
    }

    @DisplayName("바우처를 삭제할 수 있다.")
    @Test
    void deleteVoucherTest() {
        UUID voucherId = UUID.fromString("c9cf01c2-fbd1-4dfa-86d3-46c745892e60");
        voucherRepository.deleteById(voucherId);

        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucherId);
        assertThat(retrievedVoucher).isEmpty();
    }

    @DisplayName("아이디로 원하는 바우처를 조회할 수 있다.")
    @Test
    void findByIdTest() {
        UUID voucherId = UUID.fromString("5129a45c-6ec7-4158-8be0-9f24c3c110f1");
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucherId);

        assertThat(retrievedVoucher).isNotEmpty();
        assertThat(retrievedVoucher.get().getVoucherType()).isEqualTo(VoucherType.FIXED);
    }

    @DisplayName("존재하지 않는 아이디로 조회시 Optional.Empty 반환")
    @Test
    void findByNoIdTest() {
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(UUID.randomUUID());

        assertThat(retrievedVoucher).isEmpty();
    }
}