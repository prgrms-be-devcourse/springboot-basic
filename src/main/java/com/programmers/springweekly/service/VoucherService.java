package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void saveVoucher(VoucherType voucherType, String discount){
        Voucher voucher = VoucherFactory.createVoucher(voucherType, discount);
        voucherRepository.saveVoucher(voucher);
    }

    public Map<UUID, Voucher> findVoucherAll(){
       return voucherRepository.getVoucherMap();
    }
}
