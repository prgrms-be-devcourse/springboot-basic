package org.prgrms.deukyun.voucherapp.domain.voucher.persistence;

import org.junit.jupiter.api.*;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcVoucherRepositoryTest {

    static JdbcVoucherRepository jdbcVoucherRepository;
    static Voucher voucher;

    static DataSource dataSource;

    @BeforeAll
    static void setup() {
        dataSource = DataSourceBuilder
                .create()
                .url("jdbc:h2:mem:testdb;INIT=RUNSCRIPT FROM 'classpath:/schema.sql';DB_CLOSE_DELAY=-1")
                .driverClassName("org.h2.Driver")
                .build();
        jdbcVoucherRepository = new JdbcVoucherRepository(new NamedParameterJdbcTemplate(dataSource));
        voucher = dummyVoucher();
    }

    @AfterEach
    void cleanup() {
        jdbcVoucherRepository.clear();
    }

    @Nested
    class insertTest {

        @Test
        void givenVoucher_whenCallInsert_thenIdIsSetAndReturnsInsertedVoucher() {
            //when
            Voucher insertedVoucher = jdbcVoucherRepository.insert(voucher);

            //assert
            assertVoucher(insertedVoucher);
            assertFADV(insertedVoucher, voucher);
        }
    }

    @Nested
    class findAllTest {

        @Test
        void givenTwoVoucherInsertion_whenCallFindAll_thenGivesTwoVouchers() {
            //setup
            Voucher voucher1 = dummyVoucher();
            Voucher voucher2 = dummyVoucher();
            jdbcVoucherRepository.insert(voucher1);
            jdbcVoucherRepository.insert(voucher2);

            //when
            List<Voucher> vouchers = jdbcVoucherRepository.findAll();

            //assert
            assertThat(vouchers).extracting("id")
                    .containsExactlyInAnyOrder(voucher1.getId(), voucher2.getId());
        }
    }

    @Nested
    class findByIdTest {

        UUID id;

        @BeforeEach
        void setup() {
            jdbcVoucherRepository.insert(voucher);
        }

        @Test
        void givenIdOfInsertedVoucher_whenCallFindById_thenReturnFoundVoucherInstance() {
            //setup
            id = voucher.getId();

            //when
            Optional<Voucher> foundVoucher = jdbcVoucherRepository.findById(id);

            //assert
            assertThat(foundVoucher).isPresent();
            assertFADV(foundVoucher.get(), voucher);
        }

        @Test
        void givenInvalidId_whenCallFindById_thenReturnOptionalEmpty() {
            //setup
            id = UUID.randomUUID();

            //when
            Optional<Voucher> foundVoucher = jdbcVoucherRepository.findById(id);

            //assert
            assertThat(foundVoucher).isNotPresent();
        }
    }

    @Nested
    class clearTest{

        @Test
        void givenTwoInsertion_whenCallClear_thenFindAllReturnsEmptyList(){
            //setup
            jdbcVoucherRepository.insert(dummyVoucher());
            jdbcVoucherRepository.insert(dummyVoucher());

            //action
            jdbcVoucherRepository.clear();

            //assert
            assertThat(jdbcVoucherRepository.findAll()).isEmpty();
        }
    }

    private static Voucher dummyVoucher() {
        return new FixedAmountDiscountVoucher(2000L);
    }

    private void assertVoucher(Voucher actualVoucher) {
        assertThat(actualVoucher).isNotNull();
        assertThat(actualVoucher.getId()).isNotNull();
    }

    private void assertFADV(Voucher insertedVoucher, Voucher expectedVoucher) {
        FixedAmountDiscountVoucher actualFADV = (FixedAmountDiscountVoucher) insertedVoucher;
        FixedAmountDiscountVoucher expectedFADV = (FixedAmountDiscountVoucher) expectedVoucher;
        assertThat(actualFADV.getAmount()).isEqualTo(expectedFADV.getAmount());
    }
}