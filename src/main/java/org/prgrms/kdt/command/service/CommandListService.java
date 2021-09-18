package org.prgrms.kdt.command.service;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandListService implements CommandService {

    private final VoucherService voucherService;

    public CommandListService(final VoucherService voucherService) {
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
