package org.prgrms.voucherprgrms.voucher;

import org.prgrms.voucherprgrms.voucher.entity.VoucherType;
import org.prgrms.voucherprgrms.voucher.entity.Voucher;
import org.prgrms.voucherprgrms.voucher.repository.VoucherRepository;

public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherCreator voucherCreator;

    public VoucherService(VoucherRepository voucherRepository, VoucherCreator voucherCreator) {
        this.voucherRepository = voucherRepository;
        this.voucherCreator = voucherCreator;
    }


    /**
     * create Voucher
     */
    public Voucher createVoucher(VoucherType type) {
        Voucher voucher = voucherCreator.create(type);
        return voucherRepository.insert(voucher);
    }


}
