package org.programers.vouchermanagement.wallet.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.programers.vouchermanagement.global.exception.NoSuchEntityException;
import org.programers.vouchermanagement.member.application.MemberService;
import org.programers.vouchermanagement.member.domain.MemberStatus;
import org.programers.vouchermanagement.member.dto.request.MemberCreationRequest;
import org.programers.vouchermanagement.member.dto.response.MemberResponse;
import org.programers.vouchermanagement.voucher.application.VoucherService;
import org.programers.vouchermanagement.voucher.domain.FixedAmountPolicy;
import org.programers.vouchermanagement.voucher.domain.VoucherType;
import org.programers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.programers.vouchermanagement.wallet.dto.request.WalletCreationRequest;
import org.programers.vouchermanagement.wallet.dto.response.WalletResponse;
import org.programers.vouchermanagement.wallet.dto.response.WalletsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@Transactional(readOnly = true)
@SpringBootTest
class WithRepositoryWalletServiceTest {

    private final static VoucherCreationRequest voucherCreationRequest = new VoucherCreationRequest(
            VoucherType.FIXED_AMOUNT, 100);
    private final static MemberCreationRequest memberCreationRequest = new MemberCreationRequest(
            MemberStatus.NORMAL);

    @Autowired
    private WalletService springWalletService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private VoucherService voucherService;

    @Test
    void 지갑을_저장한다() {
        // given
        VoucherResponse voucher = voucherService.save(voucherCreationRequest);
        MemberResponse member = memberService.save(memberCreationRequest);

        // when
        WalletResponse result = springWalletService.save(
                new WalletCreationRequest(voucher.getId(), member.getId()));

        // then
        assertThat(result.getVoucher().getId()).isEqualTo(voucher.getId());
    }

    @Test
    void 지갑을_아이디로_조회한다() {
        // given
        VoucherResponse voucher = voucherService.save(voucherCreationRequest);
        MemberResponse member = memberService.save(memberCreationRequest);
        WalletResponse wallet = springWalletService.save(
                new WalletCreationRequest(voucher.getId(), member.getId()));

        // when
        WalletResponse result = springWalletService.findById(wallet.getId());

        // then
        assertThat(result.getId()).isEqualTo(wallet.getId());
    }

    @Test
    void 지갑을_바우처_아이디로_조회한다() {
        // given
        VoucherResponse voucher = voucherService.save(voucherCreationRequest);
        MemberResponse member = memberService.save(memberCreationRequest);
        springWalletService.save(new WalletCreationRequest(voucher.getId(), member.getId()));
        springWalletService.save(new WalletCreationRequest(voucher.getId(), member.getId()));

        // when
        WalletsResponse result = springWalletService.findByVoucherId(voucher.getId());

        // then
        assertThat(result.getWallets()).hasSize(2);
    }

    @Test
    void 지갑을_회원_아이디로_조회한다() {
        // given
        VoucherResponse voucher = voucherService.save(voucherCreationRequest);
        MemberResponse member = memberService.save(memberCreationRequest);
        springWalletService.save(new WalletCreationRequest(voucher.getId(), member.getId()));
        springWalletService.save(new WalletCreationRequest(voucher.getId(), member.getId()));

        // when
        WalletsResponse result = springWalletService.findByMemberId(member.getId());

        // then
        assertThat(result.getWallets()).hasSize(2);
    }

    @Test
    void 지갑을_모두_조회한다() {
        // given
        VoucherResponse voucher = voucherService.save(voucherCreationRequest);
        MemberResponse member = memberService.save(memberCreationRequest);
        springWalletService.save(new WalletCreationRequest(voucher.getId(), member.getId()));
        springWalletService.save(new WalletCreationRequest(voucher.getId(), member.getId()));

        // when
        WalletsResponse result = springWalletService.findAll();

        // then
        assertThat(result.getWallets()).hasSize(2);
    }

    @Test
    void 지갑을_삭제한다() {
        // given
        VoucherResponse voucher = voucherService.save(voucherCreationRequest);
        MemberResponse member = memberService.save(memberCreationRequest);
        WalletResponse wallet = springWalletService.save(new WalletCreationRequest(voucher.getId(), member.getId()));

        // when
        springWalletService.deleteById(wallet.getId());

        // then
        assertThatThrownBy(() -> springWalletService.findById(wallet.getId()))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessage("존재하지 않는 지갑입니다.");
    }
}
