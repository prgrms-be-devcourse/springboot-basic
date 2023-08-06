package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.common.domain.Page;
import com.programmers.springbootbasic.common.domain.Pageable;
import com.programmers.springbootbasic.domain.voucher.Repository.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.presentation.controller.dto.Voucher.VoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponse;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponses;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID createVoucher(VoucherCreationRequest request) {
        Voucher voucher = VoucherMapper.toVoucher(request);
        return voucherRepository.save(voucher);
    }

    public VoucherResponses list() {
        return VoucherMapper.toVoucherResponseList(voucherRepository.findAll());
    }

    public Page<VoucherResponse> findVoucherByPage(Pageable pageable) {
        return voucherRepository.findAllWithPagination(pageable)
                .map(VoucherMapper::toVoucherResponse);
    }
}
