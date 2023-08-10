package org.prgrms.kdt.wallet.service;

import org.prgrms.kdt.global.Generator;
import org.prgrms.kdt.global.exception.EntityNotFoundException;
import org.prgrms.kdt.member.dao.MemberRepository;
import org.prgrms.kdt.voucher.dao.VoucherRepository;
import org.prgrms.kdt.wallet.dao.WalletCommandRepository;
import org.prgrms.kdt.wallet.dao.WalletQueryRepository;
import org.prgrms.kdt.wallet.domain.QueryWallet;
import org.prgrms.kdt.wallet.domain.Wallet;
import org.prgrms.kdt.wallet.service.dto.CreateWalletRequest;
import org.prgrms.kdt.wallet.service.dto.JoinedWalletResponses;
import org.prgrms.kdt.wallet.service.dto.WalletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class WalletService {
    private final MemberRepository memberRepository;
    private final VoucherRepository voucherRepository;
    private final WalletQueryRepository walletQueryRepository;
    private final WalletCommandRepository walletCommandRepository;
    private final Generator generator;

    public WalletService(MemberRepository memberRepository, VoucherRepository voucherRepository, WalletQueryRepository walletQueryRepository, WalletCommandRepository walletCommandRepository, Generator generator) {
        this.memberRepository = memberRepository;
        this.voucherRepository = voucherRepository;
        this.walletQueryRepository = walletQueryRepository;
        this.walletCommandRepository = walletCommandRepository;
        this.generator = generator;
    }

    @Transactional
    public WalletResponse assignVoucherToCustomer(CreateWalletRequest request) {
        memberRepository.findById(request.memberId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 멤버 입니다."));
        voucherRepository.findById(request.voucherId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 바우처 입니다."));

        Wallet wallet = new Wallet(generator.generateId(), request.memberId(), request.voucherId());
        return new WalletResponse(walletCommandRepository.insert(wallet));
    }

    public JoinedWalletResponses findVouchersByMemberId(UUID memberId) {
        List<QueryWallet> queryWallets = walletQueryRepository.findWithMemeberAndVoucherByMemberId(memberId);
        return JoinedWalletResponses.of(queryWallets);
    }

    @Transactional
    public void deleteWalletById(UUID walletId) {
        walletCommandRepository.deleteById(walletId);
    }

    public JoinedWalletResponses findMembersByVoucherId(UUID voucherId) {
        List<QueryWallet> queryWallets = walletQueryRepository.findWithMemeberAndVoucherByVoucherId(voucherId);
        return JoinedWalletResponses.of(queryWallets);
    }

    public JoinedWalletResponses findAllWallet() {
        List<QueryWallet> queryWallets = walletQueryRepository.findWithMemeberAndVoucherAll();
        return JoinedWalletResponses.of(queryWallets);
    }
}
