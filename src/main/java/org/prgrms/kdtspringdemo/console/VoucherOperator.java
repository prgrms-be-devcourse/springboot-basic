package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.VoucherType;
import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.voucher.Voucher;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.wallet.WalletService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class VoucherOperator implements CommandOperator<Voucher> {
    private final VoucherService voucherService;
    private final WalletService walletService;
    private final CustomerService customerService;

    public VoucherOperator(VoucherService voucherService, WalletService walletService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.walletService = walletService;
        this.customerService = customerService;
    }

    // todo : voucher 생성 코드를 service 내부에서 처리하기
    @Transactional
    @Override
    public boolean create(String[] splitList) {
        if (!validationCreateCheck(splitList)) return false;
        var customerId = splitList[0];
        var voucherType = splitList[1];
        var value = splitList[2];

        Voucher voucher = voucherService.saveVoucher(voucherType, value);
        walletService.addVoucher(customerId, voucher.getVoucherId());
        return voucher != null;
    }

    @Transactional
    @Override
    public void delete(String[] splitList) {
        // todo: validate 에러 처리하기
        if (!validationDeleteCheck(splitList)) return;
        var voucherId = splitList[0];

        walletService.deleteVoucher(voucherId);
        voucherService.deleteVoucher(voucherId);
    }

    @Override
    public List<Voucher> getAllitems() {
        return voucherService.getAllVouchers();
    }

    public boolean validationCreateCheck(String[] splitList) {
        if (splitList.length != 3) return false;
        if (!VoucherType.isInVoucherType(splitList[1])) return false;
        if (!splitList[2].matches("^[0-9]*$")) return false;

        return true;
    }

    private boolean validationDeleteCheck(String[] splitList) {
        if (splitList.length != 1) return false;
        return true;
    }

    public List<Voucher> findVouchers(String[] splitList) {
        if (!validationFindCheck(splitList)) return null;
        var customerId = splitList[0];
        List<Voucher> vouchers = voucherService.findVouchers(customerId);
        return vouchers;
    }

    private boolean validationFindCheck(String[] splitList) {
        if (splitList.length != 1) return false;
        return true;
    }
}
