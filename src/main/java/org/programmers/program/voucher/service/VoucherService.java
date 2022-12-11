package org.programmers.program.voucher.service;

import org.programmers.program.voucher.controller.VoucherDto;
import org.programmers.program.voucher.model.*;
import org.programmers.program.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> findById(UUID id){
        return voucherRepository.findById(id);
    }


    public Voucher createVoucher(VoucherType type, UUID id, long discountAmount){
        if(type.equals(VoucherType.FIXED))
            return voucherRepository.save(new FixedAmountVoucher(id, discountAmount));
        return voucherRepository.save(new PercentDiscountVoucher(id, discountAmount));
    }

    public Voucher createVoucher(VoucherType type, UUID id, long discountAmount, LocalDateTime expirationDate) {
        if (type.equals(VoucherType.FIXED))
            return voucherRepository.save(new FixedAmountVoucher(id, discountAmount, expirationDate));
        return voucherRepository.save(new PercentDiscountVoucher(id, discountAmount, expirationDate));
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.findAll();
    }

    public int count(){return voucherRepository.countAll();}
}
