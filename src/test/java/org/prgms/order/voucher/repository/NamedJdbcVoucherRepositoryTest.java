package org.prgms.order.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.order.voucher.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_10;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NamedJdbcVoucherRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(NamedJdbcVoucherRepositoryTest.class);


    @Configuration
    @ComponentScan(basePackages = {"org.prgms.order.voucher"})
    static class Config {
        @Bean
        public DataSource dataSource(){
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt") //test용
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager){
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Autowired
    NamedJdbcVoucherRepository namedJdbcVoucherRepository;

    @Autowired
    VoucherCreateStretage voucherCreateStretage;

    @Autowired
    DataSource dataSource;

    Voucher testUpdate;
    Voucher newVoucher1;
    Voucher newVoucher2;
    Voucher notVoucher;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        testUpdate = voucherCreateStretage.createVoucher(VoucherIndexType.PERCENT, new VoucherData(UUID.randomUUID(),50,LocalDateTime.now().withNano(0)));
        newVoucher1 =voucherCreateStretage.createVoucher(VoucherIndexType.FIXED, new VoucherData(UUID.randomUUID(),5000,LocalDateTime.now().withNano(0)));
        newVoucher2 = voucherCreateStretage.createVoucher(VoucherIndexType.PERCENT, new VoucherData(UUID.randomUUID(),20,LocalDateTime.now().withNano(0)));
        notVoucher = voucherCreateStretage.createVoucher(VoucherIndexType.FIXED, new VoucherData(UUID.randomUUID(),100000,LocalDateTime.now().withNano(0)));
        var mysqldConfig = aMysqldConfig(v5_7_10) //port, characterset을 지정해 줄 수 있음
                .withCharset(UTF8)
                .withPort(2215) //임의의 포트 지정
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();

        namedJdbcVoucherRepository.insert(newVoucher1);
        namedJdbcVoucherRepository.insert(newVoucher2);

    }

    @Test
    @Order(1)
    @DisplayName("Hikari Connectionpool test")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @AfterAll
    void cleanUp(){
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("바우처를 추가할 수 있다.")
    void testInsert() {
        try {
            namedJdbcVoucherRepository.insert(testUpdate);
        } catch (BadSqlGrammarException e) {
            logger.error("Got BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode());
        }
        var retrievedCustomer = namedJdbcVoucherRepository.findById(testUpdate.getVoucherId());
        logger.info("testUpdate : voucherId -> {} type -> {} amount ->{} createdAt -> {}", testUpdate.getVoucherId(), testUpdate.getType(), testUpdate.getAmount(), testUpdate.getCreatedAt());
        logger.info("retrievedCustomer : voucherId -> {} type -> {} amount ->{} createdAt -> {}", retrievedCustomer.get().getVoucherId(), retrievedCustomer.get().getType(), retrievedCustomer.get().getAmount(), retrievedCustomer.get().getCreatedAt());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(testUpdate));
    }


    @Test
    @Order(2)
    @DisplayName("아이디로 바우처를 찾을 수 있다.")
    void findById() {
        var voucher = namedJdbcVoucherRepository.findById(testUpdate.getVoucherId());
        assertThat(voucher.isEmpty(), is(false));

        var unknown = namedJdbcVoucherRepository.findById(notVoucher.getVoucherId());
        assertThat(unknown.isEmpty(), is(true));
    }


    @Test
    @Order(3)
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void findAllVoucher() {
        var vouchers = namedJdbcVoucherRepository.findAllVoucher();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers,hasSize(3));
    }
}