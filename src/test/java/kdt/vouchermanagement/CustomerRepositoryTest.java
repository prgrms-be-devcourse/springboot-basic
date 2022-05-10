package kdt.vouchermanagement;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import kdt.vouchermanagement.domain.customer.domain.Customer;
import kdt.vouchermanagement.domain.customer.dto.CustomerCreateRequest;
import kdt.vouchermanagement.domain.customer.entity.CustomerEntity;
import kdt.vouchermanagement.domain.customer.repository.CustomerRepository;
import kdt.vouchermanagement.domain.voucher.repository.VoucherJdbcRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"kdt.vouchermanagement.domain.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher")
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
    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test","test1234!")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    void customer를_전체_조회할_때__저장된_customer가_없다면_빈_리스트를_반환한다() {
        //given, when
        List<Customer> foundCustomers = customerRepository.findAll();

        //then
        assertThat(foundCustomers.size()).isEqualTo(0);
    }

    @Test
    @Order(2)
    void customer를_저장할_때_전달받은_customer가_null이면_IllegalStateException이_발생한다() {
        //given
        Customer customer = null;

        //when, then
        assertThrows(IllegalStateException.class, () -> customerRepository.save(customer));
    }

    @Test
    @Order(3)
    void customer를_저장할_때_전달받은_customer를_저장한다() {
        //given
        Customer firstCustomer = firstCustomer();
        Customer secondCustomer = secondCustomer();

        //when, then
        assertDoesNotThrow(() -> customerRepository.save(firstCustomer));
        assertDoesNotThrow(() -> customerRepository.save(secondCustomer));
    }

    @Test
    @Order(4)
    void id로_customer를_조회할_때_조회된_customer가_없다면_Optional_empty를_반환한다() {
        //given
        long id = 0L;

        //when
        Optional<Customer> foundCustomer = customerRepository.findById(id);

        //then
        assertThat(foundCustomer).isEqualTo(Optional.empty());
    }

    @Test
    @Order(5)
    void id로_customer를_조회할_때_조회된_customer가_있다면_조회된_customer를_반환한다() {
        //given
        long id = 1L;

        //when
        Optional<Customer> foundCustomer = customerRepository.findById(id);

        //then
        assertThat(foundCustomer).isNotEqualTo(Optional.empty());
        assertThat(foundCustomer.get().getId()).isEqualTo(id);
    }

    @Test
    @Order(6)
    void product를_전체_조회할_때_조회된_product_리스트를_반환한다() {
        //given, when
        List<Customer> foundCustomers = customerRepository.findAll();

        //then
        assertThat(foundCustomers.size()).isEqualTo(2);
        assertThat(foundCustomers.get(0)).usingRecursiveComparison().ignoringFields("id", "createdAt", "updatedAt").isEqualTo(firstCustomer());
        assertThat(foundCustomers.get(1)).usingRecursiveComparison().ignoringFields("id", "createdAt", "updatedAt").isEqualTo(secondCustomer());
    }

    @Test
    @Order(7)
    @DisplayName("product를_삭제할_때_파라미터로_넘어온_id_값으로_삭제한다")
    void deleteVoucherById() {
        //given
        long id = 1L;

        //when, then
        assertDoesNotThrow(() -> customerRepository.deleteById(id));
    }

    private Customer firstCustomer() {
        return Customer.of(new CustomerCreateRequest("first test"));
    }

    private Customer secondCustomer() {
        return Customer.of(new CustomerCreateRequest("second test"));
    }
}
