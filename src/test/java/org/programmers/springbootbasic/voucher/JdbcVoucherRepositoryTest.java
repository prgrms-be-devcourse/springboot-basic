package org.programmers.springbootbasic.voucher;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.programmers.springbootbasic.config.DBConfig;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.repository.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.programmers.springbootbasic.config.DBConfig.dbSetup;

@SpringJUnitConfig
@ContextConfiguration(classes = {DBConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    private static EmbeddedMysql embeddedMysql;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    Voucher fixedAmountVoucher;

    @BeforeAll
    void setup() {
        embeddedMysql = dbSetup();

        VoucherType fixedAmountType = VoucherType.FIXED;
        fixedAmountVoucher = fixedAmountType.create(UUID.randomUUID(), 100L ,LocalDateTime.now());
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("고객을 추가 할 수 있다.")
    void testInsert() {
        jdbcVoucherRepository.insert(fixedAmountVoucher);

        var retrievedVoucher = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());

        assertThat(retrievedVoucher).isPresent().get().isEqualTo(fixedAmountVoucher);
    }

    @Test
    @Order(2)
    @DisplayName("전체 고객을 조회 할 수 있다.")
    void testFindAll() {
        var vouchers = jdbcVoucherRepository.findAll();

        assertThat(vouchers).isNotEmpty();
    }

    @Test
    @Order(3)
    @DisplayName("아이디로 고객을 조회 할 수 있다.")
    void testFindByName() {
        var vouchers = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(vouchers).isPresent();

        var unknown = jdbcVoucherRepository.findById(UUID.randomUUID());
        assertThat(unknown).isNotPresent();
    }

    @Test
    @Order(4)
    @DisplayName("생성일 순으로 고객을 조회 할 수 있다.")
    void testFindByCreatedAt() {
        var vouchers = jdbcVoucherRepository.findByCreatedAt();
        assertThat(vouchers).isNotEmpty();
    }

    @Test
    @Order(5)
    @DisplayName("바우처를 수정 할 수 있다.")
    void testUpdate() {
        fixedAmountVoucher.changeValue(300L);
        jdbcVoucherRepository.update(fixedAmountVoucher);

        var all = jdbcVoucherRepository.findAll();
        assertThat(all).hasSize(1).containsOnlyOnce(fixedAmountVoucher);

        var retrievedVoucher = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(retrievedVoucher).isPresent().get().isEqualTo(fixedAmountVoucher);
    }
}