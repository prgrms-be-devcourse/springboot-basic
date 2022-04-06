package org.prgms.service;

import org.prgms.repository.MemoryVoucherRepository;
import org.prgms.repository.VoucherRepository;
import org.prgms.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(@Qualifier("file") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(Voucher voucher){
        voucherRepository.save(voucher);
    }

    public void listVoucher(){
        List<Voucher> vouchers = voucherRepository.findAll();
        vouchers.forEach(System.out::println);
    }
}
