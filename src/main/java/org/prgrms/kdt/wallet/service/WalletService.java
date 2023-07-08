package org.prgrms.kdt.wallet.service;

import org.prgrms.kdt.exception.EntityNotFoundException;
import org.prgrms.kdt.member.dao.MemberRepository;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.voucher.dao.VoucherRepository;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.wallet.dao.WalletRepository;
import org.prgrms.kdt.wallet.domain.Wallet;
import org.prgrms.kdt.wallet.dto.CreateWalletRequest;
import org.prgrms.kdt.wallet.dto.WalletListResponse;
import org.prgrms.kdt.wallet.dto.WalletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public void assignVoucherToCustomer(CreateWalletRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저 입니다."));
        Voucher voucher = voucherRepository.findById(request.voucherId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 바우처 입니다."));

        walletRepository.insert(new Wallet(UUID.randomUUID(), member, voucher));
    }

    public WalletListResponse findVouchersByMemberId(UUID memberId) {
        List<Wallet> wallets = walletRepository.findByMemberId(memberId);
        return getWalletListResponse(wallets);
    }

    public void deleteWalletById(UUID walletId) {
        walletRepository.deleteById(walletId);
    }

    public WalletListResponse findMembersByVoucherId(UUID voucherId) {
        List<Wallet> wallets = walletRepository.findByVoucherId(voucherId);
        return getWalletListResponse(wallets);
    }

    public WalletListResponse findAllWallet() {
        List<Wallet> wallets = walletRepository.findAll();
        return getWalletListResponse(wallets);
    }

    private WalletListResponse getWalletListResponse(List<Wallet> wallets) {
        List<WalletResponse> walletResponses = wallets.stream()
                .map(wallet -> new WalletResponse(
                        wallet.getWalletId(),
                        wallet.getMember().getMemberName().getName(),
                        wallet.getVoucher().getVoucherType().getName(),
                        wallet.getVoucher().getDiscountPolicy().getAmount()
                ))
                .collect(Collectors.toList());

        return new WalletListResponse(walletResponses);
    }
}
