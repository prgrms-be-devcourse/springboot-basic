package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherCreateFunction;
import org.prgrms.kdtspringdemo.voucher.repository.VoucherRepository;

import java.io.IOException;
import java.util.List;
import java.text.MessageFormat;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;

    public VoucherService(VoucherRepository voucherRepository, InputConsole inputConsole, OutputConsole outputConsole) {
        this.voucherRepository = voucherRepository;
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    public Voucher createVoucher() throws IOException {
        System.out.println("create voucher");
        String voucherType = inputConsole.getString();
        Voucher voucher = null;
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher createFixedAmountVoucher(UUID voucherId, long amount) {
        return new FixedAmountVoucher(voucherId, amount);
    }

    public Voucher createPercentDiscountVoucher(UUID voucherId, long percent) {
        return new PercentDiscountVoucher(voucherId, percent);
    }

    public List<Voucher> getVoucherList() {
        System.out.println("getvoucherlist");
        return null;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not fnd a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }

    public void endVoucherService() {
        System.exit(0);
    }
}
