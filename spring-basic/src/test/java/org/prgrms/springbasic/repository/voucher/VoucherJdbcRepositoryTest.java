package org.prgrms.springbasic.repository.voucher;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.repository.customer.CustomerJdbcRepository;
import org.prgrms.springbasic.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.NoSuchElementException;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static java.util.UUID.randomUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.prgrms.springbasic.domain.customer.Customer.normalCustomer;
import static org.prgrms.springbasic.domain.voucher.Voucher.fixedVoucher;
import static org.prgrms.springbasic.domain.voucher.VoucherType.FIXED;
import static org.prgrms.springbasic.domain.voucher.VoucherType.PERCENT;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
class VoucherJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.springbasic.repository"}
    )
    static class TestConfig {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("test1234")
                    .type(HikariDataSource.class)
                    .build();
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
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new CustomerJdbcRepository(jdbcTemplate);
        }

        @Bean
        public VoucherRepository voucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new VoucherJdbcRepository(jdbcTemplate);
        }
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    EmbeddedMysql embeddedMysql;

    Customer customer = normalCustomer(randomUUID(), "new-user");
    Voucher voucher = fixedVoucher(randomUUID(), 10, null);

    @BeforeAll
    void setUp() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql")).start();

        voucherRepository.deleteVouchers();
        customerRepository.deleteCustomers();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("바우처를 저장하면 반환되는 객체는 저장한 바우처과 같아야 하고 레포지토리의 사이즈는 1이 되어야 한다.")
    void testSave() {
        var newVoucher = voucherRepository.save(voucher);

        assertThat(newVoucher, notNullValue());
        assertThat(newVoucher, samePropertyValuesAs(voucher));
        assertThat(voucherRepository.countData(), is(1));
    }

    @Test
    @Order(2)
    @DisplayName("중복되는 아이디를 가진 바우처를 저장할 경우 예외가 발생한다.")
    void testDuplicatedVoucher() {
        assertThrows(DataAccessException.class, () -> voucherRepository.save(voucher));
    }

    @Test
    @Order(3)
    @DisplayName("바우처 아이디로 바우처를 조회할 수 있다.")
    void testFindByVoucherId() {
        var retrievedVoucher = voucherRepository.findByVoucherId(voucher.getVoucherId());

        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @Order(4)
    @DisplayName("고객이 어떤 바우처를 보유하고 있는지 조회할 수 있다.")
    void testFindByCustomerId() {
        customerRepository.save(customer);

        var percentVoucher = Voucher.percentVoucher(randomUUID(), 10, customer.getCustomerId());

        voucherRepository.save(percentVoucher);

        var retrievedVoucher = voucherRepository.findByCustomerId(customer.getCustomerId());

        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(percentVoucher));
    }

    @Test
    @Order(5)
    @DisplayName("바우처 아이디로 바우처를 찾지 못하는 경우 빈 객체가 반환되어야 한다.")
    void testFindByIdException() {
        var retrievedVoucher = voucherRepository.findByVoucherId(randomUUID());

        assertThat(retrievedVoucher.isEmpty(), is(true));
        assertThrows(NoSuchElementException.class, retrievedVoucher::get);
    }

    @Test
    @Order(6)
    @DisplayName("모든 바우처을 조회했을 때 저장한 바우처 객체를 가지고 있어야 하며 사이즈가 같아야 한다.")
    void testFindAll() {
        Voucher newVoucher1 = fixedVoucher(randomUUID(), 10, null);
        Voucher newVoucher2 = Voucher.percentVoucher(randomUUID(), 10, null);
        Voucher newVoucher3 = fixedVoucher(randomUUID(), 10, null);
        Voucher newVoucher4 = Voucher.percentVoucher(randomUUID(), 10, null);
        Voucher newVoucher5 = fixedVoucher(randomUUID(), 10, null);

        voucherRepository.save(newVoucher1);
        voucherRepository.save(newVoucher2);
        voucherRepository.save(newVoucher3);
        voucherRepository.save(newVoucher4);
        voucherRepository.save(newVoucher5);

        var vouchers = voucherRepository.findVouchers();

        assertThat(vouchers.size(), is(voucherRepository.countData()));
        assertThat(vouchers.get(0), samePropertyValuesAs(voucher));
        assertThat(vouchers, containsInRelativeOrder(samePropertyValuesAs(voucher), samePropertyValuesAs(newVoucher1), samePropertyValuesAs(newVoucher2), samePropertyValuesAs(newVoucher3), samePropertyValuesAs(newVoucher4), samePropertyValuesAs(newVoucher5)));
    }

    @Test
    @Order(7)
    @DisplayName("지갑 테스트: 특정 고객에게 바우처를 할당할 수 있다.")
    void testFindWallet() {
        voucherRepository.deleteVouchers();
        customerRepository.deleteCustomers();

        var customer1 = normalCustomer(randomUUID(), "voucher-user");
        var voucher1 = fixedVoucher(randomUUID(), 10, customer1.getCustomerId());

        var customer2 = normalCustomer(randomUUID(), "no-voucher-user");
        var voucher2 = fixedVoucher(randomUUID(), 10, null);

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        var wallets = voucherRepository.findWallets();
        var wallet = wallets.get(0);

        assertThat(wallets.size(), is(1));
        assertThat(wallet.getCustomerId(), is(customer1.getCustomerId()));
        assertThat(wallet.getVoucherId(), is(voucher1.getVoucherId()));
    }

    @Test
    @Order(8)
    @DisplayName("입력된 바우처 정보대로 업데이트가 잘 되어야 한다.")
    void testUpdateVoucher() throws InterruptedException {
        var newVoucher = fixedVoucher(randomUUID(), 10L, null);
        voucherRepository.save(newVoucher);

        Thread.sleep(1000);

        var updatedVoucher = newVoucher.update(PERCENT, 20L);
        var retrievedVoucher = voucherRepository.update(updatedVoucher);

        assertThat(retrievedVoucher, notNullValue());
        assertThat(retrievedVoucher.getVoucherId(), is(newVoucher.getVoucherId()));
        assertThat(retrievedVoucher.getVoucherType(), not(is(FIXED)));
        assertThat(retrievedVoucher.getDiscountInfo(), is(20L));
        assertThat(retrievedVoucher.getModifiedAt(), notNullValue());
    }

    @Test
    @Order(9)
    @DisplayName("바우처 아이디로 바우처를 삭제할 수 있다.")
    void testDeleteByVoucherId() {
        var newVoucher = fixedVoucher(randomUUID(), 10L, null);
        var savedVoucher = voucherRepository.save(newVoucher);

        var retrievedVoucher = voucherRepository.findByVoucherId(savedVoucher.getVoucherId());

        assertThat(retrievedVoucher.isEmpty(), is(false));

        voucherRepository.deleteByVoucherId(savedVoucher.getVoucherId());

        var deletedVoucher = voucherRepository.findByVoucherId(newVoucher.getVoucherId());

        assertThat(deletedVoucher.isEmpty(), is(true));
    }

    @Test
    @Order(10)
    @DisplayName("특정 회원의 바우처를 삭제할 수 있다.")
    void testDeleteByCustomerId() {
        var customer = normalCustomer(randomUUID(), "voucher-user");
        var voucher = fixedVoucher(randomUUID(), 10, customer.getCustomerId());

        voucherRepository.deleteByCustomerId(customer.getCustomerId());

        var retrievedVoucher = voucherRepository.findByVoucherId(voucher.getVoucherId());

        assertThat(retrievedVoucher.isEmpty(), is(true));
    }

    @Test
    @Order(11)
    @DisplayName("모든 바우처을 삭제하고 나면 레객포지토리의 사이즈는 0이 되어야한다.")
    void testDeleteAll() {
        voucherRepository.save(fixedVoucher(randomUUID(), 10, null));
        voucherRepository.save(fixedVoucher(randomUUID(), 10, null));
        voucherRepository.save(fixedVoucher(randomUUID(), 10, null));

        assertThat(voucherRepository.countData(), greaterThan(0));

        voucherRepository.deleteVouchers();

        assertThat(voucherRepository.findVouchers().isEmpty(), is(true));
        assertThat(voucherRepository.countData(), is(0));
    }

}