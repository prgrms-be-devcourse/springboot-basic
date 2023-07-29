package org.programmers.VoucherManagement.voucher.infrastructure;

import com.github.f4b6a3.ulid.UlidCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.voucher.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({JdbcVoucherStoreRepository.class, JdbcVoucherReaderRepository.class})
public class JdbcVoucherRepositoryTest {
    @Autowired
    private JdbcVoucherReaderRepository voucherReaderRepository;

    @Autowired
    private JdbcVoucherStoreRepository voucherStoreRepository;

    private Voucher fixedVoucher;
    private Voucher percentVoucher;

    @BeforeEach
    void initVoucher() {
        fixedVoucher = new FixedAmountVoucher(UlidCreator.getUlid().toString(), DiscountType.FIXED, new DiscountValue(1000));
        percentVoucher = new PercentAmountVoucher(UlidCreator.getUlid().toString(), DiscountType.PERCENT, new DiscountValue(10));
    }

    @Test
    @DisplayName("Percent 바우처를 생성할 수 있다.")
    void insert_PercentVoucher_EqualsNewPercentVoucher() {
        //given
        voucherStoreRepository.insert(percentVoucher);

        //when
        Voucher voucherExpect = voucherReaderRepository.findById(percentVoucher.getVoucherId()).get();

        //then
        assertThat(voucherExpect).usingRecursiveComparison().isEqualTo(percentVoucher);
    }

    @Test
    @DisplayName("Fixed 바우처를 생성할 수 있다.")
    void insert_FixedVoucher_EqualsNewFixedVoucher() {
        //given
        voucherStoreRepository.insert(fixedVoucher);

        //when
        Voucher voucherExpect = voucherReaderRepository.findById(fixedVoucher.getVoucherId()).get();

        //then
        assertThat(voucherExpect).usingRecursiveComparison().isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("Fixed 바우처 금액을 수정할 수 있다. - 성공")
    void update_FixedVoucher_EqualsUpdateFixedVoucher() {
        //given
        voucherStoreRepository.insert(fixedVoucher);
        fixedVoucher.changeDiscountValue(new DiscountValue(2000));
        voucherStoreRepository.update(fixedVoucher);

        //when
        Voucher voucherExpect = voucherReaderRepository.findById(fixedVoucher.getVoucherId()).get();

        //then
        assertThat(voucherExpect).usingRecursiveComparison().isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("Percent 바우처 금액을 수정할 수 있다. - 성공")
    void update_PercentVoucher_EqualsUpdatePercentVoucher() {
        //given
        voucherStoreRepository.insert(percentVoucher);
        percentVoucher.changeDiscountValue(new DiscountValue(20));
        voucherStoreRepository.update(percentVoucher);

        //when
        Voucher voucherExpect = voucherReaderRepository.findById(percentVoucher.getVoucherId()).get();

        //then
        assertThat(voucherExpect).usingRecursiveComparison().isEqualTo(percentVoucher);
    }


    @Test
    @DisplayName("id를 이용해 Percent 바우처를 조회할 수 있다. - 성공")
    void findById_VoucherID_EqualsFindVoucher() {
        //given
        String findVoucherId = percentVoucher.getVoucherId();
        voucherStoreRepository.insert(percentVoucher);
        voucherStoreRepository.insert(fixedVoucher);

        //when
        Voucher voucherExpect = voucherReaderRepository.findById(findVoucherId).get();

        //then
        assertThat(voucherExpect).usingRecursiveComparison().isEqualTo(percentVoucher);
    }

    @Test
    @DisplayName("등록된 바우처를 전체 조회할 수 있다. - 성공")
    void findAll_Success() {
        //given
        voucherStoreRepository.insert(percentVoucher);
        voucherStoreRepository.insert(fixedVoucher);

        //when
        List<Voucher> voucherList = voucherReaderRepository.findAll();

        //then
        assertThat(voucherList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("등록된 바우처를 삭제할 수 있다. - 성공")
    void delete_VoucherId_Success() {
        //given
        voucherStoreRepository.insert(percentVoucher);
        voucherStoreRepository.insert(fixedVoucher);
        String deleteVoucherId = percentVoucher.getVoucherId();

        //when
        voucherStoreRepository.delete(deleteVoucherId);

        //then
        //1. 사이즈 비교
        int sizeExpect = voucherReaderRepository.findAll().size();
        assertThat(sizeExpect).isEqualTo(1);

        //2. 데이터베이스에 없는 값인지 확인
        Optional<Voucher> optionalVoucher = voucherReaderRepository.findById(deleteVoucherId);
        assertThat(optionalVoucher).isEqualTo(Optional.empty());
    }

}
