package org.prgms.voucherProgram.domain.voucher.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.config.Charset.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgms.voucherProgram.customer.domain.Customer;
import org.prgms.voucherProgram.customer.repository.BlackListRepository;
import org.prgms.voucherProgram.customer.repository.JdbcCustomerRepository;
import org.prgms.voucherProgram.global.error.exception.NothingChangeException;
import org.prgms.voucherProgram.voucher.domain.FixedAmountVoucher;
import org.prgms.voucherProgram.voucher.domain.PercentDiscountVoucher;
import org.prgms.voucherProgram.voucher.domain.Voucher;
import org.prgms.voucherProgram.voucher.repository.FileVoucherRepository;
import org.prgms.voucherProgram.voucher.repository.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
class JdbcVoucherRepositoryTest {

    private static EmbeddedMysql embeddedMysql;

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    private JdbcCustomerRepository jdbcCustomerRepository;

    @BeforeAll
    static void setup() {
        MysqldConfig config = aMysqldConfig(v8_0_17)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "test1234")
            .withTimeZone("Aisa/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    static void tearDown() {
        embeddedMysql.stop();
    }

    @AfterEach
    void clear() {
        jdbcVoucherRepository.deleteAll();
    }

    private Voucher voucher() {
        return new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 20L, LocalDateTime.now());
    }

    private List<Voucher> vouchers() {
        return List.of(new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 20L, LocalDateTime.now()),
            new PercentDiscountVoucher(UUID.randomUUID(), 30L, LocalDateTime.now()));
    }

    private Customer customer() {
        return new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
    }

    @Configuration
    @ComponentScan(basePackages = {"org.prgms.voucherProgram.voucher.repository",
        "org.prgms.voucherProgram.customer.repository"},
        excludeFilters = @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            value = {FileVoucherRepository.class, BlackListRepository.class}))
    static class Config {
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
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {

        @Nested
        @DisplayName("바우처가 주어지면")
        class Context_with_voucher {
            private static Stream<Arguments> provideVoucher() {
                return Stream.of(
                    Arguments.of(
                        new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 20L, LocalDateTime.now())),
                    Arguments.of(new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now())),
                    Arguments.of(new PercentDiscountVoucher(UUID.randomUUID(), 30L, LocalDateTime.now())),
                    Arguments.of(
                        new PercentDiscountVoucher(UUID.randomUUID(), UUID.randomUUID(), 30L, LocalDateTime.now()))
                );
            }

            @DisplayName("주어진 바우처를 저장하고 저장된 바우처를 리턴한다.")
            @ParameterizedTest
            @MethodSource("provideVoucher")
            void it_saves_voucher_and_returns_a_saved_voucher(Voucher voucher) {
                Voucher savedVoucher = jdbcVoucherRepository.save(voucher);

                Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
                assertThat(findVoucher).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(voucher);
                assertThat(savedVoucher).isEqualTo(savedVoucher);
            }
        }

    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {

        @Nested
        @DisplayName("저장된 바우처들이 있다면")
        class Context_with_saved_vouchers {

            @BeforeEach
            void prepare() {
                vouchers().forEach(voucher -> jdbcVoucherRepository.save(voucher));
            }

            @Test
            @DisplayName("모든 바우처들을 리턴한다.")
            void it_returns_all_vouchers() {
                List<Voucher> findVouchers = jdbcVoucherRepository.findAll();

                assertThat(findVouchers).hasSize(vouchers().size());
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {

        @Nested
        @DisplayName("만약 저장된 바우처의 ID라면")
        class Context_with_saved_voucher_id {
            final Voucher voucher = voucher();

            @BeforeEach
            void prepare() {
                jdbcVoucherRepository.save(voucher);
            }

            @Test
            @DisplayName("해당 바우처를 리턴한다.")
            void it_return_voucher() {
                Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());

                assertThat(findVoucher).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(voucher);
            }
        }

        @Nested
        @DisplayName("만약 저장되지 않은 바우처의 ID라면")
        class Context_with_not_saved_voucher_id {
            final UUID notSavedId = UUID.randomUUID();

            @Test
            @DisplayName("Optional Empty 를 리턴한다.")
            void it_return_optional_empty() {
                Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(notSavedId);

                assertThat(findVoucher).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("findByTypeAndDate 메서드는")
    class Describe_findByTypeAndDate {

        @Nested
        @DisplayName("해당하는 생성기간과 타입의 바우처가 있다면")
        class Context_with_created_date_and_type_voucher {
            final List<Voucher> vouchers = List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.of(2022, 1, 12, 1, 1)),
                new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.of(2022, 1, 20, 1, 1)));
            final int type = 1;
            final LocalDateTime start = LocalDateTime.of(2022, 1, 12, 0, 0);
            final LocalDateTime end = LocalDateTime.of(2022, 1, 30, 0, 0);

            @BeforeEach
            void prepare() {
                vouchers.forEach(voucher -> jdbcVoucherRepository.save(voucher));
            }

            @Test
            @DisplayName("바우처들을 찾고 리턴한다.")
            void it_finds_and_returns_vouchers() {
                List<Voucher> findVouchers = jdbcVoucherRepository.findByTypeAndDate(type, start, end);
                assertThat(findVouchers).usingRecursiveFieldByFieldElementComparatorIgnoringFields()
                    .containsAll(vouchers);
            }
        }
    }

    @Nested
    @DisplayName("findByCustomerId 메서드는")
    class Describe_findByCustomerId {

        @Nested
        @DisplayName("만약 해당 고객에게 할당된 바우처가 있다면")
        class Context_with_assign_voucher {
            final UUID customerId = UUID.randomUUID();
            final List<Voucher> vouchers = List.of(
                new FixedAmountVoucher(UUID.randomUUID(), customerId, 100L, LocalDateTime.now()),
                new PercentDiscountVoucher(UUID.randomUUID(), customerId, 50L, LocalDateTime.now()));

            @BeforeEach
            void prepare() {
                vouchers.forEach((jdbcVoucherRepository::save));
            }

            @Test
            @DisplayName("해당 바우처를 리턴한다.")
            void it_return_voucher() {
                List<Voucher> findVouchers = jdbcVoucherRepository.findByCustomerId(customerId);

                assertThat(findVouchers).hasSize(2)
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
                    .containsAll(vouchers);
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {
        final Voucher voucher = voucher();

        @BeforeEach
        void prepare() {
            jdbcVoucherRepository.save(voucher);
        }

        @Nested
        @DisplayName("수정할 바우처가 주어지면")
        class Context_with_update_voucher {
            final Voucher voucher = voucher();
            final Voucher update = new FixedAmountVoucher(voucher.getVoucherId(), voucher.getCustomerId(), 50L,
                voucher.getCreatedDateTime());

            @BeforeEach
            void prepare() {
                jdbcVoucherRepository.save(voucher);
            }

            @Test
            @DisplayName("바우처를 수정하고 수정된 바우처를 리턴한다.")
            void it_update_voucher_and_returns_updated_voucher() {
                Voucher updatedVoucher = jdbcVoucherRepository.update(update);

                assertThat(updatedVoucher).isEqualTo(update);
                assertThat(jdbcVoucherRepository.findById(voucher.getVoucherId())).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(update);
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메서드는")
    class Describe_deleteById {

        @Nested
        @DisplayName("만약 저장된 바우처의 ID라면")
        class Context_with_saved_voucher_id {
            final Voucher voucher = voucher();

            @BeforeEach
            void prepare() {
                jdbcVoucherRepository.save(voucher);
            }

            @Test
            @DisplayName("해당 바우처를 삭제한다.")
            void it_delete_voucher() {
                jdbcVoucherRepository.deleteById(voucher.getVoucherId());

                assertThat(jdbcVoucherRepository.findById(voucher.getVoucherId())).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 저장되지 않은 바우처의 ID라면")
        class Context_with_not_saved_voucher_id {
            final UUID notSavedId = UUID.randomUUID();

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_Exception() {
                assertThatThrownBy(() -> jdbcVoucherRepository.deleteById(notSavedId))
                    .isInstanceOf(NothingChangeException.class)
                    .hasMessage("[ERROR] 해당 요청이 정상적으로 처리되지 않았습니다.");
            }
        }
    }

    @Nested
    @DisplayName("deleteAll 메서드는")
    class Describe_deleteAll {

        @Nested
        @DisplayName("저장된 바우처들이 있다면")
        class Context_with_ {

            @BeforeEach
            void prepare() {
                vouchers().forEach(voucher -> jdbcVoucherRepository.save(voucher));
            }

            @Test
            @DisplayName("모두 삭제한다.")
            void it_delete_all() {
                jdbcVoucherRepository.deleteAll();

                assertThat(jdbcVoucherRepository.findAll()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("assignCustomer 메서드는")
    class Describe_assignCustomer {

        @Nested
        @DisplayName("고객에게 할당된 바우처가 주어진다면")
        class Context_with_assgin_voucher {
            final Voucher voucher = voucher();
            UUID customerId = UUID.randomUUID();
            Voucher assignVoucher = new FixedAmountVoucher(voucher.getVoucherId(), customerId,
                voucher.getDiscountValue(), voucher.getCreatedDateTime());

            @BeforeEach
            void prepare() {
                jdbcVoucherRepository.save(voucher);
            }

            @Test
            @DisplayName("고객ID로 수정한 후 할당된 바우처를 리턴한다.")
            void it_assign_to_customer_and_returns_assigned_voucher() {
                Voucher assignedVoucher = jdbcVoucherRepository.assignCustomer(assignVoucher);

                assertThat(assignedVoucher).isEqualTo(assignVoucher);
                assertThat(jdbcVoucherRepository.findById(voucher.getVoucherId())).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(assignedVoucher);
            }
        }
    }

    @Nested
    @DisplayName("findByCustomerEmail 메서드는")
    class Describe_findByCustomerEmail {

        @Nested
        @DisplayName("만약 고객에게 할당된 바우처들이 있다면")
        class Context_with_customer_has_vouchers {
            Customer customer = customer();
            List<Voucher> vouchers = List.of(
                new FixedAmountVoucher(UUID.randomUUID(), customer.getCustomerId(), 20L, LocalDateTime.now()),
                new PercentDiscountVoucher(UUID.randomUUID(), customer.getCustomerId(), 30L, LocalDateTime.now()));

            @BeforeEach
            void prepare() {
                jdbcCustomerRepository.save(customer);
                vouchers.forEach(voucher -> jdbcVoucherRepository.save(voucher));
            }

            @Test
            @DisplayName("고객의 이메일로 찾고 할당된 바우처들을 리턴한다.")
            void it_find_by_email_and_return_assign_vouchers() {
                List<Voucher> findVouchers = jdbcVoucherRepository.findByCustomerEmail(customer.getEmail());

                assertThat(findVouchers).hasSize(2)
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
                    .containsAll(vouchers);
            }
        }
    }
}
