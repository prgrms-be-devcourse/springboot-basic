package kr.co.springbootweeklymission.voucher.domain.repository;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.member.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootweeklymission.voucher.creators.VoucherCreators;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository voucherRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Order(1)
    void save_고정_할인_바우처를_등록_SUCCESS() {
        //given
        Voucher voucher = VoucherCreators.createFixedDiscount();

        //when
        voucherRepository.save(voucher);
        Voucher actual = voucherRepository.findById(voucher.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));


        //then
        assertThat(actual).isEqualTo(voucher);
    }

    @Test
    @Order(1)
    void save_퍼센트_할인_바우처를_등록_SUCCESS() {
        //given
        Voucher voucher = VoucherCreators.createPercentDiscount();

        //when
        voucherRepository.save(voucher);
        Voucher actual = voucherRepository.findById(voucher.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));

        //then
        assertThat(actual).isEqualTo(voucher);
    }

    @Test
    @Order(2)
    void findAll_등록된_모든_바우처를_조회_SUCCESS() {
        //given
        Voucher voucher = VoucherCreators.createPercentDiscount();
        voucherRepository.save(voucher);

        //when
        List<Voucher> actual = voucherRepository.findAll();

        //then
        assertThat(actual).hasSize(3);
    }

    @Test
    @Order(2)
    void findVouchersByMemberId_특정_고객의_모든_바우처를_조회_EMPTY() {
        //given & when & then
        assertThat(voucherRepository.findVouchersByMemberId(UUID.randomUUID())).isEmpty();
    }

    @Test
    @Order(2)
    void findVouchersByMemberId_특정_고객의_모든_바우처를_조회_SUCCESS() {
        //given
        Member member = MemberCreators.createWhiteMember();
        memberRepository.save(member);
        Voucher voucher1 = VoucherCreators.createPercentDiscount();
        Voucher voucher2 = VoucherCreators.createPercentDiscount();
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucher1.assignVoucher(member);
        voucher2.assignVoucher(member);
        voucherRepository.update(voucher1);
        voucherRepository.update(voucher2);

        //when
        List<Voucher> actual = voucherRepository.findVouchersByMemberId(member.getMemberId());

        //then
        assertThat(actual).hasSize(2);
    }

    @Test
    @Order(3)
    void findById_특정_바우처를_조회_SUCCESS() {
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
    @Order(3)
    void findById_특정_바우처를_조회_EMPTY() {
        //given & when & then
        assertThat(voucherRepository.findById(UUID.randomUUID())).isEmpty();
    }

    @Test
    @Order(4)
    void update_특정_바우처를_수정_SUCCESS() {
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
    @Order(4)
    void update_특정_바우처에게_회원을_할당_SUCCESS() {
        //given
        Voucher voucher = VoucherCreators.createFixedDiscount();
        Member member = MemberCreators.createWhiteMember();
        voucherRepository.save(voucher);
        memberRepository.save(member);

        //when
        voucher.assignVoucher(member);
        voucherRepository.update(voucher);
        Voucher actual = voucherRepository.findById(voucher.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));

        //then
        assertThat(actual).isEqualTo(voucher);
    }

    @Test
    @Order(5)
    void deleteById_특정_바우처를_삭제_SUCCESS() {
        //given
        Voucher voucher = VoucherCreators.createFixedDiscount();
        voucherRepository.save(voucher);

        //when
        voucherRepository.deleteById(voucher.getVoucherId());

        //then
        assertThat(voucherRepository.findById(voucher.getVoucherId())).isEmpty();
    }

    @Test
    @Order(5)
    void deleteVoucherByVoucherIdAndMemberId_특정_회원이_가진_특정_바우처를_삭제_SUCCESS() {
        //given
        Voucher voucher = VoucherCreators.createFixedDiscount();
        Member member = MemberCreators.createWhiteMember();
        voucherRepository.save(voucher);
        memberRepository.save(member);
        voucher.assignVoucher(member);
        voucherRepository.update(voucher);

        //when
        voucherRepository.deleteVoucherByVoucherIdAndMemberId(voucher.getVoucherId(), member.getMemberId());

        //then
        assertThat(voucherRepository.findById(voucher.getVoucherId())).isEmpty();
    }
}
