package org.prgrms.kdt.shop.repository;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.shop.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CustomerJdbcRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    EmbeddedMysql embeddedMysql;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public DataSource dataSource( ) {
            return DataSourceBuilder.create().url("jdbc:mysql://localhost:2215/order_mgmt").username("test").password("test1234!").build();
        }

        @Bean
        CustomerRepository customerRepository( ) {
            return new CustomerJdbcRepository(dataSource());
        }
    }

    @BeforeAll
    void setup( ) {
        var mysqlConfig = aMysqldConfig(v8_0_11).withCharset(UTF8).withPort(2215).withUser("test", "test1234!").withTimeZone("Asia/Seoul").build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig).addSchema("order_mgmt", classPathScript("schema.sql")).start();
    }

    @AfterAll
    void cleanup( ) {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("입력 테스트")
    void insert( ) {
        //given
        //when
        customerRepository.insert(new Customer(UUID.randomUUID(), "hunki", "gnsrl76@naver.com", LocalDateTime.now()));
        //then
        assertThat(customerRepository.findAll().isEmpty(), is(false));
    }

    @Test
    @DisplayName("모든 항목 검색 테스트")
    void findAll( ) {
        //given
        customerRepository.insert(new Customer(UUID.randomUUID(), "junki", "wnsrl76@naver.com", LocalDateTime.now()));
        //when
        var findList = customerRepository.findAll();
        //then
        assertThat(findList.size(), is(1));
    }

    @Test
    @DisplayName("ID를 이용한 검색 테스트")
    void findById( ) {
        //given
        UUID uuid = UUID.randomUUID();
        //when
        var insertCustomer = customerRepository.insert(new Customer(uuid, "hunki", "gnsrl76@naver.com", LocalDateTime.now()));
        var findCustomer = customerRepository.findById(uuid);
        //then
        assertThat(findCustomer.get().getCustomerId(), is(insertCustomer.getCustomerId()));
        assertThat(findCustomer.get().getEmail(), is(insertCustomer.getEmail()));
        assertThat(findCustomer.get().getName(), is(insertCustomer.getName()));
        assertThat(findCustomer.get().getLastLoginAt(), is(insertCustomer.getLastLoginAt()));
        //given
        //when
        customerRepository.deleteAll();
        //then
        assertThat(customerRepository.findById(UUID.randomUUID()), is(Optional.empty()));
    }

    @Test
    @DisplayName("수정 테스트")
    void update( ) {
        //given
        var uuid = UUID.randomUUID();
        var insertCustomer = customerRepository.insert(new Customer(uuid, "hunki", "gnsrl76@naver.com", LocalDateTime.now()));
        var updateCustomer = new Customer(uuid, "junki", "wnsrl76@naver.com", LocalDateTime.now());
        //when
        customerRepository.update(updateCustomer);
        //then
        assertThat(updateCustomer.getName(), is(customerRepository.findById(insertCustomer.getCustomerId()).get().getName()));
        assertThat(updateCustomer.getEmail(), is(customerRepository.findById(insertCustomer.getCustomerId()).get().getEmail()));
    }

    @Test
    @DisplayName("특정 항목 삭제 테스트")
    void deleteTest( ) {
        //given
        var uuid = UUID.randomUUID();

        customerRepository.insert(new Customer(uuid, "junki", "wnsrl76@naver.com", LocalDateTime.now()));
        //when
        customerRepository.delete(uuid);
        //then
        assertThat(customerRepository.findAll().isEmpty(), is(true));
    }

    @Test
    @DisplayName("이름으로 검색 테스트")
    void findByName( ) {
        //given
        var customer = customerRepository.insert(new Customer(UUID.randomUUID(), "junki", "gnsrl76@naver.com", LocalDateTime.now()));
        //when
        var findName = customerRepository.findByName(customer.getName());
        //then
        assertThat(findName.get().getName(), is(customer.getName()));

        //given
        var customer2 = customerRepository.insert(new Customer(UUID.randomUUID(), "junki", "wnsrl76@naver.com", LocalDateTime.now()));
        //then
        try {
            customerRepository.findByName(customer2.getName());
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    @DisplayName("이메일로 검색 테스트")
    void findByEmail( ) {
        //given
        var customer = customerRepository.insert(new Customer(UUID.randomUUID(), "junki", "gnsrl76@naver.com", LocalDateTime.now()));
        //when
        var findName = customerRepository.findByEmail(customer.getEmail());
        //then
        assertThat(findName.get().getEmail(), is(customer.getEmail()));
    }

    @Test
    @DisplayName("count 테스트")
    void countTest( ) {
        //given
        var customer = customerRepository.insert(new Customer(UUID.randomUUID(), "funki", "funki76@naver.com", LocalDateTime.now()));
        //when
        var count = customerRepository.count();
        //then
        assertThat(count, is(1));
    }

    @Test
    @DisplayName("모든 항목 삭제 테스트")
    void deleteAll( ) {
        //when
        customerRepository.deleteAll();
        //then
        assertThat(customerRepository.findAll().isEmpty(), is(true));
    }
}