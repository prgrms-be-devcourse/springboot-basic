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

        if(voucherChoiceDTO.voucherType() == VoucherType.PERCENT) {
            return voucherRepository
                    .insert(new PercentDiscountVoucher(UUID.randomUUID(),
                            voucherChoiceDTO.voucherType(),
                            voucherChoiceDTO.amount()));
        }
        else if(voucherChoiceDTO.voucherType() == VoucherType.FIXED) {
            return voucherRepository
                    .insert(new FixedAmountVoucher(UUID.randomUUID(),
                            voucherChoiceDTO.voucherType(),
                            voucherChoiceDTO.amount()));
        }
        else {
            throw new IllegalArgumentException("invalid voucher option");
        }

    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
