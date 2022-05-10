package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.PercentDiscountVoucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.dto.Duration;
import com.waterfogsw.voucher.voucher.exception.ResourceNotFoundException;
import com.waterfogsw.voucher.voucher.repository.VoucherJdbcRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql(scripts = {"classpath:sql/testTableInit.sql"})
@Sql(scripts = {"classpath:sql/testTableTearDown.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles({"jdbc"})
@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class VoucherJdbcRepositoryTests {

    @Autowired
    VoucherJdbcRepository voucherRepository;

    @Autowired
    DataSource dataSource;

    @Test
    @Order(1)
    @DisplayName("datasource 확인")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Configuration
    @ComponentScan(
            basePackages = {"com.waterfogsw.voucher.voucher"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/test_voucher_mgmt")
                    .username("root")
                    .password("02709580")
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
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {

        @Nested
        @DisplayName("인자로 전달받은 Voucher 가 null 이면")
        class Context_with_null_argument {

            @Test
            @Order(2)
            @DisplayName("IllegalArgumentException 예외를 발생시킨다")
            void it_throw_error() {
                assertThrows(IllegalArgumentException.class, () -> voucherRepository.save(null));
            }
        }

        @Nested
        @DisplayName("Voucher 가 정상적으로 저장되면")
        class Context_with_voucher_saved {

            @Test
            @Order(3)
            @Transactional
            @DisplayName("저장한 바우처를 리턴한다")
            void it_return_saved_voucher() {
                final var voucher1 = new FixedAmountVoucher(1000);
                final var voucher2 = new PercentDiscountVoucher(50);

                final var savedVoucher1 = voucherRepository.save(voucher1);
                final var savedVoucher2 = voucherRepository.save(voucher2);

                assertThat(savedVoucher1.getType(), is(voucher1.getType()));
                assertThat(savedVoucher1.getValue(), is(voucher1.getValue()));
                assertThat(savedVoucher2.getType(), is(voucher2.getType()));
                assertThat(savedVoucher2.getValue(), is(voucher2.getValue()));
            }
        }

        @Nested
        @DisplayName("중복된 id가 저장되면")
        class Context_with_duplicate_id {

            @Test
            @Order(4)
            @Transactional
            @DisplayName("해당 컬럼을 업데이트 한다")
            void it_throw_error() {
                final var voucher1 = new FixedAmountVoucher(1000);
                final var voucher2 = new PercentDiscountVoucher(1L, 20);

                voucherRepository.save(voucher1);
                final var savedVoucher = voucherRepository.save(voucher2);

                assertThat(savedVoucher.getType(), is(VoucherType.PERCENT_DISCOUNT));
                assertThat(savedVoucher.getValue(), is(20));
            }
        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {

        @Nested
        @DisplayName("호출되면")
        class Context_with_call {

            @Test
            @Order(5)
            @Transactional
            @DisplayName("저장된 모든 Voucher 에 대한 List 를 리턴한다")
            void it_return_saved_voucherList() {
                final var existingVoucherList = voucherRepository.findAll();

                final var voucher1 = new FixedAmountVoucher(1000);
                final var voucher2 = new PercentDiscountVoucher(50);
                final var voucher3 = new PercentDiscountVoucher(30);

                voucherRepository.save(voucher1);
                voucherRepository.save(voucher2);
                voucherRepository.save(voucher3);

                final var addedVoucherList = voucherRepository.findAll();

                assertThat(existingVoucherList.size() + 3, is(addedVoucherList.size()));
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {

        @Nested
        @DisplayName("존재하는 id 값이 조회되면")
        class Context_with_call {

            @Test
            @Order(6)
            @Transactional
            @DisplayName("해당 바우처를 리턴한다")
            void it_return_saved_voucherList() {
                final var voucher = new FixedAmountVoucher(1000);
                voucherRepository.save(voucher);

                final var findVoucher = voucherRepository.findById(1);
                assertThat(findVoucher.isPresent(), is(true));
                assertThat(findVoucher.get().getType(), is(voucher.getType()));
                assertThat(findVoucher.get().getValue(), is(voucher.getValue()));
            }


            @Nested
            @DisplayName("존재하지 않는 id 값이 조회되면")
            class Context_with_not_existing_id {

                @Test
                @Order(7)
                @Transactional
                @DisplayName("빈 값을 반환한다")
                void it_return_saved_voucherList() {
                    final var found = voucherRepository.findById(-1L);

                    assertThat(found.isEmpty(), is(true));
                }
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메서드는")
    class Describe_deleteById {

        @Nested
        @DisplayName("존재하는 id 값이면")
        class Context_with_call {

            @Test
            @Order(7)
            @Transactional
            @DisplayName("해당 바우처를 삭제한다")
            void it_delete_voucher() {
                final var voucher = new FixedAmountVoucher(1000);
                voucherRepository.save(voucher);

                final var findVoucher = voucherRepository.findById(1);
                assertThat(findVoucher.isPresent(), is(true));

                voucherRepository.deleteById(1);
                final var findVoucherAfterDeleted = voucherRepository.findById(1);
                assertThat(findVoucherAfterDeleted.isEmpty(), is(true));
            }


            @Nested
            @DisplayName("존재하지 않는 id 값이면")
            class Context_with_not_existing_id {

                @Test
                @Order(8)
                @Transactional
                @DisplayName("ResourceNotFoundException을 발생시킨다")
                void it_return_saved_voucherList() {
                    assertThrows(ResourceNotFoundException.class, () -> voucherRepository.deleteById(-1));

                }
            }
        }
    }

    @Nested
    @DisplayName("findByType 메서드는")
    class Describe_findByType {

        @Nested
        @DisplayName("type이 null 이면")
        class Context_with_type_null {

            @Test
            @Order(9)
            @DisplayName("IllegalArgumentException 을 발생시킨다")
            void It_throw_IllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> voucherRepository.findByType(null));
            }
        }

        @Nested
        @DisplayName("type이 null이 아니면")
        class Context_with_type {

            @Test
            @Order(10)
            @DisplayName("해당 타입의 모든 바우처를 조회 한다")
            void It_return_type_voucher_list() {
                final var voucher1 = new FixedAmountVoucher(1000);
                final var voucher2 = new FixedAmountVoucher(1000);
                final var voucher3 = new FixedAmountVoucher(1000);
                final var voucher4 = new PercentDiscountVoucher(10);
                final var voucher5 = new PercentDiscountVoucher(10);

                voucherRepository.save(voucher1);
                voucherRepository.save(voucher2);
                voucherRepository.save(voucher3);
                voucherRepository.save(voucher4);
                voucherRepository.save(voucher5);

                final var findVouchers = voucherRepository.findByType(VoucherType.FIXED_AMOUNT);
                assertThat(findVouchers.size(), is(3));
            }
        }
    }

    @Nested
    @DisplayName("findByDuration 메서드는")
    class Describe_findByDuration {

        @Nested
        @DisplayName("duration이 null이면")
        class Context_with_null_duration {

            @Test
            @DisplayName("IllegalArgumentException 을 발생시킨다")
            void It_throw_IllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> voucherRepository.findByDuration(null));
            }
        }


        @Nested
        @DisplayName("duration 이 null 이 아니면")
        class Context_with_not_null {

            @Test
            @DisplayName("해당 기간의 바우처를 반환한다")
            void It_return_duration_voucher() {
                // 바우처 저장
                final var initVoucher1 = new FixedAmountVoucher(100);
                final var initVoucher2 = new FixedAmountVoucher(100);
                final var initVoucher3 = new FixedAmountVoucher(100);

                voucherRepository.save(initVoucher1);
                voucherRepository.save(initVoucher2);
                voucherRepository.save(initVoucher3);

                // updatedAt 날짜 수정
                LocalDateTime time1 = LocalDateTime.of(2022, 2, 2, 1, 0);
                LocalDateTime time2 = LocalDateTime.of(2022, 3, 2, 1, 0);
                LocalDateTime time3 = LocalDateTime.of(2022, 4, 2, 1, 0);

                final var voucher1 = new FixedAmountVoucher(1L, 1000, initVoucher1.getCreatedAt(), time1);
                final var voucher2 = new FixedAmountVoucher(2L, 1000, initVoucher2.getCreatedAt(), time2);
                final var voucher3 = new FixedAmountVoucher(3L, 1000, initVoucher3.getCreatedAt(), time3);

                voucherRepository.save(voucher1);
                voucherRepository.save(voucher2);
                voucherRepository.save(voucher3);

                final var duration = new Duration(LocalDate.of(2022, 2, 2),
                        LocalDate.of(2022, 3, 2));
                final var findVouchers = voucherRepository.findByDuration(duration);
                assertThat(findVouchers.size(), is(2));

            }
        }
    }

    @Nested
    @DisplayName("findByTypeAndDuration 메서드는")
    class Describe_findByTypeAndDuration {

        @Nested
        @DisplayName("인자 값 둘중 하나라도 null 이면")
        class Context_with_null_argument {

            @Test
            @DisplayName("IllegalArgumentException 이 발생한다")
            void It_throw_IllegalArgumentException() {
                assertThrows(IllegalArgumentException.class,
                        () -> voucherRepository.findByTypeAndDuration(null, new Duration(LocalDate.now(), LocalDate.now())));

                assertThrows(IllegalArgumentException.class,
                        () -> voucherRepository.findByTypeAndDuration(VoucherType.FIXED_AMOUNT, null));

                assertThrows(IllegalArgumentException.class,
                        () -> voucherRepository.findByTypeAndDuration(null, null));
            }
        }

        @Nested
        @DisplayName("인자가 null 이 아니면")
        class Context_with_not_null {

            @Test
            @DisplayName("해당 카테고리와 기간에 해당하는 값을 조회한다")
            void It_() {
                // 바우처 저장
                final var initVoucher1 = new PercentDiscountVoucher(20);
                final var initVoucher2 = new FixedAmountVoucher(100);
                final var initVoucher3 = new FixedAmountVoucher(100);

                voucherRepository.save(initVoucher1);
                voucherRepository.save(initVoucher2);
                voucherRepository.save(initVoucher3);

                // updatedAt 날짜 수정
                LocalDateTime time1 = LocalDateTime.of(2022, 2, 2, 1, 0);
                LocalDateTime time2 = LocalDateTime.of(2022, 3, 2, 1, 0);
                LocalDateTime time3 = LocalDateTime.of(2022, 4, 2, 1, 0);

                final var voucher1 = new PercentDiscountVoucher(1L, 20, initVoucher1.getCreatedAt(), time1);
                final var voucher2 = new FixedAmountVoucher(2L, 100, initVoucher2.getCreatedAt(), time2);
                final var voucher3 = new FixedAmountVoucher(3L, 100, initVoucher3.getCreatedAt(), time3);

                voucherRepository.save(voucher1);
                voucherRepository.save(voucher2);
                voucherRepository.save(voucher3);

                final var duration = new Duration(LocalDate.of(2022, 2, 2),
                        LocalDate.of(2022, 3, 2));
                final var findVouchers = voucherRepository
                        .findByTypeAndDuration(VoucherType.FIXED_AMOUNT, duration);
                assertThat(findVouchers.size(), is(1));
            }
        }

    }
}
