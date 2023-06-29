package org.programers.vouchermanagement.wallet.application;

import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.member.domain.MemberRepository;
import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.programers.vouchermanagement.voucher.domain.VoucherRepository;
import org.programers.vouchermanagement.wallet.domain.Wallet;
import org.programers.vouchermanagement.wallet.domain.WalletRepository;
import org.programers.vouchermanagement.wallet.dto.WalletCreationRequest;
import org.programers.vouchermanagement.wallet.dto.WalletResponse;
import org.programers.vouchermanagement.wallet.dto.WalletsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public WalletResponse save(WalletCreationRequest request) {
        Voucher voucher = voucherRepository.getById(request.getVoucherId());
        Member member = memberRepository.getById(request.getMemberId());

        Wallet wallet = walletRepository.save(new Wallet(voucher, member));
        return new WalletResponse(wallet);
    }

    public WalletResponse findById(UUID id) {
        Wallet wallet = walletRepository.getById(id);
        return new WalletResponse(wallet);
    }

    public WalletsResponse findByVoucherId(UUID voucherId) {
        List<Wallet> wallets = walletRepository.findAllByVoucherId(voucherId);
        return new WalletsResponse(wallets.stream().map(WalletResponse::new).collect(Collectors.toList()));
    }
}
