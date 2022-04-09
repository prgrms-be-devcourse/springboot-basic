package org.prgms.voucher.service;

import org.prgms.voucher.Voucher;
import org.prgms.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
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
