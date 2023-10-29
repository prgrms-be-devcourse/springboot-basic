package org.prgms.kdtspringweek1.voucher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class JdbcVoucherRepositoryTest {

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.update("DELETE FROM wallets", Collections.emptyMap());
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Test
    @DisplayName("바우처 저장 성공")
    void Success_Save() {
        // given
        Voucher voucher = createVoucher();
        int beforeSave = jdbcVoucherRepository.findAllVouchers().size();

        // when
        Voucher savedVoucher = jdbcVoucherRepository.save(voucher);

        // then
        assertThat(savedVoucher, samePropertyValuesAs(voucher));
        assertThat(jdbcVoucherRepository.findAllVouchers(), hasSize(beforeSave + 1));
    }

    @Test
    @DisplayName("모든 바우처 검색 성공")
    void Success_FindAllVouchers() {
        // given
        List<Voucher> savedVouchers = saveVouchers();
        int customerCnt = jdbcVoucherRepository.findAllVouchers().size();

        // when
        List<Voucher> allVouchers = jdbcVoucherRepository.findAllVouchers();

        // then
        assertThat(allVouchers, hasSize(customerCnt));
    }

    @Test
    @DisplayName("바우처 아이디로 바우처 검색 성공")
    void Success_FindById() {
        // given
        Voucher voucher = createVoucher();
        jdbcVoucherRepository.save(voucher);

        // when
        Optional<Voucher> foundVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());

        // then
        assertThat(foundVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("바우처 정보 수정 성공")
    void Success_Update() {
        // given
        Voucher voucher = createVoucher();
        jdbcVoucherRepository.save(voucher);
        Voucher voucherToUpdate = null;
        if (voucher.getVoucherType() == VoucherType.FIXED_AMOUNT) {
            voucherToUpdate = FixedAmountVoucher.createWithIdAndAmount(voucher.getVoucherId(), 600);
        } else if (voucher.getVoucherType() == VoucherType.PERCENT_DISCOUNT) {
            voucherToUpdate = PercentDiscountVoucher.createWithIdAndPercent(voucher.getVoucherId(), 60);
        }

        // when
        Voucher updatedVoucher = jdbcVoucherRepository.update(voucherToUpdate);

        // then
        assertThat(updatedVoucher, samePropertyValuesAs(voucherToUpdate));
    }

    @Test
    @DisplayName("모든 바우처 삭제 성공")
    void Success_DeleteAll() {
        // given
        saveVouchers();

        // when
        jdbcVoucherRepository.deleteAll();

        // then
        assertThat(jdbcVoucherRepository.findAllVouchers(), hasSize(0));
    }

    @Test
    @DisplayName("바우처 아이디로 바우처 삭제 성공")
    void Success_DeleteById() {
        // given
        Voucher voucher = createVoucher();
        jdbcVoucherRepository.save(voucher);

        // when
        jdbcVoucherRepository.deleteById(voucher.getVoucherId());

        // then
        assertTrue(jdbcVoucherRepository.findById(voucher.getVoucherId()).isEmpty());
    }

    private Voucher createVoucher() {
        int voucherNum = new Random().nextInt(2);
        if (voucherNum == 0) {
            return FixedAmountVoucher.createWithAmount(300);
        } else {
            return PercentDiscountVoucher.createWithPercent(30);
        }
    }

    private List<Voucher> saveVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            vouchers.add(createVoucher());
        }

        return vouchers;
    }
}