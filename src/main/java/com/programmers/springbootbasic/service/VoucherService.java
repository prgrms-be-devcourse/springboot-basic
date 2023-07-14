package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherRepository;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponse;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponses;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse createVoucher(VoucherCreationRequest request) {
        Voucher fixedAmountVoucher = VoucherMapper.toVoucher(request);
        Voucher saved = voucherRepository.save(fixedAmountVoucher);
        return VoucherMapper.toVoucherResponse(saved);
    }

    public VoucherResponses list() {
        return VoucherMapper.toVoucherResponseList(voucherRepository.findAll());
    }
}
