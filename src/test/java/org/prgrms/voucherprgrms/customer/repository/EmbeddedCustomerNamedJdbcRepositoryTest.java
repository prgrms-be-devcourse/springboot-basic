package org.prgrms.voucherprgrms.customer.repository;


import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.voucherprgrms.customer.model.Customer;
import org.prgrms.voucherprgrms.voucher.model.FixedAmountVoucher;
import org.prgrms.voucherprgrms.voucher.repository.VoucherNamedJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmbeddedCustomerNamedJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.voucherprgrms"}
    )
    static class AppConfig {
        @Bean
        public DataSource dataSource() {

            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2210/test-voucher_prgrms")
                    .username("test")
                    .password("test123")
                    .type(HikariDataSource.class)
                    .build();

            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    CustomerNamedJdbcRepository customerNamedJdbcRepository;

    @Autowired
    VoucherNamedJdbcRepository voucherNamedJdbcRepository;

    @Autowired
    DataSource dataSource;
    EmbeddedMysql embeddedMysql;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "testUser", "testUser@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        var mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test123")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-voucher_prgrms", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("CUSTOMER INSERT")
    void insertTest() {
        //given
        customerNamedJdbcRepository.insert(newCustomer);
        var findCustomer = customerNamedJdbcRepository.findByEmail(newCustomer.getEmail());

        //then
        assertThat(findCustomer.isPresent(), is(true));
        assertThat(findCustomer.get(), samePropertyValuesAs(newCustomer));
    }


    @Test
    @Order(2)
    @DisplayName("Voucher 할당 테스트")
    void allocateVoucherTest() {
        var newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        voucherNamedJdbcRepository.insert(newVoucher);

        //when
        newCustomer.allocateVoucher(newVoucher);
        customerNamedJdbcRepository.changeVoucher(newCustomer);

        var findCustomer = customerNamedJdbcRepository.findByVoucher(newVoucher);

        //then
        assertThat(findCustomer.isEmpty(), is(false));
        assertThat(findCustomer.get().getVoucherId(), is(equalTo(newVoucher.getVoucherId())));
    }

    @Test
    @Order(3)
    @DisplayName("Voucher 제거 테스트")
    void removeVoucherTest() {
        newCustomer.removeVoucher();
        customerNamedJdbcRepository.changeVoucher(newCustomer);

        var findCustomer = customerNamedJdbcRepository.findByEmail(newCustomer.getEmail());

        //then
        assertThat(findCustomer.isEmpty(), is(false));
        assertThat(findCustomer.get().getVoucherId(), is(nullValue()));
    }

}
