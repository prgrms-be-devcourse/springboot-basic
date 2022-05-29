package org.prgrms.kdt.voucher.repository;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScripts;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgrms.kdt.config.TestConfig;
import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class JdbcVoucherRepositoryTest {

    private final int VALID_SIZE = 1;

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setUp() {
        MysqldConfig config = aMysqldConfig(v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-voucher_mgmt", classPathScripts("schema.sql"))
            .start();
    }

    @AfterAll
    static void cleanUp() {
        embeddedMysql.stop();
    }

    @AfterEach
    void clean() {
        voucherRepository.deleteAll();
    }

    @Autowired
    VoucherRepository voucherRepository;

    private static final Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, LocalDateTime.now());

    @Test
    @DisplayName("바우처를 저장할 수 있다.")
    void saveTest() {
        voucherRepository.save(newVoucher);
        List<Voucher> all = voucherRepository.findAll();
        assertThat(all.isEmpty(), Matchers.is(false));
    }

    @Test
    @DisplayName("바우처를 모두 조회할 수 있다.")
    void findAllTest() {
        voucherRepository.save(newVoucher);
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.size(), equalTo(VALID_SIZE));
    }

    @Test
    @DisplayName("특정 바우처를 조회할 수 있다.")
    void findTest() {
        voucherRepository.save(newVoucher);
        Optional<Voucher> voucher = voucherRepository.findById(newVoucher.getId());
        assertThat(voucher.isEmpty(), Matchers.is(false));
    }

    @Test
    @DisplayName("조회하려는 아이디의 바우처가 없다면 비어있는 옵셔널을 반환한다.")
    void findEmptyTest() {
        Optional<Voucher> voucher = voucherRepository.findById(UUID.randomUUID());
        assertThat(voucher.isEmpty(), Matchers.is(true));
    }

    @Test
    @DisplayName("바우처를 삭제할 수 있다.")
    void deleteAllTest() {
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), Matchers.is(true));
    }

}