package org.prgrms.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.prgrms.voucher.models.Customer;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        var config= aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("voucher-program", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    private final DataSource dataSource = DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:2215/voucher-program")
            .username("test")
            .password("test1234!")
            .type(HikariDataSource.class)
            .build();

    private final CustomerJdbcRepository customerJdbcRepository = new CustomerJdbcRepository(new NamedParameterJdbcTemplate(dataSource));

    @Nested
    @Order(1)
    @DisplayName("Repository save 메서드는")
    class DescribeCreate {

        @Nested
        @Order(1)
        @DisplayName("save 기능을 테스트 할 때 customer 객체를 null로 받으면")
        class ContextReceiveFixedVoucherTypeAndWrongValue {

            Customer customer = null;

            @Test
            @DisplayName("잘못된 인자 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

                Assertions.assertThatThrownBy(() -> customerJdbcRepository.save(customer))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @Order(2)
        @DisplayName("save 기능을 테스트 할 때 알 수 없는 ID의 customer 객체를 인자로 받으면")
        class ContextReceiveWrongCustomer {

            Customer wrongCustomer = new Customer(2L, "jack", LocalDateTime.now(), LocalDateTime.now());

            @Test
            @DisplayName("잘못된 customer 예외를 반환한다.")
            void itReturnWrongCustomerException() {

                Assertions.assertThatThrownBy(() -> customerJdbcRepository.save(wrongCustomer))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @Order(3)
        @DisplayName("save 기능을 테스트 할 때 새로 생성된 customer 객체를 인자로 받으면")
        class ContextReceiveNullIdCustomer {

            Customer expectedCustomer = new Customer("jack");

            @Test
            @DisplayName("데이터 베이스 저장소에 저장하고 저장한 customer를 반환한다.")
            void itVoucherSaveAndReturn() {

                Customer actualCustomer = customerJdbcRepository.save(expectedCustomer);

                Assertions.assertThat(expectedCustomer.getCustomerName()).isEqualTo(actualCustomer.getCustomerName());
            }
        }
    }

    @Nested
    @Order(2)
    @DisplayName("Repository findAll 메서드는")
    class DescribeFindAllMethod {

        @Nested
        @DisplayName("호출이 되면")
        class ContextCallThis {

            @Test
            @DisplayName("저장소의 고객 정보를 리스트로 반환한다.")
            void itReturnCustomerList() {

                List<Customer> list = customerJdbcRepository.findAll();

                Assertions.assertThat(list.isEmpty()).isFalse();
                Assertions.assertThat(list.get(0).getCustomerId()).isEqualTo(1);
            }
        }
    }

    @Nested
    @Order(3)
    @DisplayName("Repository findById 메서드는")
    class DescribeFindById {

        @Nested
        @DisplayName("존재하지 않는 ID를 인자로 받으면")
        class ContextReceiveWrongId {

            Long wrongId = -1L;

            @Test
            @DisplayName("비어있는 Optional객체를 반환한다.")
            void itReturnEmptyOptional() {

                Optional<Customer> expectedCustomer = customerJdbcRepository.findById(wrongId);

                Assertions.assertThat(expectedCustomer).isEqualTo(Optional.empty());
            }
        }

        @Nested
        @DisplayName("존재하는 ID를 인자로 받으면")
        class ContextReceiveValidId {

            Long validId = 1L;

            @Test
            @DisplayName("해당 고객을 반환한다.")
            void itReturnCustomer() {

                Optional<Customer> expectedCustomer = customerJdbcRepository.findById(validId);

                Assertions.assertThat(expectedCustomer.get().getCustomerId()).isEqualTo(1L);
                Assertions.assertThat(expectedCustomer.get().getCustomerName()).isEqualTo("jack");
            }
        }
    }

    @Nested
    @Order(4)
    @DisplayName("Repository deleteById 메서드는")
    class DescribeDeleteById {

        @Nested
        @DisplayName("존재하지 않는 ID를 인자로 받으면")
        class ContextReceiveWrongId {

            Long wrongId = -1L;

            @Test
            @DisplayName("잘못된 상태 예외를 반환한다.")
            void itThrowIllegalStateException() {

                Assertions.assertThatThrownBy(() -> customerJdbcRepository.deleteById(wrongId))
                        .isInstanceOf(IllegalStateException.class);
            }
        }

        @Nested
        @DisplayName("존재하는 ID를 인자로 받으면")
        class ContextReceiveValidId {

            Long validId = 1L;

            @Test
            @DisplayName("해당 고객을 삭제한다.")
            void itDeleteCustomer() {

                customerJdbcRepository.deleteById(validId);

                Assertions.assertThatThrownBy(() -> customerJdbcRepository.deleteById(validId))
                        .isInstanceOf(IllegalStateException.class);
            }
        }
    }
}