package org.programmers.VoucherManagement.wallet.application;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.exception.MemberException;
import org.programmers.VoucherManagement.member.infrastructure.MemberReaderRepository;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherReaderRepository;
import org.programmers.VoucherManagement.wallet.application.dto.WalletCreateRequest;
import org.programmers.VoucherManagement.wallet.application.dto.WalletCreateResponse;
import org.programmers.VoucherManagement.wallet.application.dto.WalletGetResponses;
import org.programmers.VoucherManagement.wallet.application.mapper.WalletServiceMapper;
import org.programmers.VoucherManagement.wallet.domain.Wallet;
import org.programmers.VoucherManagement.wallet.infrastructure.WalletReaderRepository;
import org.programmers.VoucherManagement.wallet.infrastructure.WalletStoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.programmers.VoucherManagement.global.response.ErrorCode.NOT_FOUND_MEMBER;
import static org.programmers.VoucherManagement.global.response.ErrorCode.NOT_FOUND_VOUCHER;

@Service
@Transactional(readOnly = true)
public class WalletService {
    private final WalletReaderRepository walletReaderRepository;
    private final WalletStoreRepository walletStoreRepository;
    private final MemberReaderRepository memberReaderRepository;
    private final VoucherReaderRepository voucherReaderRepository;

    public WalletService(WalletReaderRepository walletReaderRepository
            , WalletStoreRepository walletStoreRepository
            , MemberReaderRepository memberReaderRepository
            , VoucherReaderRepository voucherReaderRepository) {
        this.walletReaderRepository = walletReaderRepository;
        this.walletStoreRepository = walletStoreRepository;
        this.memberReaderRepository = memberReaderRepository;
        this.voucherReaderRepository = voucherReaderRepository;
    }

    @Transactional
    public WalletCreateResponse createWallet(WalletCreateRequest walletCreateRequest) {
        Voucher voucher = voucherReaderRepository
                .findById(UUID.fromString(walletCreateRequest.voucherId()))
                .orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));
        Member member = memberReaderRepository
                .findById(walletCreateRequest.memberId())
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        Wallet wallet = new Wallet(UUID.randomUUID(),
                voucher,
                member);

        walletStoreRepository.insert(wallet);

        return WalletServiceMapper.INSTANCE.domainToCreateResponse(wallet);
    }

    public WalletGetResponses getWalletsByVoucherId(UUID voucherId) {
        return new WalletGetResponses(walletReaderRepository.findAllByVoucherId(voucherId));
    }

    public WalletGetResponses getWalletsByMemberId(String memberId) {
        return new WalletGetResponses(walletReaderRepository.findAllByMemberId(memberId));
    }

    @Transactional
    public void deleteWallet(UUID walletId) {
        walletStoreRepository.delete(walletId);
    }
}
