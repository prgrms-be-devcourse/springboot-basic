package org.prgrms.voucherprgrms.voucher;

import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.prgrms.voucherprgrms.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public Voucher createVoucher() {
        Voucher voucher = voucherCreator.create();
        return voucherRepository.insert(voucher);
    }

    /**
     *
     */
    public List<Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }


}
