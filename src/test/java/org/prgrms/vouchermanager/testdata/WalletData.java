package org.prgrms.vouchermanager.testdata;

import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.dto.WalletRequest;

import java.util.UUID;

public class WalletData {
    public static WalletRequest getWalletDto(){
        return WalletRequest.builder().customerEmail("dlswns@naver.com").voucher(VoucherData.getFixedVoucher()).build();
    }
    public static Wallet getWallet(){
        return new Wallet(1, "dlswns2480@naver.com", UUID.randomUUID());
    }
}
