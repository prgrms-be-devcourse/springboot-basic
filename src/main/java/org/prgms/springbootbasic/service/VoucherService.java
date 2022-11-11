package org.prgms.springbootbasic.service;

import org.prgms.springbootbasic.domain.*;
import org.prgms.springbootbasic.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherChoiceDTO voucherChoiceDTO)  {

        if(voucherChoiceDTO.getVoucherType().equals(VoucherType.PERCENT)) {
            return voucherRepository
                    .insert(new PercentDiscountVoucher(UUID.randomUUID(),
                            voucherChoiceDTO.getVoucherType(),
                            voucherChoiceDTO.getAmount()));
        }
        else if(voucherChoiceDTO.getVoucherType().equals(VoucherType.FIXED)) {
            return voucherRepository
                    .insert(new FixedAmountVoucher(UUID.randomUUID(),
                            voucherChoiceDTO.getVoucherType(),
                            voucherChoiceDTO.getAmount()));
        }
        else {
            throw new IllegalArgumentException("invalid voucher option");
        }

    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
