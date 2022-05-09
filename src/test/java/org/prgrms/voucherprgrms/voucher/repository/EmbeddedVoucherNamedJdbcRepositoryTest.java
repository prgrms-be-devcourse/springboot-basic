package org.prgrms.voucherprgrms.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.voucherprgrms.voucher.model.FixedAmountVoucher;
import org.prgrms.voucherprgrms.voucher.model.PercentDiscountVoucher;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmbeddedVoucherNamedJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.voucherprgrms.voucher"}
    )
    static class AppConfig {

        @Bean
        public DataSource dataSource() {

            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher_prgrms")
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

        @Bean
        public VoucherNamedJdbcRepository voucherNamedJdbcRepository(NamedParameterJdbcTemplate template) {
            return new VoucherNamedJdbcRepository(template);
        }
    }

    @Autowired
    DataSource dataSource;
    @Autowired
    VoucherNamedJdbcRepository voucherNamedJdbcRepository;

    Voucher newVoucher;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
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
    @DisplayName("INSERT 쿼리 테스트")
    void voucherInsertTest() {
        voucherNamedJdbcRepository.insert(newVoucher);

        var findVoucher = voucherNamedJdbcRepository.findById(newVoucher.getVoucherId());

        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("ID를 통한 검색 테스트")
    void voucherFindByIdTest() {
        var findVoucher = voucherNamedJdbcRepository.findById(newVoucher.getVoucherId());

        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("생성 날짜에 대한 검색 테스트")
    void voucherFindByCreatedAtTest() {

        //given
        var findList = voucherNamedJdbcRepository.findByCreated(LocalDateTime.now().minusDays(2), LocalDateTime.now());

        assertThat(findList, hasSize(1));
        assertThat(findList.get(0), samePropertyValuesAs(newVoucher));

    }


    @Test
    @Order(4)
    @DisplayName("VoucherType을 통한 검색 테스트")
    void voucherFindByTypeTest() {

        voucherNamedJdbcRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 5));
        voucherNamedJdbcRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 10));
        voucherNamedJdbcRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 20));

        var findList = voucherNamedJdbcRepository.findByVoucherType(newVoucher.getDTYPE());

        assertThat(findList, hasSize(1));
        assertThat(findList.get(0), samePropertyValuesAs(newVoucher));
    }

    @Test
    @Order(5)
    @DisplayName("중복 키 에러 테스트")
    void duplicateKeyExceptionTest(){

        assertThrows(DuplicateKeyException.class,
                () -> voucherNamedJdbcRepository.insert(new FixedAmountVoucher(newVoucher.getVoucherId(), 1000)));

    }

    @Test
    @Order(6)
    @DisplayName("삭제 실패 테스트")
    void failedDeleteByIdTest(){

        //given
        var uuid = UUID.randomUUID();
        //then
        assertThrows(IllegalArgumentException.class,
                () -> voucherNamedJdbcRepository.deleteById(uuid));
    }

    @Test
    @Order(7)
    @DisplayName("UUID를 이용하여 voucher 삭제하기")
    void deleteByIdTest(){

        voucherNamedJdbcRepository.deleteById(newVoucher.getVoucherId());

        var emptyOne = voucherNamedJdbcRepository.findById(newVoucher.getVoucherId());
        assertThat(emptyOne.isEmpty(), is(true));
    }

    @Test
    @Order(8)
    @DisplayName("DELETE ALL 테스트")
    void deleteAllTest() {
        voucherNamedJdbcRepository.deleteAll();
        var voucherList = voucherNamedJdbcRepository.findAll();
        assertThat(voucherList.isEmpty(), is(true));
    }

}
