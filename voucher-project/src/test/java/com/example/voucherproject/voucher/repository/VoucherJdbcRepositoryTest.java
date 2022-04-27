package com.example.voucherproject.voucher.repository;

import com.example.voucherproject.voucher.enums.VoucherType;
import com.example.voucherproject.voucher.domain.Voucher;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.example.voucherproject.voucher.enums.VoucherType.FIXED;
import static com.example.voucherproject.voucher.enums.VoucherType.PERCENT;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Voucher JDBC REPO TEST")
class VoucherJdbcRepositoryTest {

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;
    EmbeddedMysql embeddedMysql;

    @Test
    @Order(1)
    @DisplayName("insert")
    void insertTest() {
        var voucher = makeVoucher(10000,FIXED);
        voucherJdbcRepository.insert(voucher);
        assertThat(voucherJdbcRepository.count()).isEqualTo(1);
        voucherJdbcRepository.deleteAll();
    }


    @Test
    @Order(2)
    @DisplayName("find all")
    void findAllTest() {
        var voucher1 = voucherJdbcRepository.insert(makeVoucher(10000,FIXED));
        var voucher2 = voucherJdbcRepository.insert(makeVoucher(11000,FIXED));
        var voucher3 = voucherJdbcRepository.insert(makeVoucher(12000,FIXED));

        var vouchers = voucherJdbcRepository.findAll();

        assertThat(vouchers).usingRecursiveFieldByFieldElementComparator().contains(voucher1,voucher2,voucher3);
        voucherJdbcRepository.deleteAll();
    }

    @Test
    @Order(3)
    @DisplayName("find by type")
    void findByTypeTest() {
        var voucher1 = voucherJdbcRepository.insert(makeVoucher(10000,FIXED));
        var voucher2 = voucherJdbcRepository.insert(makeVoucher(10,PERCENT));
        var voucher3 = voucherJdbcRepository.insert(makeVoucher(20000,FIXED));
        var voucher4 = voucherJdbcRepository.insert(makeVoucher(20, PERCENT));

        var vouchers = voucherJdbcRepository.findHavingTypeAll(FIXED);

        assertThat(vouchers).usingRecursiveFieldByFieldElementComparator().contains(voucher1,voucher3);
        assertThat(vouchers).usingRecursiveFieldByFieldElementComparator().doesNotContain(voucher2,voucher4);

        voucherJdbcRepository.deleteAll();
    }

    @Test
    @Order(4)
    @DisplayName("find by id")
    void findById() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();

        var voucher1 = voucherJdbcRepository.insert(makeVoucherWithId(uuid1,10000,FIXED));
        var voucher2 = voucherJdbcRepository.insert(makeVoucherWithId(uuid2,10,PERCENT));
        var voucher3 = voucherJdbcRepository.insert(makeVoucherWithId(uuid3,20000,FIXED));

        var queryVoucher1 = voucherJdbcRepository.findById(uuid1);
        var queryVoucher2 = voucherJdbcRepository.findById(uuid2);
        var queryVoucher3 = voucherJdbcRepository.findById(uuid3);

        assertThat(voucher1).usingRecursiveComparison().isEqualTo(queryVoucher1.get());
        assertThat(voucher2).usingRecursiveComparison().isEqualTo(queryVoucher2.get());
        assertThat(voucher3).usingRecursiveComparison().isEqualTo(queryVoucher3.get());

        voucherJdbcRepository.deleteAll();
    }


    /* helper method */
    private Voucher makeVoucher(long amount, VoucherType type){
        return makeVoucherWithId(UUID.randomUUID(), amount, type);
    }
    private Voucher makeVoucherWithId(UUID id, long amount, VoucherType type){
        return new Voucher(id, type, amount, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }

    /* Embedded DB Setting */
    @BeforeAll
    void setup() {
        System.out.println("before al");
        var config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("voucher_app", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup(){
        voucherJdbcRepository.deleteAll();
        embeddedMysql.stop();
    }

    @Configuration
    static class Config{
        @Bean
        public DataSource dataSource(){
            var source = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/voucher_app")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            source.setMaximumPoolSize(1000);
            source.setMinimumIdle(100);
            return source;
        }
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }
        @Bean
        public VoucherJdbcRepository voucherJdbcRepository(JdbcTemplate jdbcTemplate){
            return new VoucherJdbcRepository(jdbcTemplate);
        }
    }
}
