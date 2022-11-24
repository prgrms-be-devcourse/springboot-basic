package org.programmers.springbootbasic.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.domain.voucher.repository.JdbcVoucherRepository;
import org.programmers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
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
import static org.hamcrest.Matchers.is;
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("local")
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.programmers.springbootbasic.domain.customer.repository"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {

            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/spring_basic")
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

        @Bean
        public VoucherRepository voucherRepository() {
            return new JdbcVoucherRepository(jdbcTemplate(dataSource()));
        }
    }

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private DataSource dataSource;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        this.embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("spring_basic", classPathScript("schema.sql"))
                .start();
    }

    @BeforeEach
    void beforeEach() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("Voucher가 주어졌을 때 삽입한 결과가 그 전 조회 시의 row 수보다 1 증가해야 한다.")
    public void Voucher_삽입_테스트() throws Exception {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        List<Voucher> retrievedAllFirst = voucherRepository.findAll();
        int beforeSize = retrievedAllFirst.size();
        // when
        voucherRepository.save(fixedAmountVoucher);
        //then
        List<Voucher> retrievedAllLast = voucherRepository.findAll();
        int afterSize = retrievedAllLast.size();
        assertThat(afterSize, is(beforeSize + 1));
    }

    @Test
    @DisplayName("특정 ID에 맞는 Voucher가 삭제되어야 한다.")
    public void Voucher_삭제_테스트() throws Exception {
        //given
        UUID selectedID = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(selectedID, 10);
        voucherRepository.save(fixedAmountVoucher);

        // when
        voucherRepository.deleteById(selectedID);
        //then
        assertThat(voucherRepository.findAll().size(), is(0));
    }

    @Test
    @DisplayName("특정 ID에 맞는 Voucher가 수정되어야 한다.")
    public void Voucher_수정_테스트() throws Exception {
        //given
        UUID selectedID = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(selectedID, 10);
        voucherRepository.save(fixedAmountVoucher);
        // when
        PercentDiscountVoucher updatedpercentDiscountVoucher = new PercentDiscountVoucher(selectedID, 33);
        voucherRepository.update(updatedpercentDiscountVoucher);

        //then
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(selectedID);
        assertThat(retrievedVoucher.get().getVoucherType(), is(VoucherType.PERCENT));
        assertThat(retrievedVoucher.get().getAmount(), is(33L));
    }
}