package org.devcourse.springbasic.menu;

import org.devcourse.springbasic.io.IODevice;
import org.devcourse.springbasic.voucher.Voucher;
import org.devcourse.springbasic.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RunByHistoryMenu implements RunByMenu {

    private final IODevice ioDevice;
    private final VoucherRepository voucherRepository;

    public RunByHistoryMenu(IODevice ioDevice, VoucherRepository voucherRepository) {
        this.ioDevice = ioDevice;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void run() {
        List<Voucher> vouchers = voucherRepository.findAll();
        ioDevice.outputVouchers(vouchers);

    }
}
