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

@ActiveProfiles("test")
@SpringJUnitConfig()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    Voucher newFixedVoucher, newPercentVoucher , copyFixedVoucher, copyPercentVoucher;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {

        newFixedVoucher =  new FixedAmountVoucher(UUID.randomUUID(), 10);
        newPercentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        copyFixedVoucher =  new FixedAmountVoucher(UUID.randomUUID(), 10);
        copyPercentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);

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

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("voucher insert 테스트")
    void testInsert() {
        try {
            voucherRepository.insert(newFixedVoucher);
            voucherRepository.insert(newPercentVoucher);
        } catch (BadSqlGrammarException e) {
            logger.error("testInsert - BadSqlGrammarException -> {}",e.getSQLException().getErrorCode());
        }

        System.out.println("new Fix voucher : => " + newFixedVoucher.toString());
        System.out.println("new Percent voucher : => " + newPercentVoucher.toString());

        Optional<Voucher> retrieveFixVoucher = voucherRepository.findById(newFixedVoucher.getVoucherId());
        assertThat(retrieveFixVoucher.isEmpty(), is(false));
        assertThat(retrieveFixVoucher.get(), samePropertyValuesAs(newFixedVoucher));

        Optional<Voucher> retrievePercentVoucher = voucherRepository.findById(newPercentVoucher.getVoucherId());
        assertThat(retrievePercentVoucher.isEmpty(), is(false));
        assertThat(retrievePercentVoucher.get(), samePropertyValuesAs(newPercentVoucher));
    }

    @Test
    @Order(3)
    @Disabled
    @DisplayName("value와 type이 같은 voucher insert 테스트")
    void testInsertDuplicateByEmail() {
        try {
            voucherRepository.insert(copyFixedVoucher);
//            jdbcVoucherRepository.insert(copyPercentVoucher);
        } catch (BadSqlGrammarException e) {
            logger.error("testInsert - BadSqlGrammarException -> {}",e.getSQLException().getErrorCode());
        }

        Optional<Voucher> retrieveCustomer = voucherRepository.findById(copyFixedVoucher.getVoucherId());
        assertThat(retrieveCustomer.isEmpty(), is(false));
        assertThat(retrieveCustomer.get(), samePropertyValuesAs(copyFixedVoucher));

    }

    @Test
    @Order(4)
    @DisplayName("JdbcVoucherRepository 에 들어있는 customer 수 조회 테스트")
    void testCountJdbcCustomerRepository() {
        assertThat(voucherRepository.count() == 2, is(true));
        System.out.println(voucherRepository.count());
    }


    @Test
    @Order(4)
    @DisplayName("voucher 전체 조회 테스트")
    void testFindAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        vouchers.forEach(e -> {
            System.out.println(e.toString());
        });
        assertThat(vouchers.isEmpty(), is(false));

    }

    @Test
    @Order(4)
    @DisplayName("voucher Id 를 이용해 조회 테스트")
    void testFindById() {
        UUID fixVoucherId = newFixedVoucher.getVoucherId();
        UUID percentVoucherId = newPercentVoucher.getVoucherId();
        Optional<Voucher> voucher = voucherRepository.findById(fixVoucherId);
        assertThat(voucher.get(), samePropertyValuesAs(newFixedVoucher));
        voucher = voucherRepository.findById(percentVoucherId);
        assertThat(voucher.get(), samePropertyValuesAs(newPercentVoucher));

    }

    @Test
    @Order(5)
    @DisplayName("저장되어 있는 voucher 정보 수정 테스트")
    void testUpdateCustomer() {
        newFixedVoucher.changeValue(200L);
        voucherRepository.update(newFixedVoucher);

        List<Voucher> customers = voucherRepository.findAll();
        assertThat(customers, hasSize(2));

        Optional<Voucher> retrievedCustomer = voucherRepository.findById(newFixedVoucher.getVoucherId());

        assertThat(retrievedCustomer.isEmpty(),is(false));
        assertThat(retrievedCustomer.get(),samePropertyValuesAs(newFixedVoucher));
    }

    @Test
    @Order(6)
    @DisplayName("voucher 삭제 테스트")
    void testDeleteCustomer() {
        voucherRepository.delete(newFixedVoucher);

        List<Voucher> customers = voucherRepository.findAll();
        assertThat(customers, hasSize(1));

        Optional<Voucher> retrievedCustomer = voucherRepository.findById(newFixedVoucher.getVoucherId());
        assertThat(retrievedCustomer.equals(Optional.empty()), is(true));
    }

}