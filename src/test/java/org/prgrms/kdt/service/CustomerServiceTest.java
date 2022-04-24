package org.prgrms.kdt.service;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.repository.CustomerJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

    EmbeddedMysql embeddedMysql;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    CustomerService customerService;

    Customer newCustomer;

    @BeforeAll
    void clean() {
        newCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        var mysqldConfig = aMysqldConfig(Version.v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order-mgmt", classPathScript("schema.sql"))
                .start();
        customerService.join(newCustomer);
    }

    @Test
    @DisplayName("이메일이 중복된 고객은 추가 할 수 없다.")
    void joinDuplicateCustomer() {
        //given
        Customer newCustomer2 = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        int beforeJoinSize = customerJdbcRepository.findAll().size();
        customerService.join(newCustomer2);

        //when
        int afterJoinSize = customerJdbcRepository.findAll().size();

        //then
        assertThat(afterJoinSize, equalTo(beforeJoinSize));
    }
}