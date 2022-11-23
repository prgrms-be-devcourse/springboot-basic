package org.prgrms.kdt.voucher;

import com.wix.mysql.EmbeddedMysql;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.prgrms.kdt.JdbcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;


@Import(JdbcConfig.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("prod")
class JdbcVoucherManagerTest {

    @Autowired
    private VoucherManager voucherManager;

    private EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("voucher_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }


    @BeforeEach
    void init() {
        voucherManager.deleteAll();
    }

    @DisplayName("바우처는 데이터베이스에 저장될 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {
            "fixed, 0",
            "fixed, 10",
            "fixed, 10000",
            "percent, 10",
            "percent, 100",
            "percent, 21"
    })
    void saveTest(String type, String value) {
        // given
        Voucher voucher = Voucher.newInstance(VoucherType.of(type), new VoucherAmount(value));

        // when
        Voucher savedVoucher = voucherManager.save(voucher);

        // then
        Assertions.assertThat(voucherManager.findById(savedVoucher.getId()).isPresent())
                .isTrue();
    }

    @DisplayName("데이터베이스에서 바우처들을 조회할 수 있다.")
    @Test
    void findAllTest() {
        // given
        Voucher voucher1 = Voucher.newInstance(VoucherType.of("fixed"), new VoucherAmount("10"));
        Voucher voucher2 = Voucher.newInstance(VoucherType.of("percent"), new VoucherAmount("20"));

        Voucher savedVoucher1 = voucherManager.save(voucher1);
        Voucher savedVoucher2 = voucherManager.save(voucher2);

        // when
        List<Voucher> actualVouchers = voucherManager.findAll();

        // then
        assertThat(actualVouchers)
                .usingRecursiveComparison()
                .isEqualTo(List.of(savedVoucher1, savedVoucher2));
    }

    @DisplayName("Id로 바우처를 조회할 수 있다.")
    @Test
    void findById() {
        // given
        Voucher voucher = Voucher.newInstance(VoucherType.of("fixed"), new VoucherAmount("10"));
        Voucher savedVoucher = voucherManager.save(voucher);

        // when

        // then
        assertThat(voucherManager.findById(savedVoucher.getId()).isPresent())
                .isTrue();
    }

    @DisplayName("바우처는 수정될 수 있다.")
    @Test
    void updateTest() {
        // given
        Voucher voucher = Voucher.newInstance(VoucherType.of("fixed"), new VoucherAmount("10"));
        Voucher savedVoucher = voucherManager.save(voucher);
        Voucher updatedVoucher = Voucher.from(savedVoucher.getId(), VoucherType.FIXED, new VoucherAmount("20"));

        // when
        voucherManager.update(updatedVoucher);
        Voucher actualVoucher = voucherManager.findById(savedVoucher.getId()).get();
        long actualAmount = actualVoucher.getAmount().getValue();

        // then
        assertThat(actualAmount)
                .isEqualTo(20);

    }

    @DisplayName("Id로 바우처는 삭제될 수 있다.")
    @Test
    void deleteByIdTest() {
        // given
        Voucher voucher = Voucher.newInstance(VoucherType.of("fixed"), new VoucherAmount("10"));
        Voucher savedVoucher = voucherManager.save(voucher);

        // when
        voucherManager.deleteById(savedVoucher.getId());

        // then
        assertThat(voucherManager.findById(savedVoucher.getId()).isPresent())
                .isFalse();
    }
}