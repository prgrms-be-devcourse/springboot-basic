package com.programmers.voucher.repository.customer;

import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.model.voucher.FixedAmountVoucher;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerRepositoryTest {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    DataSource dataSource;
    private static EmbeddedMysql embeddedMysql;

    @Configuration
    @ComponentScan(basePackages = {"com.programmers.voucher.repository"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test_voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @BeforeAll
    static void setup() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("test_voucher", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("데이터베이스에 고객을 저장한다.")
    void save() {
        //given
        String email = "taehee@gmail.com";
        CustomerDto customerDto = new CustomerDto("taehee", email);

        //when
        customerRepository.save(customerDto);

        //then
        assertThat(customerRepository.findByEmail(email).getEmail())
                .isEqualTo(email);
    }

    @Test
    @Order(2)
    @DisplayName("데이터베이스에서 이메일을 통해 고객을 조회한다.")
    void findByEmail() {
        //given
        String email = "taehee@gmail.com";

        //when
        Customer result = customerRepository.findByEmail(email);

        //then
        assertThat(result.getEmail())
                .isEqualTo(email);
    }

    @Test
    @Order(3)
    @DisplayName("데이터베이스에서 고객이 가지고 있는 바우처 아이디를 통해 고객을 조회한다.")
    void findByVoucher() {
        //given
        String email = "taehee@gmail.com";
        Customer customer = customerRepository.findByEmail(email);
        Voucher voucher = insertSingleVoucherData();
        voucher.setCustomer(customer);
        voucherRepository.assign(voucher);

        //when
        Customer result = customerRepository.findByVoucher(voucher.getVoucherId());

        //then
        assertThat(result.getEmail())
                .isEqualTo(email);
    }

    private Voucher insertSingleVoucherData() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 3000);
        voucher.setVoucherType(VoucherType.toVoucherType("1"));
        return voucherRepository.save(voucher);
    }
}
