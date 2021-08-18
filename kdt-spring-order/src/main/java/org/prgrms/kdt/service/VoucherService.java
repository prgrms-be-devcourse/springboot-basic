package org.prgrms.kdt.service;

import org.prgrms.kdt.configure.FixedAmountVoucher;
import org.prgrms.kdt.configure.PercentDiscountVoucher;
import org.prgrms.kdt.configure.Voucher;
import org.prgrms.kdt.repo.VoucherMemoryRepo;
import org.prgrms.kdt.repo.VoucherRepository;

import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final VoucherMemoryRepo voucherMemoryRepo;

    public VoucherService(VoucherMemoryRepo voucherMemoryRepo) {
        this.voucherMemoryRepo = voucherMemoryRepo;
    }


    public Voucher getVoucher(UUID voucherId) {
        return voucherMemoryRepo.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(String.format("Can not find a voucher %s", voucherId)));

    }

    //사용안함
    public void useVoucher(Voucher voucher){
    }

    public void createVoucher(UUID voucherId, long discount, int voucherType){
        if(voucherType == 1){
            voucherMemoryRepo.save(new FixedAmountVoucher(voucherId,discount));
        }else{
            voucherMemoryRepo.save(new PercentDiscountVoucher(voucherId,discount));
        }
    }

    public List<Voucher> findAll() {
        return voucherMemoryRepo.findAll();
    }

}
