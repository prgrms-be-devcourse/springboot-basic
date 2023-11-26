package com.weeklyMission.wallet.service;

import com.weeklyMission.exception.AlreadyExistsException;
import com.weeklyMission.exception.ExceptionMessage;
import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.wallet.domain.Wallet;
import com.weeklyMission.wallet.dto.WalletRequest;
import com.weeklyMission.wallet.dto.WalletResponse;
import com.weeklyMission.wallet.repository.WalletRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletFacade walletFacade;

    public WalletService(WalletRepository walletRepository, WalletFacade walletFacade) {
        this.walletRepository = walletRepository;
        this.walletFacade = walletFacade;
    }

    public WalletResponse save(WalletRequest wallet){
        Wallet walletEntity = wallet.toEntity();
        if(walletRepository.checkWallet(walletEntity)){
            throw new AlreadyExistsException(ExceptionMessage.ALREADY_EXIST_WALLET.getMessage());
        }
        Wallet createWallet = walletRepository.save(walletEntity);
        return WalletResponse.of(createWallet);
    }

    public List<VoucherResponse> findVoucherByMember(String memberId){
        List<String> voucherIdList = walletRepository.findByMemberId(memberId).stream()
            .map(Wallet::voucherId)
            .toList();

        return walletFacade.findVoucher(voucherIdList);
    }

    public List<MemberResponse> findMemberByVoucher(String voucherId){
        List<String> memberIdList = walletRepository.findByVoucherId(voucherId).stream()
            .map(Wallet::memberId)
            .toList();

        return walletFacade.findMember(memberIdList);
    }

    public void deleteById(String memberId, String voucherId){
        walletRepository.deleteById(memberId, voucherId);
    }
}
