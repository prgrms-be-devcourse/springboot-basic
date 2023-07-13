package com.prgrms.repository.voucher;


import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.discount.FixedDiscount;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import({JdbcVoucherRepository.class, VoucherCreator.class})
class JdbcVoucherRepositoryTest {

    private final int VOUCHER_ID = Math.abs(new Random().nextInt());

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;
    @Autowired
    private VoucherCreator voucherCreator;
    private Voucher newFixVoucher;

    @BeforeAll
    void clean() {
        newFixVoucher = new FixedAmountVoucher(VOUCHER_ID, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        jdbcVoucherRepository.insert(newFixVoucher);
    }


    @Test
    @DisplayName("바우처를 추가한 결과 반환하는 바우처의 아이디와 추가한 바우처의 아이디는 같다.")
    void insert_VoucherId_EqualsNewVoucherId() {
        //when
        Optional<Voucher> retrievedVoucher = jdbcVoucherRepository.findById(newFixVoucher.getVoucherId());

        //then
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get().getVoucherId(), samePropertyValuesAs(newFixVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("데이터베이스에 몇 개의 데이터를 저장한 후 전체 고객을 조회한 결과는 빈 값을 반환하지 않는다.")
    void findAll_Vouchers_NotEmpty() {
        //when
        Vouchers vouchers = jdbcVoucherRepository.getAllVoucher();

        //then
        assertThat(vouchers.getVouchers().isEmpty(), is(false));
    }

    @Test
    @DisplayName("데이터베이스에 존재하는 회원의 아이디로 검색했을 때 반환하는 바우처의 아이디와 검색할 때 사용한 바우처의 아이디는 같다.")
    void findById_ExistingVoucherId_EqualsSearchId() {
        //when
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(newFixVoucher.getVoucherId());

        //then
        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get().getVoucherId()).isEqualTo(newFixVoucher.getVoucherId());
    }
}
