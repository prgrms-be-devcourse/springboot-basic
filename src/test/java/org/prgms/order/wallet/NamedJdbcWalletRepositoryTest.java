package org.prgms.order.wallet;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.order.customer.entity.Customer;
import org.prgms.order.customer.repository.CustomerNamedJdbcRepository;
import org.prgms.order.voucher.entity.Voucher;
import org.prgms.order.voucher.entity.VoucherCreateStretage;
import org.prgms.order.voucher.entity.VoucherData;
import org.prgms.order.voucher.entity.VoucherIndexType;
import org.prgms.order.voucher.repository.NamedJdbcVoucherRepository;
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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.Locale;
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
class NamedJdbcWalletRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(NamedJdbcWalletRepositoryTest.class);


    @Configuration
    @ComponentScan(basePackages = {
            "org.prgms.order.wallet",
            "org.prgms.order.customer",
            "org.prgms.order.voucher"})
    static class Config {
        @Bean
        public DataSource dataSource(){
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
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
    CustomerNamedJdbcRepository customerNamedJdbcRepository;

    @Autowired
    VoucherCreateStretage voucherCreateStretage;

    @Autowired
    NamedJdbcWalletRepository namedJdbcWalletRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;

    Voucher newVoucher1;
    Voucher newVoucher2;
    Voucher newVoucher3;
    Voucher newVoucher4;

    Customer newCustomer1;
    Customer newCustomer2;

    Wallet newWallet1;
    Wallet newWallet2;
    Wallet newWallet3;
    Wallet newWallet4;
    Wallet newWallet5;




    @BeforeAll
    void setUp() {
        String inputFixed = "Fixed";
        String inputPercent = "Percent";
        newVoucher1 = voucherCreateStretage.createVoucher(VoucherIndexType.valueOf(inputPercent.toUpperCase(Locale.ROOT)), new VoucherData(UUID.randomUUID(),50, LocalDateTime.now().withNano(0)));
        newVoucher2 =voucherCreateStretage.createVoucher(VoucherIndexType.valueOf(inputFixed.toUpperCase(Locale.ROOT)), new VoucherData(UUID.randomUUID(),5000,LocalDateTime.now().withNano(0)));
        newVoucher3 = voucherCreateStretage.createVoucher(VoucherIndexType.valueOf(inputPercent.toUpperCase(Locale.ROOT)), new VoucherData(UUID.randomUUID(),20,LocalDateTime.now().withNano(0)));
        newVoucher4 = voucherCreateStretage.createVoucher(VoucherIndexType.valueOf(inputFixed.toUpperCase(Locale.ROOT)), new VoucherData(UUID.randomUUID(),100000,LocalDateTime.now().withNano(0)));

        newCustomer1 = new Customer(UUID.randomUUID(), "newCustomer1", "newCustomer1@gmail.com", LocalDateTime.now().withNano(0));
        newCustomer2 = new Customer(UUID.randomUUID(), "newCustomer2", "newCustomer2@gmail.com", LocalDateTime.now().withNano(0));

        newWallet1 = new Wallet(new WalletData(UUID.randomUUID(), newCustomer1.getCustomerId(), newVoucher1.getVoucherId(), LocalDateTime.now().withNano(0)));
        newWallet2 = new Wallet(new WalletData(UUID.randomUUID(), newCustomer1.getCustomerId(), newVoucher2.getVoucherId(), LocalDateTime.now().withNano(0)));
        newWallet3 = new Wallet(new WalletData(UUID.randomUUID(), newCustomer2.getCustomerId(), newVoucher1.getVoucherId(), LocalDateTime.now().withNano(0)));
        newWallet4 = new Wallet(new WalletData(UUID.randomUUID(), newCustomer2.getCustomerId(), newVoucher3.getVoucherId(), LocalDateTime.now().withNano(0)));
        newWallet5 = new Wallet(new WalletData(UUID.randomUUID(), newCustomer2.getCustomerId(), newVoucher4.getVoucherId(), LocalDateTime.now().withNano(0)));

        var mysqldConfig = aMysqldConfig(v5_7_10)
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
        namedJdbcVoucherRepository.insert(newVoucher3);
        namedJdbcVoucherRepository.insert(newVoucher4);

        customerNamedJdbcRepository.insert(newCustomer1);
        customerNamedJdbcRepository.insert(newCustomer2);


        namedJdbcWalletRepository.insert(newWallet3);
        namedJdbcWalletRepository.insert(newWallet4);
        namedJdbcWalletRepository.insert(newWallet5);

    }

    @Test
    @Order(1)
    @DisplayName("Hikari Connectionpool test")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }


    @Test
    @Order(2)
    @DisplayName("wallet을 추가할 수 있다.")
    void testInsert() {
        var insertWallet = namedJdbcWalletRepository.insert(newWallet1);
        assertThat(insertWallet,samePropertyValuesAs(newWallet1));
    }

    @Test
    @Order(3)
    @DisplayName("wallet_id로 wallet을 찾을 수 있다.")
    void testFindById() {
        //given
        namedJdbcWalletRepository.insert(newWallet2);
        //when
        var insertWallet = namedJdbcWalletRepository.findById(newWallet2.getWalletId());
        //then
        assertThat(insertWallet.get().getWalletId(),is(newWallet2.getWalletId()));
    }

    @Test
    @Order(4)
    @DisplayName("custom_id로 wallet을 찾을 수 있다.")
    void testFindByCustomerId() {
        var testList = namedJdbcWalletRepository.findByCustomerId(newCustomer1.getCustomerId());
        assertThat(testList.size(),is(2));

    }

    @Test
    @Order(5)
    @DisplayName("VoucherId로 wallet을 찾을 수 있다.")
    void testFindByVoucherId() {
        var testList = namedJdbcWalletRepository.findByVoucherId(newVoucher1.getVoucherId());
        assertThat(testList.size(),is(2));
    }


    @Test
    @Order(6)
    @DisplayName("wallet을 사용상태로 만들 수 있다.")
    void testUseVoucher() {
        var inputwallet = new Wallet(new WalletData(newWallet3.getWalletId(), true));
        namedJdbcWalletRepository.useVoucher(inputwallet.getWalletId());
        var testList = namedJdbcWalletRepository.findById(newWallet3.getWalletId()).get();
        assertThat(testList.getUsed(),notNullValue());
    }

    @Test
    @Order(7)
    @DisplayName("사용 가능한 wallet을 찾을 수 있다.")
    void testFindByCustomerAvailable() {
        var testList = namedJdbcWalletRepository.findByCustomerAvailable(newCustomer2.getCustomerId());
        assertThat(testList.size(),is(2));
    }


    @Test
    @Order(7)
    @DisplayName("wallet_id로 wallet을 삭제할 수 있다.")
    void testDeleteByWalletId() {
        namedJdbcWalletRepository.deleteByWalletId(newWallet3.getWalletId());

        var test = namedJdbcWalletRepository.findByCustomerId(newCustomer2.getCustomerId());
        assertThat(test.size(),is(2));

    }

    @Test
    @Order(8)
    @DisplayName("CustomerId로 wallet을 삭제할 수 있다.")
    void testDeleteByCustomerId() {
        namedJdbcWalletRepository.deleteByCustomerId(newCustomer1.getCustomerId());

        var test = namedJdbcWalletRepository.findByCustomerId(newCustomer1.getCustomerId());
        assertThat(test.size(),is(0));
    }

    @Test
    @Order(9)
    @DisplayName("VoucherId로 wallet을 삭제할 수 있다.")
    void testDeleteByVoucherId() {
        namedJdbcWalletRepository.insert(newWallet3);
        namedJdbcWalletRepository.deleteByVoucherId(newVoucher1.getVoucherId());

        var test = namedJdbcWalletRepository.findByVoucherId(newVoucher1.getVoucherId());
        assertThat(test.size(),is(0));
    }

    @Test
    @Order(10)
    @DisplayName("모든 wallet을 삭제할 수 있다.")
    void deleteAll() {
        namedJdbcWalletRepository.deleteAll();
        var test = namedJdbcWalletRepository.findAll();
        assertThat(test.size(),is(0));
    }
}