package org.weekly.weekly.voucher.service;

import org.springframework.stereotype.Service;
import org.weekly.weekly.util.DiscountMap;
import org.weekly.weekly.util.ExceptionMsg;
import org.weekly.weekly.voucher.domain.Discount;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.dto.VoucherDto;
import org.weekly.weekly.voucher.repository.VoucherRepository;

import java.util.Optional;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void insertVoucher(VoucherDto voucherDto, Discount discount) {
        validateVoucher(voucherDto);

    }

    private void validateVoucher(VoucherDto voucherDto) {
        Optional<Voucher> voucherOptional = this.voucherRepository.findById(voucherDto.getVoucherId());
        if (voucherOptional.isEmpty()) {
            throw new RuntimeException(ExceptionMsg.VOUCHER_EXIST.getMsg());
        }
    }

}
