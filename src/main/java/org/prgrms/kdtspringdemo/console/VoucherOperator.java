package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.VoucherType;
import org.prgrms.kdtspringdemo.voucher.Voucher;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.wallet.WalletService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherOperator implements CommandOperator<Voucher> {
    private final VoucherService voucherService;
    private final WalletService walletService;

    public VoucherOperator(VoucherService voucherService, WalletService walletService) {
        this.voucherService = voucherService;
        this.walletService = walletService;
    }

    // todo : voucher 생성 코드를 service 내부에서 처리하기
    @Override
    public boolean create(String[] splitList) {
        if (!validationCheck(splitList)) return false;
        var customerId = splitList[0];
        var voucherType = splitList[1];
        var value = splitList[2];

        Voucher voucher = voucherService.saveVoucher(voucherType, value);
        walletService.addVoucher(customerId, voucher.getVoucherId());
        return voucher != null;
    }

    @Override
    public List<Voucher> getAllitems() {
        return voucherService.getAllVouchers();
    }

    public boolean validationCheck(String[] splitList) {
        if (splitList.length != 3) return false;
        if (!VoucherType.isInVoucherType(splitList[1])) return false;
        if (!splitList[2].matches("^[0-9]*$")) return false;

        return true;
    }
}
