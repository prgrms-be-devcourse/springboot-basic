package com.example.kdtspringmission.voucher.repository;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.kdtspringmission.customer.domain.Customer;
import com.example.kdtspringmission.customer.repository.CustomerJdbcRepository;
import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(Lifecycle.PER_CLASS)
public class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
        basePackages = {"com.example.kdtspringmission.customer",
            "com.example.kdtspringmission.voucher"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                .username("test")
                .password("test1234!")
                .type(HikariDataSource.class)
                .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    CustomerJdbcRepository customerRepository;

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void beforeAll() {
        MysqldConfig config = aMysqldConfig(v8_0_11)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-order_mgmt", classPathScript("sql/test-schema.sql"))
            .start();
    }

    @BeforeEach
    void beforeEach() {
        voucherRepository.deleteAll();
    }

    @AfterAll
    void afterAll() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("단건의 데이터가 잘 추가 되는가")
    void testInsert() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 150L);

        //when
        UUID insertedVoucher = voucherRepository.insert(voucher);

        //then
        assertThat(insertedVoucher).isEqualTo(voucher.getId());
    }

    @Test
    @DisplayName("Id로 조회가 잘 되는가")
    void testFindById() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 150L);
        UUID voucherId = voucher.getId();
        voucherRepository.insert(voucher);

        //when
        Voucher findVoucher = voucherRepository.findById(voucherId);

        //then
        assertThat(findVoucher).isEqualTo(voucher);
    }

    @Test
    @DisplayName("특정 고객이 소유한 바우처를 조회한다")
    void testFindByOwnerId() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "tester", "tester@gmail.com",
            LocalDateTime.now());
        customerRepository.insert(customer);

        List<Voucher> vouchers = new ArrayList<>() {{
            add(new FixedAmountVoucher(UUID.randomUUID(), 100L, customer.getCustomerId()));
            add(new FixedAmountVoucher(UUID.randomUUID(), 100L, customer.getCustomerId()));
            add(new FixedAmountVoucher(UUID.randomUUID(), 100L, customer.getCustomerId()));
        }};

        for (Voucher voucher : vouchers) {
            voucherRepository.insert(voucher);
        }

        // when
        List<Voucher> findVouchers = voucherRepository.findByOwnerId(customer.getCustomerId());

        // then
        assertThat(findVouchers).hasSameElementsAs(vouchers);

    }

}
