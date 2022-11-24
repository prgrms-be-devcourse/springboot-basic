package com.example.weeklymission3.repositories;

import com.example.weeklymission3.models.Voucher;
import com.example.weeklymission3.models.VoucherType;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.wix.mysql.EmbeddedMysql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanUp() {
        embeddedMysql.stop();
    }

    @Autowired
    VoucherRepository voucherRepository;

    private final Voucher newVoucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED.toString(), 20, LocalDateTime.now());

    @Test
    @Order(1)
    @DisplayName("바우처를 추가할 수 있다")
    void testInsert() {
        voucherRepository.insert(newVoucher);
        List<Voucher> all = voucherRepository.findAll();
        assertThat(all.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 아이디로 조회할 수 있다")
    void testFindById() {
        Optional<Voucher> voucher = voucherRepository.findById(newVoucher.getVoucherId());
        assertThat(voucher.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("특정 기간 내에 생성된 바우처를 조회할 수 있다")
    void testFindByTime() {
        LocalDateTime startTime = newVoucher.getCreatedAt().minusDays(1);
        LocalDateTime endTime = newVoucher.getCreatedAt().plusDays(1);
        List<Voucher> vouchers = voucherRepository.findByTime(startTime, endTime);
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("특정 할인 타입별로 바우처를 조회할 수 있다")
    void testFindByType() {
        List<Voucher> vouchers = voucherRepository.findByType(VoucherType.checkVoucherType(newVoucher.getType()));
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("바우처를 전체 삭제할 수 있다")
    void testDelete() {
        voucherRepository.deleteAll();
        List<Voucher> all = voucherRepository.findAll();
        assertThat(all.isEmpty(), is(true));
    }
}