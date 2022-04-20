package org.prgrms.kdt.domain.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.repository.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt"}
    )
    static class config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test")
                    .username("park")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

    }

    @Autowired
    JdbcCustomerRepository customerRepository;

    @Autowired
    JdbcVoucherRepository voucherRepository;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("park", "1234")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test", classPathScript("schema.sql"))
                .start();
    }

    @BeforeEach
    void setUp() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @AfterAll
    void stopDatabase() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("고객이 정상적으로 저장된다.")
    public void save() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        //when
        UUID savedId = customerRepository.save(customer);
        //then
        assertThat(savedId).isEqualTo(customerId);
    }

    @Test
    @DisplayName("저장된 고객들을 조회할 수 있다.")
    public void findAll() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        //when
        List<Customer> customers = customerRepository.findAll();
        //then
        assertThat(customers.size()).isEqualTo(1);
        assertThat(customers.get(0)).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("고객 ID를 통해 고객을 조회할 수 있다.")
    public void findById() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        //when
        Optional<Customer> findCustomer = customerRepository.findById(customerId);
        //then
        assertThat(findCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("email을 통해 고객을 조회할 수 있다.")
    public void findByEmail() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        String email = "dbslzld15@naver.com";
        Customer customer = new Customer(customerId,"park" , email, CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        //when
        Optional<Customer> findCustomer = customerRepository.findByEmail(email);
        //then
        assertThat(findCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("바우처 ID를 통해 해당 바우처를 가진 고객을 조회할 수 있다.")
    public void findByVoucherId() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "park", "d@naver.com", now, now);
        customerRepository.save(customer);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, customerId, now, now);
        voucherRepository.save(voucher);
        //when
        Optional<Customer> findCustomer = customerRepository.findByVoucherId(voucherId);
        //then
        assertThat(findCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("설정한 고객 타입에 해당하는 고객들을 조회할 수 있다.")
    public void findByCustomerType() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        //when
        List<Customer> customers = customerRepository.findByCustomerType(CustomerType.BLACK_LIST);
        //then
        assertThat(customers.size()).isEqualTo(1);
        assertThat(customers.get(0)).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("고객 ID를 통해 고객에 대한 정보를 업데이트 할 수 있다.")
    public void updateById() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        Customer updateCustomer = new Customer(customerId, "kim", "a@naver.com", CustomerType.NORMAL, LocalDateTime.now(), LocalDateTime.now());
        //when
        int updatedRows = customerRepository.updateById(updateCustomer);
        //then
        assertThat(updatedRows).isEqualTo(1);
    }

    @Test
    @DisplayName("고객 ID를 통해 고객을 삭제할 수 있다.")
    public void deleteById() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        //when
        int deletedRows = customerRepository.deleteById(customerId);
        //then
        assertThat(deletedRows).isEqualTo(1);
    }

    @Test
    @DisplayName("고객 테이블에 저장된 모든 데이터를 삭제할 수 있다.")
    public void deleteAll() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        //when
        int deletedRows = customerRepository.deleteAll();
        //then
        assertThat(deletedRows).isEqualTo(1);
    }
}