package org.programmers.VoucherManagement.voucher.infrastructure;

import org.junit.jupiter.api.*;
import org.programmers.VoucherManagement.voucher.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.annotation.Rollback;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JdbcVoucherRepositoryTest {

    private JdbcVoucherRepository voucherRepository;

    JdbcVoucherRepositoryTest(@Autowired DataSource dataSource) {
        voucherRepository = new JdbcVoucherRepository(dataSource);
    }

    private Voucher fixedVoucher;
    private Voucher percentVoucher;

    @BeforeAll
    void initVoucher() {
        fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), DiscountType.FIXED, new DiscountValue(1000));
        percentVoucher = new PercentAmountVoucher(UUID.randomUUID(), DiscountType.PERCENT, new DiscountValue(10));
        voucherRepository.deleteAll();
    }

    @Order(1)
    @Test
    @Rollback(value = false)
    @DisplayName("Percent 바우처를 생성할 수 있다.")
    void insert_PercentVoucher_EqualsNewPercentVoucher() {
        //given
        voucherRepository.insert(percentVoucher);

        //when
        Voucher voucherExpect = voucherRepository.findById(percentVoucher.getVoucherId()).get();

        //then
        assertThat(voucherExpect).isEqualTo(percentVoucher);
    }

    @Order(2)
    @Test
    @Rollback(value = false)
    @DisplayName("Fixed 바우처를 생성할 수 있다.")
    void insert_FixedVoucher_EqualsNewFixedVoucher() {
        //given
        voucherRepository.insert(fixedVoucher);

        //when
        Voucher voucherExpect = voucherRepository.findById(fixedVoucher.getVoucherId()).get();

        //then
        assertThat(voucherExpect).isEqualTo(fixedVoucher);
    }

    @Order(3)
    @Test
    @Rollback(value = false)
    @DisplayName("Fixed 바우처 금액을 수정할 수 있다. - 성공")
    void update_FixedVoucher_EqualsUpdateFixedVoucher() {
        //given
        fixedVoucher.changeDiscountValue(new DiscountValue(2000));
        voucherRepository.update(fixedVoucher);

        //when
        Voucher voucherExpect = voucherRepository.findById(fixedVoucher.getVoucherId()).get();

        //then
        assertThat(voucherExpect).isEqualTo(fixedVoucher);
    }

    @Order(4)
    @Test
    @Rollback(value = false)
    @DisplayName("Percent 바우처 금액을 수정할 수 있다. - 성공")
    void update_PercentVoucher_EqualsUpdatePercentVoucher() {
        //given
        percentVoucher.changeDiscountValue(new DiscountValue(20));
        voucherRepository.update(percentVoucher);

        //when
        Voucher voucherExpect = voucherRepository.findById(percentVoucher.getVoucherId()).get();

        //then
        assertThat(voucherExpect).isEqualTo(percentVoucher);
    }


    @Order(5)
    @Test
    @DisplayName("id를 이용해 Percent 바우처를 조회할 수 있다. - 성공")
    void findById_VoucherID_EqualsFindVoucher() {
        //given
        UUID findVoucherId = percentVoucher.getVoucherId();

        //when
        Voucher voucherExpect = voucherRepository.findById(findVoucherId).get();

        //then
        assertThat(voucherExpect).isEqualTo(percentVoucher);
    }

    @Order(6)
    @Test
    @DisplayName("등록된 바우처를 전체 조회할 수 있다. - 성공")
    void findAll_Success() {
        //when
        List<Voucher> voucherList = voucherRepository.findAll();

        //then
        assertThat(voucherList.size()).isEqualTo(2);
    }

    @Order(7)
    @Test
    @Rollback(value = false)
    @DisplayName("등록된 바우처를 삭제할 수 있다. - 성공")
    void delete_VoucherId_Success() {
        //given
        UUID deleteVoucherId = percentVoucher.getVoucherId();
        Voucher deleteVoucher = voucherRepository.findById(deleteVoucherId).get();

        //when
        voucherRepository.delete(deleteVoucher);

        //then
        //1. 사이즈 비교
        int sizeExpect = voucherRepository.findAll().size();
        assertThat(sizeExpect).isEqualTo(1);

        //2. 데이터베이스에 없는 값인지 확인
        Optional<Voucher> optionalVoucher = voucherRepository.findById(deleteVoucherId);
        assertThat(optionalVoucher).isEqualTo(Optional.empty());

    }
}


