package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.Voucher;
import com.programmers.springweekly.domain.VoucherFactory;
import com.programmers.springweekly.domain.VoucherMenu;
import com.programmers.springweekly.repository.MemoryVoucherRepository;

import java.util.Map;
import java.util.UUID;

public class VoucherService {

    private final MemoryVoucherRepository memoryVoucherRepository;

    public VoucherService(MemoryVoucherRepository memoryVoucherRepository) {
        this.memoryVoucherRepository = memoryVoucherRepository;
    }

    void saveVoucher(VoucherMenu voucherMenu, long discount){
        Voucher voucher = new VoucherFactory().createVoucher(voucherMenu, discount);
        memoryVoucherRepository.saveVoucher(voucher);
    }

    Map<UUID, Voucher> findVoucherAll(){
       return memoryVoucherRepository.getVoucherMap();
    }
}
