package org.prgrms.kdtspringdemo.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.VoucherType;
import org.prgrms.kdtspringdemo.voucher.Voucher;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("dev")
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcVoucherRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepositoryTest.class);
    @Autowired
    DataSource dataSource;
    @Configuration
    @ComponentScan(basePackages = {
            "org.prgrms.kdtspringdemo.voucher",
    })
    static class Config {
        @Bean
        public DataSource dataSource() {
//            Embedded DataBase로 설정하기(mysql v5.7.latest)
//            var dataSource = DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost:2215/customer_mgmt")
//                    .username("test")
//                    .password("test1234!")
//                    .build();
//            return dataSource;

//          docker에 올린 DB로 시작하기(mysql v8.0.11)
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/customer_mgmt")
                    .username("root")
                    .password("root1234!")
                    .build();
            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    Voucher newVoucher;

    EmbeddedMysql embeddMysql;

    @BeforeAll
    void setup() {
        newVoucher = new Voucher(UUID.randomUUID(), "FixVoucher", 1000L, VoucherType.FIXED_AMOUNT, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
// Embedded DataBase로 실행하기(mysql v5.7.latest)
//        var mysqlConfig = aMysqldConfig(v5_7_latest)
//                .withCharset(UTF8)
//                .withPort(2215)
//                .withUser("test", "test1234!")
//                .build();

//        embeddMysql = anEmbeddedMysql(mysqlConfig)
//                .addSchema("customer_mgmt", classPathScript("schema.sql"))
//                .start();

        jdbcVoucherRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() throws InterruptedException {
        try {
            jdbcVoucherRepository.insert(newVoucher);
        } catch (BadSqlGrammarException e) {
            logger.error("Get BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode(), e);
        }
//        customerJdbcRepository.insert(newCustomer);

        var retrievedVoucher = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        System.out.println(newVoucher);
        System.out.println(retrievedVoucher.get());
        assertThat(retrievedVoucher.get(), samePropertyValuesAs((Voucher) newVoucher));
    }
    @Test
    @Order(2)
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() throws InterruptedException {
        var customers = jdbcVoucherRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }
    @Test
    @Order(3)
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindName() throws InterruptedException {
        var customers = jdbcVoucherRepository.findByName(newVoucher.getName());
        assertThat(customers.isEmpty(), is(false));
        var unknown = jdbcVoucherRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(4)
    @DisplayName("타입으로 고객을 조회할 수 있다.")
    public void testFindType() throws InterruptedException {
        var customers = jdbcVoucherRepository.findByType(newVoucher.getType().name());
        assertThat(customers.isEmpty(), is(false));
        var unknown = jdbcVoucherRepository.findByType("unknown-type");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() throws InterruptedException {
        newVoucher.changeName("updated-user");
        jdbcVoucherRepository.update(newVoucher);
        var all = jdbcVoucherRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(newVoucher)));
        var retrievedCustomer = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newVoucher));
    }
}