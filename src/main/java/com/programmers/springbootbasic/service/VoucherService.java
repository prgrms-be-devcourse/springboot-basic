package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherRepository;
import com.programmers.springbootbasic.service.dto.VoucherRequest;
import com.programmers.springbootbasic.service.dto.VoucherResponses;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherRequest request) {
        Voucher fixedAmountVoucher = VoucherMapper.toVoucher(request);
        voucherRepository.save(fixedAmountVoucher);
        return fixedAmountVoucher;
    }

    public VoucherResponses list() {
        return VoucherMapper.toVoucherResponseList(voucherRepository.findAll());
    }
}
