package org.prgrms.kdt.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcWalletRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.repository"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order-mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    EmbeddedMysql embeddedMysql;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    JdbcWalletRepository jdbcWalletRepository;

    @Autowired
    CustomerRepository customerRepository;

    FixedAmountVoucher fixedAmountVoucher;

    PercentDiscountVoucher percentDiscountVoucher;

    @BeforeAll
    void clean() {
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 30, LocalDateTime.now());
        percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30, LocalDateTime.now());
        var mysqldConfig = aMysqldConfig(Version.v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order-mgmt", classPathScript("schema.sql"))
                .start();
    }

    @Test
    @DisplayName("지갑에서는 사용자에게 할당되지 않은 바우처이면, 바우처값을 가져올 수 없다.")
    void getVoucherUnprovided() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> jdbcWalletRepository.selectJoinVoucherCustomer(fixedAmountVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("사용자에게 할당된 바우처면, 바우처값을 가져올 수 있다.")
    void getVoucherProvided() {
        Customer newCustomer = new Customer(UUID.randomUUID(),"test","test@gmail.com",LocalDateTime.now() ,LocalDateTime.now());
        Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        customerRepository.insert(newCustomer);
        jdbcVoucherRepository.insert(newVoucher);
        jdbcVoucherRepository.updateVoucherOwner(newVoucher.getVoucherId(), newCustomer.getCustomerId());
        Voucher voucher = jdbcWalletRepository.selectJoinVoucherCustomer(newVoucher.getVoucherId());
        assertThat(voucher.getCustomer().getCustomerId(), equalTo(newCustomer.getCustomerId()));
    }
}