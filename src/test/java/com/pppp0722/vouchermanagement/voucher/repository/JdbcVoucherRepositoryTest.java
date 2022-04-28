package com.pppp0722.vouchermanagement.voucher.repository;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("JdbcVoucherRepository 단위 테스트")
class JdbcVoucherRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
            .addSchema("test-voucher_mgmt", classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    JdbcVoucherRepository voucherRepository;

    private final Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100,
        LocalDateTime.now(), UUID.randomUUID());

    @Test
    @Order(1)
    @DisplayName("바우처를 생성할 수 있다.")
    public void testCreateVoucher() {
        Voucher voucher = voucherRepository.insert(newVoucher);

        Optional<Voucher> readVoucher = voucherRepository.findById(
            voucher.getVoucherId());

        if (readVoucher.isEmpty()) {
            assertThat(false, is(true));
        }

        assertThat(readVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("모든 바우처를 읽을 수 있다.")
    public void testReadVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("아이디로 바우처를 읽을 수 있다.")
    public void testReadVoucherById() {
        Optional<Voucher> voucher = voucherRepository.findById(newVoucher.getVoucherId());

        if (voucher.isEmpty()) {
            assertThat(false, is(true));
        }

        assertThat(voucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @Order(4)
    @DisplayName("멤버 아이디로 바우처를 읽을 수 있다.")
    public void testReadVouchersByMemberId() {
        List<Voucher> vouchers = voucherRepository.findByMemberId(newVoucher.getMemberId());
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("바우처를 업데이트할 수 있다.")
    public void testUpdateVoucher() {
        Voucher newVoucher2 = new PercentDiscountVoucher(newVoucher.getVoucherId(), 20,
            LocalDateTime.now(),
            newVoucher.getMemberId());
        Voucher updatedVoucher = voucherRepository.update(newVoucher2);

        assertThat(updatedVoucher, not(samePropertyValuesAs(newVoucher)));
    }

    @Test
    @Order(6)
    @DisplayName("바우처를 삭제할 수 있다")
    public void testDeleteVoucher() {
        voucherRepository.delete(newVoucher);
        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @Order(7)
    @DisplayName("아이디로 바우처를 읽어올 때 아이디가 존재하지 않으면 empty를 반환한다.")
    public void testReadVoucherException() {
        Optional<Voucher> voucher = voucherRepository.findById(newVoucher.getVoucherId());

        assertThat(voucher.isEmpty(), is(true));
    }

    @Test
    @Order(8)
    @DisplayName("업데이트할 때 아이디가 존재하지 않으면 예외를 발생한다.")
    public void testUpdateVoucherException() {
        Voucher updatedVoucher = new PercentDiscountVoucher(newVoucher.getVoucherId(), 20,
            LocalDateTime.now(), newVoucher.getMemberId());
        assertThrows(RuntimeException.class, () -> {
            voucherRepository.update(updatedVoucher);
        });
    }

    @Test
    @Order(9)
    @DisplayName("바우처를 삭제할 때 아이디가 존재하지 않으면 예외를 발생한다.")
    public void testDeleteVoucherException() {
        assertThrows(RuntimeException.class, () -> {
            voucherRepository.delete(newVoucher);
        });
    }
}