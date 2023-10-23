package com.programmers.springbootbasic.domain.voucher.application;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(CreateVoucherRequest request) {
        // 중복 바우처 체크
        voucherRepository.save(request.toEntity());
    }

    public List<VoucherResponse> findAll() {
        return voucherRepository.findAll().stream()
            .map(VoucherResponse::of)
            .toList();
    }

}
