package org.prgrms.kdtspringdemo.domain.voucher;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerRepository;
import org.prgrms.kdtspringdemo.domain.voucher.data.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.domain.voucher.data.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.repository.VoucherRepository;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = VoucherServiceTest.class)
class VoucherServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceTest.class);

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdtspringdemo.domain.voucher",
                    "org.prgrms.kdtspringdemo.domain.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherService voucherService;

    @Autowired
    DataSource dataSource;

    Voucher newPercentVoucher;
    Voucher newFixedVoucher;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        newFixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 500, UUID.nameUUIDFromBytes("null".getBytes()));
        newPercentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50, UUID.nameUUIDFromBytes("null".getBytes()));

        voucherRepository.insert(newFixedVoucher);
        voucherRepository.insert(newPercentVoucher);

        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();
//    customerJdbcRepository.deleteAll();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("repository 의 모든 voucher 를 보여주는 리스트 체크")
    public void showVoucherListTest () throws Exception{
        // given
        List<Voucher> vouchers = voucherService.showVoucherList();

        // when

        // then
        assertThat(vouchers.get(0), samePropertyValuesAs(newFixedVoucher));
        assertThat(vouchers.get(1), samePropertyValuesAs(newPercentVoucher));

    }

    @Test
    @Order(2)
    @DisplayName("repository 의 모든 voucher 를 보여주는 리스트 체크")
    public void saveVoucherInDBTest () throws Exception{
        // given

        // when
        voucherService.saveVoucherInDB(100, VoucherType.FIXED);
        voucherService.saveVoucherInDB(50, VoucherType.PERCENT);

        // then
        List<Voucher> vouchers = voucherService.showVoucherList();
        assertThat(vouchers.get(2).getAmount(), is(100));
        assertThat(vouchers.get(3), is(50));
    }

    @Test
    @Order(3)
    @DisplayName("repository 의 모든 voucher 를 보여주는 리스트 에러 체크")
    public void saveVoucherInDBErrorTest () throws Exception{
        // given

        // when
        voucherService.saveVoucherInDB(-100, VoucherType.FIXED);
        voucherService.saveVoucherInDB(150, VoucherType.PERCENT);

        // then
        List<Voucher> vouchers = voucherService.showVoucherList();
        assertThat(vouchers.size(), is(4));
    }

    @Test
    @Order(4)
    @DisplayName("repository 의 모든 voucher 개수 count 를 부여주는 테스트")
    public void countTest () throws Exception{
        // given

        // when
        String count = voucherService.count();

        // then
        assertThat(count, is(4));
    }

    @Test
    @Order(5)
    @DisplayName("repository 의 모든 voucher 를 제거하는 delete 테스트")
    public void deleteAllTest () throws Exception{
        // given

        // when
        voucherService.deleteAll();

        // then
        assertThat(voucherService.count(), is(0));
    }

}