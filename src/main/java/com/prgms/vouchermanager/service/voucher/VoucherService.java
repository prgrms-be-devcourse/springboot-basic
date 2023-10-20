package com.prgms.vouchermanager.service.voucher;


import com.prgms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.dto.CreateVoucherDto;
import com.prgms.vouchermanager.exception.ExceptionType;
import com.prgms.vouchermanager.repository.voucher.VoucherRepository;
import com.prgms.vouchermanager.validation.InputValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final InputValidation validation;
    private final VoucherRepository voucherRepository;

    public VoucherService(InputValidation validation, VoucherRepository voucherRepository) {
        this.validation = validation;
        this.voucherRepository = voucherRepository;
    }

    public void create(CreateVoucherDto dto) {

        Voucher voucher = null;
        if (dto.getVoucherType() == 1) {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), dto.getValue());
        } else if (dto.getVoucherType() == 2) {
            if (!validation.validVoucherPercent(dto.getValue())) {
                throw new RuntimeException(ExceptionType.INVALID_VOUCHER_PERCENT.getMessage());
            }
            voucher = new PercentDiscountVoucher(UUID.randomUUID(), dto.getValue());
        } else
            return;

        voucherRepository.create(voucher);
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.getAllVouchers();
    }
}
