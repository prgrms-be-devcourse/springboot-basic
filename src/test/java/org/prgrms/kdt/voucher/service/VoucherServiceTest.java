package org.prgrms.kdt.voucher.service;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScripts;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgrms.kdt.config.TestConfig;
import org.prgrms.kdt.error.VoucherNotFoundException;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class VoucherServiceTest {

    private static final VoucherType VALID_VOUCHER_TYPE = VoucherType.FIXED;
    private static final long VALID_VOUCHER_AMOUNT = 1000L;

    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    VoucherService voucherService;

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

    @Test
    @DisplayName("바우처를 만들 수 있다.")
    void makeTest() {
        // GIVEN
        // WHEN
        Voucher voucher = voucherService.makeVoucher(VALID_VOUCHER_TYPE, VALID_VOUCHER_AMOUNT);

        // THEN
        assertThat(voucher.getVoucherType(), is(VALID_VOUCHER_TYPE));
        assertThat(voucher.getValue(), is(VALID_VOUCHER_AMOUNT));
    }

    @Test
    @DisplayName("아이디를 통해 바우처를 조회할 수 있다.")
    void findByIdTest() {
        // GIVEN
        Voucher newVoucher = voucherService.makeVoucher(VALID_VOUCHER_TYPE, VALID_VOUCHER_AMOUNT);

        // WHEN
        Voucher findVoucher = voucherRepository.findById(newVoucher.getId()).get();

        // THEN
        assertThat(newVoucher, samePropertyValuesAs(findVoucher));
    }

    @Test
    @DisplayName("아이디에 해당하는 바우처가 없다면 예외를 발생시킨다.")
    void findByIdNotFoundTest() {
        // GIVEN
        // WHEN
        voucherRepository.deleteAll();
        // THEN
        assertThrows(VoucherNotFoundException.class, () -> {
            voucherService.getVoucher(UUID.randomUUID());
        });
    }

    @Test
    @DisplayName("바우처를 전체조회 할 수 있다.")
    void getVouchersTest() {
        // GIVEN
        // WHEN
        List<Voucher> vouchers = voucherService.getVouchers();

        // THEN
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("바우처를 전체 삭제할 수 있다.")
    void deleteAllTest() {
        // GIVEN
        voucherService.deleteVouchers();

        // WHEN
        List<Voucher> vouchers = voucherRepository.findAll();

        // THEN
        assertThat(vouchers.isEmpty(), is(true));
    }

}
