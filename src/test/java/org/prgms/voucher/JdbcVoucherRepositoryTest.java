package org.prgms.voucher;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import org.junit.jupiter.api.*;
import org.prgms.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_10;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.prgms.voucher.VoucherType.FIXED_AMOUNT;
import static org.prgms.voucher.VoucherType.PERCENT_DISCOUNT;

@SpringJUnitConfig
@Import({TestConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;


    FixedAmountVoucher newFixedAmount;
    PercentDiscountVoucher newPercentDiscount;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        newFixedAmount = new FixedAmountVoucher(UUID.randomUUID(), 100, FIXED_AMOUNT);
        newPercentDiscount = new PercentDiscountVoucher(UUID.randomUUID(), 10, PERCENT_DISCOUNT);
        var mysqlConfig = aMysqldConfig(v5_7_10)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @Order(1)
    @DisplayName("FixedAmountVoucher를 추가할 수 있다. ")
    public void testInsert() {
        jdbcVoucherRepository.save(newFixedAmount);
        var retrievedVoucher = jdbcVoucherRepository.findById(newFixedAmount.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));

    }

    @Test
    @Order(2)
    @DisplayName("PercentDiscountVoucher를 추가할 수 있다. ")
    public void testInsert2() {
        jdbcVoucherRepository.save(newPercentDiscount);
        var retrievedVoucher = jdbcVoucherRepository.findById(newPercentDiscount.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("전체 Voucher를 조회할 수 있다")
    public void testFindAll() {
        var vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
    }


}
