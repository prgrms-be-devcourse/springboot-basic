package org.prgrms.vouchermission;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> list() {
        return voucherRepository.findAll();
    }

}
