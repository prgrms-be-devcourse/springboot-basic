package kr.co.springbootweeklymission.voucher.domain.repository;

import kr.co.springbootweeklymission.common.error.exception.NotFoundException;
import kr.co.springbootweeklymission.common.response.ResponseStatus;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootweeklymission.voucher.creators.VoucherCreators;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcVoucherRepositoryTest {
    @Autowired
    JdbcVoucherRepository voucherRepository;
    @Autowired
    MemberRepository memberRepository;

    Voucher voucher1;
    Voucher voucher2;
    Voucher voucher3;

    @BeforeAll
    void beforeAll() {
        voucherRepository.deleteAll();
        voucher1 = VoucherCreators.createFixedDiscount();
        voucher2 = VoucherCreators.createPercentDiscount();
        voucher3 = VoucherCreators.createPercentDiscount();
    }

    @Test
    @Order(1)
    void save_고정_할인_바우처를_등록_SUCCESS() {
        //given & when
        voucherRepository.save(voucher1);
        Voucher actual = voucherRepository.findById(voucher1.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));

        //then
        assertThat(actual).isEqualTo(voucher1);
    }

    @Test
    @Order(2)
    void save_퍼센트_할인_바우처를_등록_SUCCESS() {
        //given & when
        voucherRepository.save(voucher2);
        Voucher actual = voucherRepository.findById(voucher2.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));

        //then
        assertThat(actual).isEqualTo(voucher2);
    }

    @Test
    @Order(3)
    void findAll_등록된_모든_바우처를_조회_SUCCESS() {
        //given
        voucherRepository.save(voucher3);

        //when
        List<Voucher> actual = voucherRepository.findAll();

        //then
        assertThat(actual).hasSize(3);
    }

    @Test
    @Order(4)
    void findById_특정_바우처를_조회_SUCCESS() {
        //given & when
        Voucher actual = voucherRepository.findById(voucher1.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));

        //then
        assertThat(actual).isEqualTo(voucher1);
    }

    @Test
    @Order(5)
    void findById_특정_바우처를_조회_EMPTY() {
        //given & when & then
        assertThat(voucherRepository.findById(UUID.randomUUID())).isEmpty();
    }

    @Test
    @Order(6)
    void update_특정_바우처를_수정_SUCCESS() {
        //given
        Voucher updateVoucher = VoucherCreators.updateVoucher(voucher1.getVoucherId(), 20, VoucherPolicy.PERCENT_DISCOUNT);

        //when
        voucherRepository.update(updateVoucher);
        Voucher actual = voucherRepository.findById(voucher1.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));

        //then
        assertThat(actual).isEqualTo(updateVoucher);
    }

    @Test
    @Order(7)
    void deleteById_특정_바우처를_삭제_SUCCESS() {
        //given & when
        voucherRepository.delete(voucher1);

        //then
        assertThat(voucherRepository.findById(voucher1.getVoucherId())).isEmpty();
    }
}
