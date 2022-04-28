package org.prgrms.deukyun.voucherapp.domain.voucher.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.persistence.JdbcCustomerRepository;
import org.prgrms.deukyun.voucherapp.domain.testutil.JdbcTestConfig;
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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.customer;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.voucher;

@Transactional
@SpringJUnitConfig
@ContextConfiguration(classes = JdbcTestConfig.class)
class JdbcVoucherRepositoryTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    JdbcVoucherRepository jdbcVoucherRepository;
    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;
    Voucher voucher;

    @BeforeEach
    void setUp() {
        jdbcVoucherRepository = new JdbcVoucherRepository(jdbcTemplate);
        voucher = voucher();
    }


    @Test
    void 성공_생성() {
        //when
        Voucher insertedVoucher = jdbcVoucherRepository.insert(voucher);

        //then
        assertVoucher(insertedVoucher);
        assertFADV(insertedVoucher, voucher);
    }

    @Test
    void 성공_전체조회() {
        //given
        Voucher voucher1 = voucher();
        Voucher voucher2 = voucher();
        jdbcVoucherRepository.insert(voucher1);
        jdbcVoucherRepository.insert(voucher2);

        //when
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();

        //then
        assertThat(vouchers).extracting("id")
                .containsExactlyInAnyOrder(voucher1.getId(), voucher2.getId());
    }

    @Test
    void 성공_고객의_아이디로_전체_조회() {
        //given
        Customer customer = customer();
        UUID customerId = customer.getId();
        jdbcCustomerRepository.insert(customer);

        Voucher voucher1 = voucher();
        voucher1.setOwnerId(customerId);
        jdbcVoucherRepository.insert(voucher1);

        Voucher voucher2 = voucher();
        voucher2.setOwnerId(customerId);
        jdbcVoucherRepository.insert(voucher2);

        Voucher voucher3 = voucher();
        jdbcVoucherRepository.insert(voucher3);

        //when
        List<Voucher> foundVouchers = jdbcVoucherRepository.findByCustomerId(customerId);

        //then
        assertThat(foundVouchers).hasSize(2);
        assertThat(foundVouchers).extracting("id")
                .containsExactlyInAnyOrder(voucher1.getId(), voucher2.getId());
    }

    @Test
    void 실패_고객의_아이디로_전체_조회() {
        //given
        UUID customerId = null;

        //then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> jdbcVoucherRepository.findByCustomerId(customerId));
    }


    @Test
    void 성공_단건조회() {
        //given
        jdbcVoucherRepository.insert(voucher);
        UUID id = voucher.getId();

        //when
        Optional<Voucher> foundVoucher = jdbcVoucherRepository.findById(id);

        //then
        assertThat(foundVoucher).isPresent();
        assertFADV(foundVoucher.get(), voucher);
    }

    @Test
    void 성공_단건조회_아이디가_없을경우_OptionalEmpty_반환() {
        //given
        jdbcVoucherRepository.insert(voucher);
        UUID id = UUID.randomUUID();

        //when
        Optional<Voucher> foundVoucher = jdbcVoucherRepository.findById(id);

        //then
        assertThat(foundVoucher).isNotPresent();
    }


    @Test
    void 성공_전체삭제() {
        //given
        jdbcVoucherRepository.insert(voucher());
        jdbcVoucherRepository.insert(voucher());

        //when
        jdbcVoucherRepository.deleteAll();

        //then
        assertThat(jdbcVoucherRepository.findAll()).isEmpty();
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