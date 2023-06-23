package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.domain.voucher.VoucherMenu;
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

    public void saveVoucher(VoucherMenu voucherMenu, long discount){
        Voucher voucher = new VoucherFactory().createVoucher(voucherMenu, discount);
        voucherRepository.saveVoucher(voucher);
    }

    public Map<UUID, Voucher> findVoucherAll(){
       return voucherRepository.getVoucherMap();
    }
}
