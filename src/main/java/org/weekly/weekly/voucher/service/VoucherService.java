package org.weekly.weekly.voucher.service;

import org.springframework.stereotype.Service;
import org.weekly.weekly.util.ExceptionMsg;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.dto.VoucherDto;
import org.weekly.weekly.voucher.model.ListResponse;
import org.weekly.weekly.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher insertVoucher(VoucherDto voucherDto) {
        validateVoucher(voucherDto);
        Voucher voucher = voucherDto.parseToVoucher();
        return this.voucherRepository.insert(voucher);

    }

    public ListResponse getVouchers() {
        List<Voucher> vouchers = this.voucherRepository.findAll();
        return new ListResponse(vouchers);
    }

    private void validateVoucher(VoucherDto voucherDto) {
        Optional<Voucher> voucherOptional = this.voucherRepository.findById(voucherDto.getVoucherId());
        if (voucherOptional.isPresent()) {
            throw new RuntimeException(ExceptionMsg.VOUCHER_EXIST.getMsg());
        }
    }


}
