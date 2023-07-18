package org.programmers.VoucherManagement.wallet.application;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.exception.MemberException;
import org.programmers.VoucherManagement.member.infrastructure.MemberRepository;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherRepository;
import org.programmers.VoucherManagement.wallet.domain.Wallet;
import org.programmers.VoucherManagement.wallet.dto.request.WalletCreateRequest;
import org.programmers.VoucherManagement.wallet.dto.response.WalletGetResponses;
import org.programmers.VoucherManagement.wallet.infrastructure.WalletRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.programmers.VoucherManagement.member.exception.MemberExceptionMessage.NOT_FOUND_MEMBER;
import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_FOUND_VOUCHER;

@Component
@Transactional(readOnly = true)
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
    public void createWallet(WalletCreateRequest walletCreateRequest) {
        Voucher voucher = voucherRepository
                .findById(UUID.fromString(walletCreateRequest.getVoucherId()))
                .orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));
        Member member = memberRepository
                .findById(UUID.fromString(walletCreateRequest.getMemberId()))
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        Wallet wallet = new Wallet(UUID.randomUUID(),
                voucher,
                member);

        walletRepository.insert(wallet);
    }

    public WalletGetResponses getWalletsByVoucherId(UUID voucherId) {
        return new WalletGetResponses(walletRepository.findAllByVoucherId(voucherId));
    }

    public WalletGetResponses getWalletsByMemberId(UUID memberId) {
        return new WalletGetResponses(walletRepository.findAllByMemberId(memberId));
    }

    @Transactional
    public void deleteWallet(UUID walletId) {
        walletRepository.delete(walletId);
    }
}
