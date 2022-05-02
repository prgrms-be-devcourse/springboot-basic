package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.PercentDiscountVoucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles({"jdbc"})
@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
                final var voucher1 = new FixedAmountVoucher(0L, 1000);
                final var voucher2 = new PercentDiscountVoucher(0L, 20);

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
                final var voucher = new FixedAmountVoucher(0L, 1000);
                voucherRepository.save(voucher);

                final var findVoucher = voucherRepository.findById(0L);
                assertThat(findVoucher.isPresent(), is(true));
                assertThat(findVoucher.get().getId(), is(voucher.getId()));
                assertThat(findVoucher.get().getType(), is(voucher.getType()));
                assertThat(findVoucher.get().getValue(), is(voucher.getValue()));
            }
        }
    }
}
