package kr.co.springbootweeklymission.voucher.domain.repository;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.voucher.domain.creators.VoucherCreators;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Test
    @Order(1)
    void 고정_할인_바우처를_등록() {
        //given
        Voucher voucher = VoucherCreators.createFixedDiscount();

        //when
        Voucher actual = voucherRepository.save(voucher);

        //then
        assertThat(actual.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    @Order(2)
    void 등록된_모든_바우처를_조회() {
        //given
        Voucher voucher = VoucherCreators.createPercentDiscount();
        voucherRepository.save(voucher);

        //when
        List<Voucher> actual = voucherRepository.findAll();

        //then
        assertThat(actual).hasSize(2);
    }

    @Test
    @Order(3)
    void 특정_바우처를_조회() {
        //given
        Voucher voucher = VoucherCreators.createPercentDiscount();
        voucherRepository.save(voucher);

        //when
        Voucher actual = voucherRepository.findById(voucher.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));

        //then
        assertThat(actual).isEqualTo(voucher);
    }

    @Test
    @Order(4)
    void 특정_바우처를_수정() {
        //given
        Voucher voucher = VoucherCreators.createFixedDiscount();
        voucherRepository.save(voucher);
        Voucher updateVoucher = VoucherCreators.updateVoucher(voucher.getVoucherId(), 20, VoucherPolicy.PERCENT_DISCOUNT);

        //when
        voucherRepository.update(updateVoucher);
        Voucher actual = voucherRepository.findById(voucher.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));

        //then
        assertThat(actual).isEqualTo(updateVoucher);
    }

    @Test
    @Order(5)
    void 특정_바우처를_삭제() {
        //given
        Voucher voucher = VoucherCreators.createFixedDiscount();
        voucherRepository.save(voucher);

        //when
        voucherRepository.deleteById(voucher.getVoucherId());

        //then
        assertThat(voucherRepository.findById(voucher.getVoucherId())).isEmpty();
    }
}
