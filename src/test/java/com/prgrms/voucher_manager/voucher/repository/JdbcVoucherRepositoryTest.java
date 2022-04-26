package com.prgrms.voucher_manager.voucher.repository;

import com.prgrms.voucher_manager.voucher.FixedAmountVoucher;
import com.prgrms.voucher_manager.voucher.PercentDiscountVoucher;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringJUnitConfig()
class JdbcVoucherRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"com.prgrms.voucher_manager.voucher.repository"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-jdbc-voucher-mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    DataSource dataSource;

    Voucher newFixedVoucher, newPercentVoucher;

    EmbeddedMysql embeddedMysql;

    @BeforeEach
    void setup() {

        newFixedVoucher =  new FixedAmountVoucher(UUID.randomUUID(), 10);
        newPercentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-jdbc-voucher-mgmt", classPathScript("voucherSchemaTest.sql"))
                .start();

    }

    @AfterEach
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("voucher insert 테스트")
    void testInsert() {

        voucherRepository.insert(newFixedVoucher);
        voucherRepository.insert(newPercentVoucher);

        Optional<Voucher> retrieveFixVoucher = voucherRepository.findById(newFixedVoucher.getVoucherId());
        assertThat(retrieveFixVoucher.isEmpty(), is(false));
        assertThat(retrieveFixVoucher.get(), samePropertyValuesAs(newFixedVoucher));

        Optional<Voucher> retrievePercentVoucher = voucherRepository.findById(newPercentVoucher.getVoucherId());
        assertThat(retrievePercentVoucher.isEmpty(), is(false));
        assertThat(retrievePercentVoucher.get(), samePropertyValuesAs(newPercentVoucher));
    }

    @Test
    @DisplayName("value와 type이 같은 voucher가 이미 있는 경우 insert가 안된다")
    void testInsertDuplicateByEmail() {

        voucherRepository.insert(newFixedVoucher);
        voucherRepository.insert(newPercentVoucher);

        FixedAmountVoucher testVoucher1 = new FixedAmountVoucher(UUID.randomUUID(), 10);
        PercentDiscountVoucher testVoucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        assertThrows(DataAccessException.class, () -> voucherRepository.insert(testVoucher1));
        assertThrows(DataAccessException.class, () -> voucherRepository.insert(testVoucher2));

        Optional<Voucher> retrieveVoucher = voucherRepository.findById(testVoucher1.getVoucherId());
        assertThat(retrieveVoucher.isEmpty(), is(true));
        retrieveVoucher = voucherRepository.findById(testVoucher2.getVoucherId());
        assertThat(retrieveVoucher.isEmpty(), is(true));

    }


    @Test
    @DisplayName("voucher 전체 조회 테스트")
    void testFindAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));

        voucherRepository.insert(newFixedVoucher);
        vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers, hasSize(1));
    }

    @Test
    @DisplayName("voucher Id 를 이용해 조회 테스트")
    void testFindById() {
        UUID fixVoucherId = newFixedVoucher.getVoucherId();
        Optional<Voucher> voucher = voucherRepository.findById(fixVoucherId);
        assertThat(voucher.isEmpty(), is(true));

        voucherRepository.insert(newFixedVoucher);
        voucher = voucherRepository.findById(fixVoucherId);
        assertThat(voucher.get(), samePropertyValuesAs(newFixedVoucher));
    }

    @Test
    @DisplayName("voucher type 를 이용해 조회 테스트")
    void testFindByType() {

        voucherRepository.insert(newFixedVoucher);
        List<Voucher> fixType = voucherRepository.findByType("fix");

        assertThat(fixType.isEmpty(), is(false));
        assertThat(fixType.get(0), samePropertyValuesAs(newFixedVoucher));
    }

    @Test
    @DisplayName("voucher 정보 수정 테스트")
    void testUpdateCustomer() {
        voucherRepository.insert(newFixedVoucher);
        newFixedVoucher.changeValue(200L);
        voucherRepository.update(newFixedVoucher);

        Optional<Voucher> retrievedCustomer = voucherRepository.findById(newFixedVoucher.getVoucherId());

        assertThat(retrievedCustomer.isEmpty(),is(false));
        assertThat(retrievedCustomer.get(),samePropertyValuesAs(newFixedVoucher));
    }

    @Test
    @DisplayName("voucher 삭제 테스트")
    void testDeleteCustomer() {
        voucherRepository.insert(newFixedVoucher);
        List<Voucher> customers = voucherRepository.findAll();
        assertThat(customers, hasSize(1));

        voucherRepository.delete(newFixedVoucher);
        customers = voucherRepository.findAll();
        assertThat(customers.isEmpty(), is(true));

    }

}