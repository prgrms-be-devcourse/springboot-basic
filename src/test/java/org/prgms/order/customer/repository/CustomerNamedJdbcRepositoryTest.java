package org.prgms.order.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.order.customer.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_10;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerNamedJdbcRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepositoryTest.class);


    @Configuration
    @ComponentScan(basePackages = {"org.prgms.order.customer"})
    static class Config {
        @Bean
        public DataSource dataSource(){
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt") //test용
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
    CustomerNamedJdbcRepository customerNamedJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer testUpdate;
    Customer testBlackList;
    Customer newCustomer3;
    Customer notCustomer;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        testUpdate = new Customer(UUID.randomUUID(), "test-user1@gmail.com", "test-user1", LocalDateTime.now().withNano(0));
        testBlackList = new Customer(UUID.randomUUID(), "test-user2@gmail.com", "test-user2", LocalDateTime.now().withNano(0));
        newCustomer3 = new Customer(UUID.randomUUID(), "test-user3@gmail.com", "test-user3", LocalDateTime.now().withNano(0));
        notCustomer = new Customer(UUID.randomUUID(), "test-user4@gmail.com", "test-user4", LocalDateTime.now().withNano(0));
        var mysqldConfig = aMysqldConfig(v5_7_10) //port, characterset을 지정해 줄 수 있음
                .withCharset(UTF8)
                .withPort(2215) //임의의 포트 지정
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();

        customerNamedJdbcRepository.insert(testBlackList);
        customerNamedJdbcRepository.insert(newCustomer3);

    }

    @AfterAll
    void cleanUp(){
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("Hikari Connectionpool test")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() {
        try {
            customerNamedJdbcRepository.insert(testUpdate);
        } catch (BadSqlGrammarException e) {
            logger.error("Got BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode());

            logger.info("customId -> {} email -> {} createdAt ->{}", testUpdate.getCustomerId(), testUpdate.getEmail(), testUpdate.getCreatedAt());

            var retrievedCustomer = customerNamedJdbcRepository.findById(testUpdate.getCustomerId());
            logger.info("customId -> {} email -> {} createdAt ->{}", retrievedCustomer.get().getCustomerId(), retrievedCustomer.get().getEmail(), retrievedCustomer.get().getCreatedAt());
            assertThat(retrievedCustomer.isEmpty(), is(false));
            assertThat(retrievedCustomer.get(), samePropertyValuesAs(testUpdate));//프로퍼티 value들이 같은지 비교
        }
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() {
        var customers = customerNamedJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
        assertThat(customers,hasSize(3));

    }

    @Test
    @Order(4)
    @DisplayName("아이디로 고객을 조회할 수 있다.")
    public void testFindById() {
        var customer = customerNamedJdbcRepository.findById(testUpdate.getCustomerId());
        assertThat(customer.isEmpty(), is(false));

        var unknown = customerNamedJdbcRepository.findById(notCustomer.getCustomerId());
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindByName() {
        var customer = customerNamedJdbcRepository.findByName(testUpdate.getName());
        assertThat(customer.isEmpty(), is(false));

        var unknown = customerNamedJdbcRepository.findByName(notCustomer.getName());
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("이메일로로 고객 조회할 수 있다.")
    public void testFindByEmail() {
        var customer = customerNamedJdbcRepository.findByEmail(testUpdate.getEmail());
        assertThat(customer.isEmpty(), is(false));

        var unknown = customerNamedJdbcRepository.findByEmail(notCustomer.getEmail());
        assertThat(unknown.isEmpty(), is(true));
    }



    @Test
    @Order(7)
    @DisplayName("고객 수를 조회할 수 있다.")
    void count() {
        var customerCount = customerNamedJdbcRepository.count();
        assertThat(customerCount, is(3));
    }

    @Test
    @Order(8)
    @DisplayName("블랙리스트 고객 목록을 조회할 수 있다.")
    void findBlackList() {
        var blackList = customerNamedJdbcRepository.findBlackList();
        assertThat(blackList, hasSize(0));
    }


    @Test
    @Order(9)
    @DisplayName("블랙리스트로 고객을 등록할 수 있다.")
    void registerBlackListById() {
        customerNamedJdbcRepository.registerBlackListById(testBlackList.getCustomerId());
        var blackList = customerNamedJdbcRepository.findBlackList();
        assertThat(blackList, hasSize(1));
    }

    @Test
    @Order(10)
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() {
        testUpdate.changeName("updated-user");
        customerNamedJdbcRepository.update(testUpdate);

        Optional<Customer> retrievedCustomer = customerNamedJdbcRepository.findById(testUpdate.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(),samePropertyValuesAs(testUpdate));
    }

    @Test
    @Order(11)
    @DisplayName("고객을 모두 삭제할 수 있다.")
    void deleteAll() {
        customerNamedJdbcRepository.deleteAll();
        var customers = customerNamedJdbcRepository.findAll();
        assertThat(customers, hasSize(0));
    }

}