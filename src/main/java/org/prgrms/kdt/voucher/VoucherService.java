package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("can not find a voucher for {0}" + voucherId));
    }

    public Voucher save(VoucherType voucherType, long size) {
        Voucher newVoucher = Voucher.voucherFactory(voucherType, size);

        if(newVoucher == null){
            return null;
        }
        voucherRepository.save(newVoucher);

        return newVoucher;
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.getAllVouchers();
    }

    public void useVoucher(Voucher voucher) {
        // TODO:
    }
}
