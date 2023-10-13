package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherCreateFunction;
import org.prgrms.kdtspringdemo.voucher.repository.VoucherRepository;

import java.io.IOException;
import java.util.List;
import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(String type, UUID voucherId, long amount) throws IOException {
        System.out.println("create voucher");

        VoucherCreateFunction voucherType = VoucherCreateFunction.findByCode(type);
        Voucher voucher = voucherType.create(voucherId, amount);

        return voucher;
    }

    public Map<UUID, Voucher> getVoucherList() {
        System.out.println("getvoucherlist");
        return voucherRepository.getAllVouchers().get();
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
