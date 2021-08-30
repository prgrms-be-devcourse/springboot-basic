package org.prgrms.kdt.devcourse.voucher;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.devcourse.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {



    @Configuration
    static class config{
        @Bean
        public DataSource dataSource(){
            //using only test
            DataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }
    }
    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    FixedAmountVoucher fixedAmountVoucher;
    PercentDiscountVoucher percentDiscountVoucher;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp(){
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);

        var  mysqldConfig= aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test_mgmt",classPathScript("schema.sql"))
                .start();
    }
    @AfterAll
    void cleanUp(){
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("바우처를 새로 추가할 수 있다.")
    @Order(1)
    void testInsert() {
        jdbcVoucherRepository.deleteAll();

        jdbcVoucherRepository.insert(fixedAmountVoucher);
        Optional<Voucher> retrievedVoucher = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());

        assertThat(retrievedVoucher. isPresent(),is(true));
        assertThat(retrievedVoucher.get(),samePropertyValuesAs(fixedAmountVoucher));
    }
    @Test
    @Order(2)
    @DisplayName("바우처를 ID로 찾을 수 있다.")
    void testFindById() {

        Optional<Voucher> retrievedVoucher = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(retrievedVoucher. isPresent(),is(true));
        assertThat(retrievedVoucher.get(),samePropertyValuesAs(fixedAmountVoucher));
    }
    
    @Test
    @Order(3)
    @DisplayName("레포지토리에 추가되지 않은 바우처는 찾을 수 없다. 에러발생.")
    void testFindByIdError() {
        Assertions.assertThrows(RuntimeException.class, ()-> jdbcVoucherRepository.findById(percentDiscountVoucher.getVoucherId()));
    }

    

    @Test
    @Order(4)
    @DisplayName("레포지토리의 모든 바우처를 찾을 수 있다.")
    void testFindAll() {
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(),is(false));
    }


    @Test
    @DisplayName("레포지토리의 모든 바우처를 삭제할 수 있다.")
    void deleteAll() {
        jdbcVoucherRepository.deleteAll();
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(),is(true));
    }
}