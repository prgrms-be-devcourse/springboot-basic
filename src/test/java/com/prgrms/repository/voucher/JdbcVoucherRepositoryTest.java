package com.prgrms.repository.voucher;


import com.prgrms.model.voucher.FixedAmountVoucher;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.voucher.discount.FixedDiscount;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(JdbcVoucherRepository.class)
class JdbcVoucherRepositoryTest {


    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    private Voucher newFixVoucher;

    private int id = 1;

    @BeforeAll
    void clean() {
        newFixVoucher = new FixedAmountVoucher(id, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        jdbcVoucherRepository.insert(newFixVoucher);
    }


    @Test
    @DisplayName("바우처를 추가한 결과 반환하는 바우처의 아이디와 추가한 바우처의 아이디는 같다.")
    void insert_VoucherId_EqualsNewVoucherId() {

        Optional<Voucher> retrievedVoucher = jdbcVoucherRepository.findById(newFixVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get().getVoucherId(), samePropertyValuesAs(newFixVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("데이터베이스에 몇 개의 데이터를 저장한 후 전체 고객을 조회한 결과는 빈 값을 반환하지 않는다.")
    void findAll_Vouchers_NotEmpty() {
        Vouchers vouchers = jdbcVoucherRepository.getAllVoucher();
        assertThat(vouchers.getVouchers().isEmpty(), is(false));
    }

    @Test
    @DisplayName("데이터베이스에 존재하는 회원의 아이디로 검색했을 때 반환하는 바우처의 아이디와 검색할 때 사용한 바우처의 아이디는 같다.")
    void findById_ExistingVoucherId_EqualsSearchId() {
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(newFixVoucher.getVoucherId());
        assertThat(voucher.isEmpty(), is(false));

        assertThat(voucher.get().getVoucherId()).isEqualTo(newFixVoucher.getVoucherId());
    }
}
