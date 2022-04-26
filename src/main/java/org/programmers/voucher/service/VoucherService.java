package org.programmers.voucher.service;

import org.programmers.voucher.domain.Voucher;
import org.programmers.voucher.domain.VoucherType;
import org.programmers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    private long amount;
    private long percent;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> listVoucher(){
        return voucherRepository.findAll();
    }

    public void makeVoucher(VoucherType voucherType, Long value){
        Voucher voucher = VoucherType.makeVoucher(voucherType, value);
        voucherRepository.save(voucher);
    }
}
