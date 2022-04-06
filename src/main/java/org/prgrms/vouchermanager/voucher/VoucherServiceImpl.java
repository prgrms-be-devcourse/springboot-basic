package org.prgrms.vouchermanager.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher createVoucher(String type, long amount) {
        Voucher voucher = VoucherFactory.getVoucher(type, amount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    @Override
    public String getVouchersLists() {
        StringBuilder sb = new StringBuilder();
        voucherRepository.getAll().forEach(v -> sb.append(sb).append("\n"));
        return sb.toString();
    }

    @Override
    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    @Override
    public void useVoucher(Voucher voucher) {

    }
}
