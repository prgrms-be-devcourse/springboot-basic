package org.prgrms.kdt.wallet.service;

import org.prgrms.kdt.global.Generator;
import org.prgrms.kdt.global.exception.EntityNotFoundException;
import org.prgrms.kdt.member.dao.MemberRepository;
import org.prgrms.kdt.voucher.dao.VoucherRepository;
import org.prgrms.kdt.wallet.dao.WalletRepository;
import org.prgrms.kdt.wallet.domain.JoinedWallet;
import org.prgrms.kdt.wallet.domain.Wallet;
import org.prgrms.kdt.wallet.service.dto.CreateWalletRequest;
import org.prgrms.kdt.wallet.service.dto.JoinedWalletResponses;
import org.prgrms.kdt.wallet.service.dto.WalletResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class WalletService {
    private final MemberRepository memberRepository;
    private final VoucherRepository voucherRepository;
    private final WalletRepository walletRepository;
    private final Generator generator;

    public WalletService(MemberRepository memberRepository, VoucherRepository voucherRepository, WalletRepository walletRepository, Generator generator) {
        this.memberRepository = memberRepository;
        this.voucherRepository = voucherRepository;
        this.walletRepository = walletRepository;
        this.generator = generator;
    }

    @Transactional
    public WalletResponse assignVoucherToCustomer(CreateWalletRequest request) {
        memberRepository.findById(request.memberId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 멤버 입니다."));
        voucherRepository.findById(request.voucherId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 바우처 입니다."));

        Wallet wallet = new Wallet(generator.generateId(), request.memberId(), request.voucherId());
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
