package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherRepository;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponse;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponses;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private static final String FAIL_TO_CREATE = "바우처 생성에 실패했습니다. 입력값을 확인해주세요";
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse createVoucher(VoucherCreationRequest request) {
        Voucher fixedAmountVoucher = VoucherMapper.toVoucher(request);
        return voucherRepository.save(fixedAmountVoucher)
                .map(VoucherMapper::toVoucherResponse)
                .orElseThrow(() -> new IllegalArgumentException(FAIL_TO_CREATE));
    }

    public VoucherResponses list() {
        return VoucherMapper.toVoucherResponseList(voucherRepository.findAll());
    }
}
