package org.prgrms.kdt.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("database")
class JdbcVoucherRepositoryTest {
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.voucher.repository"}
    )
    static class Config{
        @Bean
        public DataSource dataSource(){
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
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
    }

    @Autowired
    VoucherRepository voucherRepository;

    Voucher newVoucher;

    EmbeddedMysql embeddedMysql;

    Logger logger = LoggerFactory.getLogger(JdbcVoucherRepositoryTest.class);

    @BeforeAll
    void setUp(){
        newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_mgmt", classPathScript("create_voucher_table.sql"))
                .start();
    }

    @AfterEach
    void afterEach(){
        voucherRepository.clearAllVouchers();
    }

    @Test
    @DisplayName("Voucher를 등록한다.")
    void testInsert(){
        //given //when
        Voucher insertResult = voucherRepository.insert(newVoucher);

        //then
        assertThat(insertResult, samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("기존에 등록된 Voucher와 동일한 Id를 갖는 Voucher를 등록하면 오류가 발생한다.")
    void testInsertingDuplicatedVoucher(){
        //given
        Voucher insertResult = voucherRepository.insert(newVoucher);
        Voucher duplicatedVoucher = new FixedAmountVoucher(insertResult.getVoucherId(), 100);

        //when
        assertThrows(DataAccessException.class, () -> voucherRepository.insert(duplicatedVoucher));

        //then
        assertThat(voucherRepository.findById(insertResult.getVoucherId()).get(), samePropertyValuesAs(newVoucher));
        assertThat(voucherRepository.findAllVouchers().size(), is(1));
    }

    @Test
    @DisplayName("id에 해당하는 Voucher 객체를 반환한다.")
    void testFindById(){
        //given
        voucherRepository.insert(newVoucher);

        //when
        Optional<Voucher> findResult = voucherRepository.findById(newVoucher.getVoucherId());

        //then
        assertThat(findResult.isEmpty(), is(false));
        assertThat(findResult.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("존재하지 않는 id에 대한 Voucher를 찾는다면 Empty 값을 반환한다.")
    void testFindByIdNotExist(){
        //given //when
        Optional<Voucher> findResult = voucherRepository.findById(newVoucher.getVoucherId());

        //then
        assertThat(findResult.isEmpty(), is(true));
    }

    @Test
    @DisplayName("등록된 모든 Voucher를 반환한다.")
    void testFindAllVouchers(){
        //given
        voucherRepository.insert(newVoucher);

        PercentDiscountVoucher newPercentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        voucherRepository.insert(newPercentVoucher);

        //when
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();

        //then
        assertThat(allVouchers.size(), is(2));
        assertThat(allVouchers, hasItem(samePropertyValuesAs(newVoucher)));
        assertThat(allVouchers, hasItem(samePropertyValuesAs(newPercentVoucher)));

    }

    @Test
    @DisplayName("모든 Voucher를 삭제한다.")
    void testClearAllVouchers(){
        //given
        voucherRepository.insert(newVoucher);

        //when
        voucherRepository.clearAllVouchers();

        //then
        assertThat(voucherRepository.findAllVouchers().size(), is(0));
    }
}