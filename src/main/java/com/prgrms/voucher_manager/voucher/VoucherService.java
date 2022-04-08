package com.prgrms.voucher_manager.voucher;

import com.prgrms.voucher_manager.exception.EmptyVoucherException;
import com.prgrms.voucher_manager.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void getFindAllVoucher(){
        if(voucherRepository.findAll().isEmpty()) throw new EmptyVoucherException("");
        voucherRepository.findAll().forEach(v -> System.out.println(v.getInfo()));
    }

    public void createFixedAmountVoucher(long amount){
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        voucherRepository.save(voucher);
    }

    public void createPercentDiscountVoucher(long percent){
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        voucherRepository.save(voucher);
    }





}
