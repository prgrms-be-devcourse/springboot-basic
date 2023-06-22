package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.Voucher;
import com.programmers.springweekly.domain.VoucherFactory;
import com.programmers.springweekly.domain.VoucherMenu;
import com.programmers.springweekly.repository.MemoryVoucherRepository;
import com.programmers.springweekly.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(MemoryVoucherRepository voucherRepository) {
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
