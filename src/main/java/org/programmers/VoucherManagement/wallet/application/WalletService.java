package org.programmers.VoucherManagement.wallet.application;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.exception.MemberException;
import org.programmers.VoucherManagement.member.infrastructure.MemberRepository;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherRepository;
import org.programmers.VoucherManagement.wallet.domain.Wallet;
import org.programmers.VoucherManagement.wallet.dto.CreateWalletRequest;
import org.programmers.VoucherManagement.wallet.dto.GetWalletListResponse;
import org.programmers.VoucherManagement.wallet.infrastructure.WalletRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.programmers.VoucherManagement.member.exception.MemberExceptionMessage.NOT_FOUND_MEMBER;
import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_FOUND_VOUCHER;

@Component
public class WalletService {
    private final WalletRepository walletRepository;
    private final VoucherRepository voucherRepository;
    private final MemberRepository memberRepository;

    public WalletService(WalletRepository walletRepository, VoucherRepository voucherRepository, MemberRepository memberRepository) {
        this.walletRepository = walletRepository;
        this.voucherRepository = voucherRepository;
        this.memberRepository = memberRepository;
    }

    public void createWallet(CreateWalletRequest createWalletRequest) {
        Voucher voucher = voucherRepository
                .findById(UUID.fromString(createWalletRequest.getVoucherId()))
                .orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));
        Member member = memberRepository
                .findById(UUID.fromString(createWalletRequest.getMemberId()))
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        Wallet wallet = new Wallet(UUID.randomUUID(),
                voucher,
                member);

        walletRepository.insert(wallet);
    }

    public GetWalletListResponse getWalletsByVoucherId(UUID voucherId) {
        return new GetWalletListResponse(walletRepository.findAllByVoucherId(voucherId));
    }

    public GetWalletListResponse getWalletsByMemberId(UUID memberId) {
        return new GetWalletListResponse(walletRepository.findAllByMemberId(memberId));
    }

    public void deleteWallet(UUID walletId) {
        walletRepository.delete(walletId);
    }
}
