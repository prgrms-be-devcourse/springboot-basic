package com.example.demo.repository.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.domain.voucher.FixedAmountVoucher;
import com.example.demo.domain.voucher.PercentDiscountVoucher;
import com.example.demo.domain.voucher.Voucher;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class VoucherJdbcRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    @DisplayName("바우처(고정, 퍼센트)를 생성하여 데이터베이스에 저장할 수 있다.")
    void save() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        UUID voucherId2 = UUID.randomUUID();

        Voucher voucherExpected1 = new FixedAmountVoucher(voucherId1, 3000);
        Voucher voucherExpected2 = new PercentDiscountVoucher(voucherId2, 30);

        // when
        Voucher voucherActual1 = voucherRepository.save(voucherExpected1);
        Voucher voucherActual2 = voucherRepository.save(voucherExpected2);

        // then
        assertThat(voucherActual1).usingRecursiveComparison().isEqualTo(voucherExpected1);
        assertThat(voucherActual2).usingRecursiveComparison().isEqualTo(voucherExpected2);
    }

    @Test
    @DisplayName("바우처(고정, 퍼센트)를 저장한 뒤, 저장한 바우처의 ID로 조회하여 바우처을 가져올 수 있다.")
    void findById() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        UUID voucherId2 = UUID.randomUUID();

        Voucher voucherExpected1 = new FixedAmountVoucher(voucherId1, 3000);
        Voucher voucherExpected2 = new PercentDiscountVoucher(voucherId2, 30);

        // when
        voucherRepository.save(voucherExpected1);
        voucherRepository.save(voucherExpected2);
        Voucher voucherActual1 = voucherRepository.findById(voucherId1)
                .orElseThrow(() -> new NoSuchElementException("찾는 바우처가 없습니다."));
        Voucher voucherActual2 = voucherRepository.findById(voucherId2)
                .orElseThrow(() -> new NoSuchElementException("찾는 바우처가 없습니다."));

        // then
        assertThat(voucherActual1).usingRecursiveComparison().isEqualTo(voucherExpected1);
        assertThat(voucherActual2).usingRecursiveComparison().isEqualTo(voucherExpected2);
    }

    @Test
    @DisplayName("여러 바우처(고정1, 퍼센트1)을 저장한 뒤, 바우처 리스트를 조회할 수 있다.")
    void findAll() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        UUID voucherId2 = UUID.randomUUID();

        Voucher voucher1 = new FixedAmountVoucher(voucherId1, 3000);
        Voucher voucher2 = new PercentDiscountVoucher(voucherId2, 30);

        List<Voucher> voucherListExpect = List.of(
                voucher1,
                voucher2
        );

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        List<Voucher> voucherListActual = voucherRepository.findAll();

        // then
        assertThat(voucherListActual)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(voucherListExpect);
    }


    @Test
    @DisplayName("데이터베이스에 저장된 바우처(고정, 퍼센트)의 금액을 업데이트 할 수 있다.")
    void update() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        UUID voucherId2 = UUID.randomUUID();

        Voucher voucher1 = new FixedAmountVoucher(voucherId1, 3000);
        Voucher voucher2 = new PercentDiscountVoucher(voucherId2, 30);

        int newAmount1 = 5000;
        int newAmount2 = 50;

        Voucher voucherExpected1 = new FixedAmountVoucher(voucherId1, newAmount1);
        Voucher voucherExpected2 = new PercentDiscountVoucher(voucherId2, newAmount2);

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.updateAmount(voucher1.getId(), newAmount1);
        voucherRepository.updateAmount(voucher2.getId(), newAmount2);
        Voucher voucherActual1 = voucherRepository.findById(voucherExpected1.getId())
                .orElseThrow(() -> new NoSuchElementException("찾는 바우처이 없습니다."));
        Voucher voucherActual2 = voucherRepository.findById(voucherExpected2.getId())
                .orElseThrow(() -> new NoSuchElementException("찾는 바우처이 없습니다."));

        // then
        assertThat(voucherActual1).usingRecursiveComparison().isEqualTo(voucherExpected1);
        assertThat(voucherActual2).usingRecursiveComparison().isEqualTo(voucherExpected2);
    }

    @Test
    @DisplayName("바우처를 저장하고 저장한 바우처의 ID를 데이터베이스에서 찾아 삭제할 수 있다.")
    void deleteById() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        UUID voucherId2 = UUID.randomUUID();

        Voucher voucher1 = new FixedAmountVoucher(voucherId1, 3000);
        Voucher voucher2 = new PercentDiscountVoucher(voucherId2, 30);

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.deleteById(voucher1.getId());
        voucherRepository.deleteById(voucher2.getId());
        Optional<Voucher> voucherActual1 = voucherRepository.findById(voucher1.getId());
        Optional<Voucher> voucherActual2 = voucherRepository.findById(voucher2.getId());

        // then
        assertThat(voucherActual1).isEmpty();
        assertThat(voucherActual2).isEmpty();
    }

}