package com.prgms.management.repository;

import com.prgms.management.common.exception.FindFailException;
import com.prgms.management.common.exception.InvalidParameterException;
import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.model.CustomerType;
import com.prgms.management.customer.repository.CustomerRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@ActiveProfiles("default")
@DisplayName("JdbcCustomerRepository 유닛 테스트")
class JdbcCustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;
    List<Customer> customers = new ArrayList<>();

    @BeforeAll
    void setUp() {
        MysqldConfig config = aMysqldConfig(v5_7_latest)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "1234")
            .withTimeZone("Asia/Seoul")
            .withTimeout(2, TimeUnit.MINUTES)
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("demo", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Configuration
    @ComponentScan(basePackages = {"com.prgms.management.customer"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/demo")
                .username("test")
                .password("1234")
                .type(HikariDataSource.class)
                .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @DisplayName("save() : 고객 저장 테스트")
    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class SaveTest {
        @DisplayName("성공 : WHITE/BLACK 타입으로 고객 정보가 지정된 경우 저장에 성공합니다.")
        @ParameterizedTest(name = "{0} 고객 정보 저장 성공")
        @CsvSource({"kate, WHITE, kate@gmail.com", "jade, WHITE, jade@gmail.com", "sage, BLACK, sage@gmail.com",
            "haes, BLACK, haes@gmail.com"})
        void saveSuccess(String name, String typeStr, String email) {
            Customer newCustomer = new Customer(name, CustomerType.valueOf(typeStr), email);
            customers.add(customerRepository.save(newCustomer));

            var retrievedCustomer = customerRepository.findById(newCustomer.getId());
            assertThat(retrievedCustomer, not(nullValue()));
            assertThat(retrievedCustomer, equalTo(newCustomer));
        }

        @DisplayName("실패 : NONE 타입으로 고객 정보가 지정된 경우 InvalidParameterException 예외가 발생합니다.")
        @Test
        void saveFail() {
            assertThrows(InvalidParameterException.class, () -> {
                Customer newCustomer = new Customer("test", CustomerType.NONE, "test@test.co");
                customers.add(customerRepository.save(newCustomer));
            });
        }
    }

    @DisplayName("findById() : ID로 고객 조회 테스트")
    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class FindByIdTest {
        @DisplayName("성공 : 존재하는 ID로 조회하는 경우 알맞은 고객을 반환합니다.")
        @Test
        void findSuccess() {
            for (Customer customer : customers) {
                var resultCustomer = customerRepository.findById(customer.getId());
                assertThat(resultCustomer, equalTo(customer));
            }
        }

        @DisplayName("실패 : 존재하지 않는 ID로 조회하는 경우 null이 반환됩니다.")
        @Test
        void findFail() {
            assertThrows(FindFailException.class, () -> customerRepository.findById(UUID.randomUUID()));
        }
    }

    @DisplayName("findByEmail() : 이메일로 고객 조회 테스트")
    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class FindByEmailTest {
        @DisplayName("성공 : 존재하는 이메일로 조회하는 경우 알맞은 고객을 반환합니다.")
        @Test
        void findSuccess() {
            for (Customer customer : customers) {
                var resultCustomer = customerRepository.findByEmail(customer.getEmail());
                assertThat(resultCustomer, equalTo(customer));
            }
        }

        @DisplayName("실패 : 존재하지 않는 이메일로 조회하는 경우 null이 반환됩니다.")
        @Test
        void findFail() {
            assertThrows(FindFailException.class, () -> customerRepository.findByEmail("fake@email"));
        }
    }

    @DisplayName("findAll() : 전체 고객 목록 조회 테스트")
    @Nested
    @Order(3)
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class FindAllTest {
        @DisplayName("성공 : 한 개 이상의 고객 정보가 저장되어 있는 경우 전체 고객 리스트가 반환됩니다.")
        @Test
        void findSuccess() {
            var resultCustomers = customerRepository.findAll();
            assertThat(resultCustomers.isEmpty(), is(false));
            assertThat(resultCustomers, hasSize(customers.size()));
        }
    }

    @DisplayName("findByType() : 타입으로 고객 목록 조회 테스트")
    @Nested
    @Order(3)
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class FindByTypeTest {
        @DisplayName("성공 : WHITE/BLACK 타입으로 조회하는 경우 해당 타입을 지닌 고객 리스트가 반환됩니다.")
        @ParameterizedTest(name = "{0} 타입의 고객 목록 조회 성공")
        @CsvSource({"WHITE, 2", "BLACK, 2"})
        void findSuccess(String typeStr, int count) {
            var resultCustomers = customerRepository.findByType(CustomerType.valueOf(typeStr));
            assertThat(resultCustomers.isEmpty(), is(false));
            assertThat(resultCustomers, hasSize(count));
        }

        @DisplayName("실패 : NONE 타입으로 조회하는 경우 빈 리스트가 반환됩니다.")
        @Test
        void findFail() {
            var resultCustomers = customerRepository.findByType(CustomerType.NONE);
            assertThat(resultCustomers.isEmpty(), is(true));
        }
    }
}