package org.prgrms.vouchermanager.testdata;

import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;

import java.util.UUID;

public class WalletData {
    public static WalletRequestDto getWalletDto(){
        return WalletRequestDto.builder().customerEmail("dlswns@naver.com").voucher(VoucherData.getFixedVoucher()).build();
    }
    public static Wallet getWallet(){
        return new Wallet(1, "dlswns2480@naver.com", UUID.randomUUID());
    }
}
