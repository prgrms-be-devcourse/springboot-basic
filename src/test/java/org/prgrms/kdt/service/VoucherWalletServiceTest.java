package org.prgrms.kdt.service;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.repository.CustomerRepository;
import org.prgrms.kdt.repository.JdbcVoucherRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherWalletServiceTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.repository", "org.prgrms.kdt.service"}
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
    VoucherWalletService voucherWalletService;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeAll
    void clean() {
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

    @AfterEach
    void cleanTable() {
        jdbcVoucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자이메일로 사용자가 보유한 바우처를 조회할 수 있다")
    void getVoucherListByCustomerEmail() {
        Customer customer = new Customer(UUID.randomUUID(),"test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        customerRepository.insert(customer);
        jdbcVoucherRepository.insert(voucher);
        jdbcVoucherRepository.updateVoucherOwner(voucher.getVoucherId(), customer.getCustomerId());

        Optional<Map<UUID, Voucher>> voucherList = voucherWalletService.getVoucherListByCustomerEmail(customer.getEmail());

        assertThat(voucherList.isPresent(), is(true));
        assertThat(voucherList.get().get(voucher.getVoucherId()).getVoucherId(), equalTo(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("사용자 이메일이 없거나, 보유하고 있는 바우처가 없는경우 바우처리스트를 가져올 수 없다")
    void getVoucherListByCustomerEmailValidate() {
        Customer customer = new Customer(UUID.randomUUID(),"test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        customerRepository.insert(customer);
        jdbcVoucherRepository.insert(voucher);

        Optional<Map<UUID, Voucher>> unknownCustomerVoucherList = voucherWalletService.getVoucherListByCustomerEmail("unknown@gmail.com");
        Optional<Map<UUID, Voucher>> newCustomerVoucherList = voucherWalletService.getVoucherListByCustomerEmail(customer.getEmail());
        assertThat(unknownCustomerVoucherList.isEmpty(), is(true));
        assertThat(newCustomerVoucherList.isEmpty(), is(true));
    }


}