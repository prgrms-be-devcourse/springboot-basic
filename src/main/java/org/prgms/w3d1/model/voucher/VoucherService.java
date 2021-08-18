package org.prgms.w3d1.model.voucher;

import org.prgms.w3d1.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory = new VoucherFactory();

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId){
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()-> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public void saveVoucher(VoucherType voucherType, long discountValue){
        voucherRepository.save(voucherFactory.createVoucher(voucherType, discountValue));
    }

    public List<Voucher> findAll(){
        return voucherRepository.findAll();
    }

    public void useVoucher(Voucher voucher) {

    }
}
