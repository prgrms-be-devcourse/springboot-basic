package org.prgrms.kdt.command.service.voucher;

import org.prgrms.kdt.command.service.CommandService;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherListService implements CommandService {

    private final VoucherService voucherService;

    public VoucherListService(final VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void commandRun() {
        final List<Voucher> allVoucherList = voucherService.getAllVoucher();
        for (final Voucher voucher : allVoucherList) {
            System.out.println(voucher);
        }
    }
}
