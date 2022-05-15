package com.dojinyou.devcourse.voucherapplication.customer;

import com.dojinyou.devcourse.voucherapplication.common.exception.NotFoundException;
import com.dojinyou.devcourse.voucherapplication.customer.domain.Customer;
import com.dojinyou.devcourse.voucherapplication.customer.dto.CustomerCreateRequest;
import com.dojinyou.devcourse.voucherapplication.customer.entity.CustomerEntity;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


@EnableAutoConfiguration
@SpringBootTest(classes = {CustomerJdbcRepository.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class CustomerJdbcRepositoryTest {

    @Autowired
    CustomerJdbcRepository customerRepository;

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig mysqlConfig = aMysqldConfig(Version.v5_7_10)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "testmysql")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("spring-boot-mission", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Nested
    @DisplayName("생성 함수 호출 시")
    @Order(1)
    class 생성_함수_호출_시 {
        @Nested
        @DisplayName("정상적인 입력이 들어온다면,")
        class 정상적인_입력이_들어온다면 {

            @Test
            @DisplayName("id를 가진 고객 데이터를 반환한다")
            void id를_가진_고객_데이터를_반환한다() {
                // given
                String customerName = "testName1";
                Customer newCustomer = Customer.from(new CustomerCreateRequest(customerName));

                // when
                Customer savedCustomer = customerRepository.create(newCustomer);

                // then
                assertThat(savedCustomer).isNotNull();
                assertThat(savedCustomer.getName()).isEqualTo(newCustomer.getName());
                assertThat(savedCustomer.getId()).isNotEqualTo(0);

            }
        }
    }


    @Nested
    @Order(2)
    @DisplayName("전체 조회 함수 호출 시")
    class 전체_조회_함수_호출_시 {
        @Nested
        @DisplayName("정상적으로 호출된다면,")
        class 정상적으로_호출된다면 {

            @Test
            @DisplayName("고객리스트를 반환한다")
            void 고객리스트를_반환한다() {
                // given

                // when
                List<Customer> customers = customerRepository.findAll();

                // then
                assertThat(customers).isNotNull();
                assertThat(customers.size()).isEqualTo(1);
            }
        }
    }

    @Nested
    @Order(2)
    @DisplayName("id를 이용한 고객 조회 시")
    class id를_이용한_고객_조회_시 {
        @Nested
        @DisplayName("해당 id를 가진 고객이 존재하는 경우,")
        class 해당_id를_가진_고객이_존재하는_경우 {

            @Test
            @DisplayName("해당 id를 가진 고객을 반환한다")
            void 해당_id를_가진_고객을_반환한다() {
                // given
                long inputId = 1;

                // when
                Customer customer = customerRepository.findById(inputId)
                                                      .orElseThrow(AssertionError::new);

                // then
                assertThat(customer).isNotNull();
                assertThat(customer.getId()).isEqualTo(inputId);
            }
        }

        @Nested
        @DisplayName("해당 id를 가진 고객이 존재하지 않는 경우,")
        class 해당_id를_가진_고객이_존재하지_않는_경우 {

            @Test
            @DisplayName("Optional.empty를 반환한다")
            void Optional_empty를_반환한다() {
                // given
                long inputId = 10;

                // when
                Optional<Customer> customer = customerRepository.findById(inputId);

                // then
                assertThat(customer).isNotNull();
                assertThat(customer.isEmpty()).isTrue();
            }
        }
    }

    @Nested
    @Order(4)
    @DisplayName("id를 이용한 고객 삭제 시")
    class Describe_deleteById_method {
        @Nested
        @DisplayName("해당 id를 가진 고객이 존재하는 경우,")
        class Context_Exist_Customer_Has_InputId {

            @Test
            @DisplayName("해당 id를 가진 고객을 삭제한다")
            void it_Return_VoucherList_Object() {
                // given
                long inputId = 1;
                var foundCustomer = customerRepository.findById(inputId);
                assertThat(foundCustomer).isNotNull();
                assertThat(foundCustomer.isEmpty()).isFalse();

                // when
                customerRepository.deleteById(inputId);

                // then
                foundCustomer = customerRepository.findById(inputId);
                assertThat(foundCustomer).isNotNull();
                assertThat(foundCustomer.isEmpty()).isTrue();

            }
        }

        @Nested
        @DisplayName("해당 id를 가진 고객이 존재하지 않는 경우,")
        class Context_Not_Exist_Customer_Has_InputId {

            @Test
            @DisplayName("Not Found Exception을 발생시킨다")
            void it_Throws_Not_Found_Exception() {
                // given
                long inputId = 10;

                // when
                Throwable throwable = catchThrowable(() -> customerRepository.deleteById(inputId));

                // then
                assertThat(throwable).isNotNull();
                assertThat(throwable).isInstanceOf(NotFoundException.class);
            }
        }
    }
}