package org.programmers.program.voucher.service;

import org.programmers.program.voucher.model.FixedAmountVoucher;
import org.programmers.program.voucher.model.PercentDiscountVoucher;
import org.programmers.program.voucher.model.Voucher;
import org.programmers.program.voucher.model.VoucherType;
import org.programmers.program.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> getVoucher(UUID id){
        return voucherRepository.findById(id);
    }

    public Voucher createVoucher(VoucherType type, UUID id, long discountAmount, LocalDate expirationDate){
        if(type.equals(VoucherType.FIXED))
            return voucherRepository.insert(new FixedAmountVoucher(id, discountAmount, expirationDate));
        return voucherRepository.insert(new PercentDiscountVoucher(id, discountAmount, expirationDate));
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.findAll();
    }
}
