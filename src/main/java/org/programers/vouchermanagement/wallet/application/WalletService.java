package org.programers.vouchermanagement.wallet.application;

import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.member.domain.MemberRepository;
import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.programers.vouchermanagement.voucher.domain.VoucherRepository;
import org.programers.vouchermanagement.wallet.domain.Wallet;
import org.programers.vouchermanagement.wallet.domain.WalletRepository;
import org.programers.vouchermanagement.wallet.dto.request.WalletCreationRequest;
import org.programers.vouchermanagement.wallet.dto.response.WalletResponse;
import org.programers.vouchermanagement.wallet.dto.response.WalletsResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final VoucherRepository voucherRepository;
    private final MemberRepository memberRepository;

    public WalletService(WalletRepository walletRepository, VoucherRepository voucherRepository, MemberRepository memberRepository) {
        this.walletRepository = walletRepository;
        this.voucherRepository = voucherRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public WalletResponse save(WalletCreationRequest request) {
        Voucher voucher = voucherRepository.getById(request.getVoucherId());
        Member member = memberRepository.getById(request.getMemberId());

        Wallet savingWallet = new Wallet(voucher, member);
        Wallet wallet = walletRepository.save(savingWallet);
        return new WalletResponse(wallet);
    }

    public WalletResponse findById(UUID id) {
        Wallet wallet = walletRepository.getById(id);
        return new WalletResponse(wallet);
    }

    public WalletsResponse findByVoucherId(UUID voucherId) {
        List<Wallet> wallets = walletRepository.findAllByVoucherId(voucherId);
        List<WalletResponse> responses = wallets.stream()
                .map(WalletResponse::new)
                .collect(Collectors.toList());
        return new WalletsResponse(responses);
    }

    public WalletsResponse findByMemberId(UUID memberId) {
        List<Wallet> wallets = walletRepository.findAllByMemberId(memberId);
        List<WalletResponse> responses = wallets.stream()
                .map(WalletResponse::new)
                .collect(Collectors.toList());
        return new WalletsResponse(responses);
    }

    public WalletsResponse findAll() {
        List<Wallet> wallets = walletRepository.findAll();
        List<WalletResponse> responses = wallets.stream()
                .map(WalletResponse::new)
                .collect(Collectors.toList());
        return new WalletsResponse(responses);
    }

    @Transactional
    public void deleteById(UUID id) {
        walletRepository.deleteById(id);
    }
}
