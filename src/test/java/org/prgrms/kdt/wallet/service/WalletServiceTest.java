package org.prgrms.kdt.wallet.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.EntityNotFoundException;
import org.prgrms.kdt.member.dao.MemberRepository;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.dao.VoucherRepository;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.wallet.dao.WalletRepository;
import org.prgrms.kdt.wallet.domain.Wallet;
import org.prgrms.kdt.wallet.dto.request.CreateWalletRequest;
import org.prgrms.kdt.wallet.dto.response.WalletResponse;
import org.prgrms.kdt.wallet.dto.response.WalletsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
class WalletServiceTest {

    @Autowired
    WalletService walletService;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    VoucherRepository voucherRepository;

    @BeforeEach
    void setup(){
//         멤버 두명, 바우처 두개를 각각의 db테이블에 넣어놓고
//         멤버1에게 바우처 두개를 할당한 상태 셋팅
        setupInsertWallets();
    }

    @Test
    @DisplayName("올바른 멤버와 바우처가 담긴 request객체를 통해 월렛 할당 후 반환된 월렛안의 멤버네임 비교")
    void assignVoucherToCustomer_correctRequest_correctWallet() {
        //given
        Member member = memberRepository.insert(new Member("giho", MemberStatus.COMMON));
        Voucher voucher = voucherRepository.insert(new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0)));
        CreateWalletRequest createWalletRequest = new CreateWalletRequest(member.getMemberId(), voucher.getVoucherId());

        //when
        WalletResponse resultWallet = walletService.assignVoucherToCustomer(createWalletRequest);

        //then
        String resultMemberName = resultWallet.memberName();
        assertThat(resultMemberName).isEqualTo("giho");
    }

    @Test
    @DisplayName("존재하지 않는 바우처가 담긴 request객체를 통해 월렛 할당 시 EntityNotFoundException 확인")
    void assignVoucherToCustomer_incorrectRequest_EntityNotFoundException() {
        //given
        Member member = memberRepository.insert(new Member("giho", MemberStatus.COMMON));
        Voucher voucher = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        CreateWalletRequest createWalletRequest = new CreateWalletRequest(member.getMemberId(), voucher.getVoucherId());

        //when
        Exception exception = catchException(() -> walletService.assignVoucherToCustomer(createWalletRequest));

        //then
        assertThat(exception).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("setup을 통해 바우처 2개를 할당받은 멤버Id를 조회하여 Response의 size가 2인지 확인")
    void findVouchersByMemberId_correctMemberId_correctResponseSize() {
        //when
        WalletsResponse responseList = walletService.findVouchersByMemberId(UUID.fromString("1a3d5b3e-2d12-4958-9ef3-52d424485895"));

        //then
        int responseSize = responseList.getWallets().size();
        assertThat(responseSize).isEqualTo(2);
    }

    @Test
    @DisplayName("setup을 통해 바우처 2개를 가진 멤버1의 바우처 하나 삭제 후 멤버1의 바우처 개수 조회")
    void deleteWalletById_correctId_correctResponseSize() {
        //when
        walletService.deleteWalletById(UUID.fromString("f7c23946-7174-4f56-b464-3ed1fa5224d7"));

        //then
        List<Wallet> findWalletList = walletRepository.findByMemberId(UUID.fromString("1a3d5b3e-2d12-4958-9ef3-52d424485895"));
        assertThat(findWalletList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("setup을 통해 해당 바우처를 할당받은 james를 voucherId를 통해 찾아서 확인")
    void findMembersByVoucherId_correctVoucherId_correctMemberName() {
        //when
        WalletsResponse response = walletService.findMembersByVoucherId(UUID.fromString("3c3dda5e-eb09-4b21-b57f-d9ef54bacd29"));

        //then
        String findMemberName = response.getWallets().get(0).memberName();
        assertThat(findMemberName).isEqualTo("james");
    }

    @Test
    @DisplayName("setup을 통해 저장된 월렛2개 전체 조회를 통해 사이즈 확인")
    void findAllWallet_collectWalletSize() {
        //when
        List<Wallet> wallets = walletRepository.findAll();

        //then
        assertThat(wallets.size()).isEqualTo(2);
    }

    void setupInsertWallets(){
        UUID memberId1 = UUID.fromString("1a3d5b3e-2d12-4958-9ef3-52d424485895");
        Member member1 = new Member(memberId1,"james", MemberStatus.COMMON);
        Member member2 = new Member("lala", MemberStatus.COMMON);

        UUID voucherId1 = UUID.fromString("3c3dda5e-eb09-4b21-b57f-d9ef54bacd29");
        Voucher voucher1 = new Voucher(voucherId1, VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        Voucher voucher2 = new Voucher(VoucherType.PERCENT, VoucherType.PERCENT.createPolicy(70.0));

        UUID walletId1 = UUID.fromString("f7c23946-7174-4f56-b464-3ed1fa5224d7");
        Wallet wallet1 = new Wallet(walletId1, member1, voucher1);
        Wallet wallet2 = new Wallet(UUID.randomUUID(), member1, voucher2);

        memberRepository.insert(member1);
        memberRepository.insert(member2);
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        walletRepository.insert(wallet1);
        walletRepository.insert(wallet2);
    }
}