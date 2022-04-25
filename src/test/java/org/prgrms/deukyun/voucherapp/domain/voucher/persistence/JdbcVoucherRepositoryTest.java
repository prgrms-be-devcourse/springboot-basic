package org.prgrms.deukyun.voucherapp.domain.voucher.persistence;

import org.junit.jupiter.api.*;
import org.prgrms.deukyun.voucherapp.domain.testconfig.JdbcTestConfig;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringJUnitConfig
@ContextConfiguration(classes = JdbcTestConfig.class)
class JdbcVoucherRepositoryTest {

    @Autowired NamedParameterJdbcTemplate jdbcTemplate;
    JdbcVoucherRepository jdbcVoucherRepository;
    Voucher voucher;

    @BeforeEach
    void setUp() {
        jdbcVoucherRepository = new JdbcVoucherRepository(jdbcTemplate);
        voucher = dummyVoucher();
    }

    @Nested
    @DisplayName("삽입")
    class insertTest {

        @Test
        void 성공() {
            //when
            Voucher insertedVoucher = jdbcVoucherRepository.insert(voucher);

            //assert
            assertVoucher(insertedVoucher);
            assertFADV(insertedVoucher, voucher);
        }
    }

    @Nested
    @DisplayName("전체 조회")
    class findAllTest {

        @Test
        void 성공() {
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
    @DisplayName("단건 조회")
    class findByIdTest {

        UUID id;

        @BeforeEach
        void setup() {
            jdbcVoucherRepository.insert(voucher);
        }

        @Test
        void 성공() {
            //setup
            id = voucher.getId();

            //when
            Optional<Voucher> foundVoucher = jdbcVoucherRepository.findById(id);

            //assert
            assertThat(foundVoucher).isPresent();
            assertFADV(foundVoucher.get(), voucher);
        }

        @Test
        void 성공_아이디가_없을경우_OptionalEmpty_반환() {
            //setup
            id = UUID.randomUUID();

            //when
            Optional<Voucher> foundVoucher = jdbcVoucherRepository.findById(id);

            //assert
            assertThat(foundVoucher).isNotPresent();
        }
    }

    @Nested
    @DisplayName("전체 삭제")
    class deleteAllTest{

        @Test
        void 성공(){
            //setup
            jdbcVoucherRepository.insert(dummyVoucher());
            jdbcVoucherRepository.insert(dummyVoucher());

            //action
            jdbcVoucherRepository.deleteAll();

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