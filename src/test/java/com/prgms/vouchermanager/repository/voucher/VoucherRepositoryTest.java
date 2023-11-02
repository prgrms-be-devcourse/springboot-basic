package com.prgms.vouchermanager.repository.voucher;

import com.prgms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
public class VoucherRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    @DisplayName("save()실행시 정상적으로 저장된다. ")
    void saveVoucherSuccess() {

        //given

        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100000, null);

        //when
        Voucher savedVoucher = voucherRepository.save(voucher);

        //then
        Assertions.assertThat(voucher).isEqualTo(savedVoucher);
    }

    @Test
    @DisplayName("save()실행시 ID가 중복될 경우 예외가 발생한다.")
    void saveVoucherFail() {

        //given
        UUID id = UUID.randomUUID();
        Voucher voucher1 = new FixedAmountVoucher(id, 100000, null);
        Voucher voucher2 = new PercentDiscountVoucher(id, 10, null);

        //when
        voucherRepository.save(voucher1);

        //then
        Assertions.assertThatThrownBy(() -> voucherRepository.save(voucher2)).isInstanceOf(DuplicateKeyException.class);

    }

    @Test
    @DisplayName("update()를 이용해 정보수정을 자유롭게 할수 있다.")
    void updateVoucherSuccess() {

        //given
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(id, 100000, LocalDateTime.now());
        Voucher savedVoucher = voucherRepository.save(voucher);
        Voucher updatedVoucher = new FixedAmountVoucher(savedVoucher.getId(), 9999, LocalDateTime.now());

        //when
        voucherRepository.update(updatedVoucher);

        Optional<Voucher> findVoucher = voucherRepository.findById(updatedVoucher.getId());

        //then
        Assertions.assertThat(updatedVoucher.getId()).isEqualTo(findVoucher.get().getId());
        Assertions.assertThat(updatedVoucher.getVoucherType()).isEqualTo(findVoucher.get().getVoucherType());
        Assertions.assertThat(updatedVoucher.getDiscountValue()).isEqualTo(findVoucher.get().getDiscountValue());

    }

    @Test
    @DisplayName("findById() 를 통해 Voucher를 찾아올 수 있다.")
    void findByIdVoucherSuccess() {

        //given
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(id, 100000, LocalDateTime.now());

        Voucher savedCustomer = voucherRepository.save(voucher);

        Optional<Voucher> findByIdVoucher = voucherRepository.findById(savedCustomer.getId());

        //then
        Assertions.assertThat(voucher.getId()).isEqualTo(findByIdVoucher.get().getId());
        Assertions.assertThat(voucher.getDiscountValue()).isEqualTo(findByIdVoucher.get().getDiscountValue());
        Assertions.assertThat(voucher.getVoucherType()).isEqualTo(findByIdVoucher.get().getVoucherType());
    }

    @Test
    @DisplayName("findById 를 실행시 없는 ID를 조회하면, 빈 값이 넘어온다.")
    void findByIdVoucherFail() {

        //given
        UUID notExistId = UUID.randomUUID();

        //when
        Optional<Voucher> findById = voucherRepository.findById(notExistId);

        //then
        Assertions.assertThat(findById).isEmpty();

    }

    @Test
    @DisplayName("findAll()실행시 성공적으로 모든 목록을 조회한다.")
    void findAllVoucherSuccess() {

        //given
        //when
        List<Voucher> all = voucherRepository.findAll();

        //then
        Assertions.assertThat(all).isNotNull();

    }

    @Test
    @DisplayName("deleteById()실행 시 customer를 성공적으로 삭제한다.")
    void deleteByVoucherSuccess() {

        //given
        UUID id = UUID.randomUUID();
        voucherRepository.save(new FixedAmountVoucher(id, 1000, LocalDateTime.now()));
        //when
        voucherRepository.deleteById(id);
        Optional<Voucher> findById = voucherRepository.findById(id);

        //then
        Assertions.assertThat(findById).isEmpty();
    }

}
