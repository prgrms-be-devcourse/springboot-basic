package org.prgrms.kdt.wallet.service;

import org.prgrms.kdt.exception.EntityNotFoundException;
import org.prgrms.kdt.member.dao.MemberRepository;
import org.prgrms.kdt.voucher.dao.VoucherRepository;
import org.prgrms.kdt.wallet.dao.WalletRepository;
import org.prgrms.kdt.wallet.domain.JoinedWallet;
import org.prgrms.kdt.wallet.domain.Wallet;
import org.prgrms.kdt.wallet.dto.request.CreateWalletRequest;
import org.prgrms.kdt.wallet.dto.response.JoinedWalletResponses;
import org.prgrms.kdt.wallet.dto.response.WalletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class WalletService {
    private final MemberRepository memberRepository;
    private final VoucherRepository voucherRepository;
    private final WalletRepository walletRepository;

    public WalletService(MemberRepository memberRepository, VoucherRepository voucherRepository, WalletRepository walletRepository) {
        this.memberRepository = memberRepository;
        this.voucherRepository = voucherRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public WalletResponse assignVoucherToCustomer(CreateWalletRequest request) {
        memberRepository.findById(request.memberId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 바우처 입니다."));
        voucherRepository.findById(request.voucherId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 바우처 입니다."));

        Wallet wallet = new Wallet(request.walletId(), request.memberId(), request.voucherId());
        return new WalletResponse(walletRepository.insert(wallet));
    }

    public JoinedWalletResponses findVouchersByMemberId(UUID memberId) {
        List<JoinedWallet> joinedWallets = walletRepository.findWithMemeberAndVoucherByMemberId(memberId);
        return JoinedWalletResponses.of(joinedWallets);
    }

    @Transactional
    public void deleteWalletById(UUID walletId) {
        walletRepository.deleteById(walletId);
    }

    public JoinedWalletResponses findMembersByVoucherId(UUID voucherId) {
        List<JoinedWallet> joinedWallets = walletRepository.findWithMemeberAndVoucherByVoucherId(voucherId);
        return JoinedWalletResponses.of(joinedWallets);
    }

    public JoinedWalletResponses findAllWallet() {
        List<JoinedWallet> joinedWallets = walletRepository.findWithMemeberAndVoucherAll();
        return JoinedWalletResponses.of(joinedWallets);
    }
}
