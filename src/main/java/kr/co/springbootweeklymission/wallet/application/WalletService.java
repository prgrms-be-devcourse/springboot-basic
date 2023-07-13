package kr.co.springbootweeklymission.wallet.application;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.member.api.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootweeklymission.voucher.api.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.repository.VoucherRepository;
import kr.co.springbootweeklymission.wallet.api.dto.request.WalletReqDTO;
import kr.co.springbootweeklymission.wallet.domain.entity.Wallet;
import kr.co.springbootweeklymission.wallet.domain.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final VoucherRepository voucherRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createVoucherMember(WalletReqDTO.CREATE create) {
        final Voucher voucher = voucherRepository.findById(create.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        final Member member = memberRepository.findById(create.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));
        final Wallet saveWallet = Wallet.toVoucherMember(voucher, member);
        walletRepository.save(saveWallet);
    }

    public List<VoucherResDTO.READ> getVouchersByMemberId(UUID memberId) {
        final List<Wallet> wallets = walletRepository.findAllByMemberId(memberId);
        return wallets.stream()
                .map(VoucherResDTO.READ::toVoucherReadDto)
                .toList();
    }

    public MemberResDTO.READ getMemberByVoucherId(UUID voucherId) {
        final Wallet wallet = walletRepository.findByVoucherId(voucherId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_WALLET));
        return MemberResDTO.READ.toMemberReadDto(wallet);
    }

    @Transactional
    public void deleteVoucherMemberByVoucherIdAndMemberId(WalletReqDTO.DELETE delete) {
        final Voucher voucher = voucherRepository.findById(delete.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        final Member member = memberRepository.findById(delete.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));
        walletRepository.deleteByVoucherIdAndMemberId(voucher.getVoucherId(), member.getMemberId());
    }
}

