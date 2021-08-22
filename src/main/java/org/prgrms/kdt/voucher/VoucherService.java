package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<Voucher> save(VoucherType voucherType, long size){
        var newVoucher = Voucher.voucherFactory(voucherType, size);

        if(newVoucher.isPresent())
            voucherRepository.save(newVoucher.get());

        return newVoucher;
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.getAllVouchers();
    }
}
