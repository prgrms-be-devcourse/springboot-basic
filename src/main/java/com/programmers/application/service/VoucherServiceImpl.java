package com.programmers.application.service;

import com.programmers.application.domain.voucher.Voucher;
import com.programmers.application.domain.voucher.VoucherFactory;
import com.programmers.application.dto.reponse.ResponseFactory;
import com.programmers.application.dto.reponse.VoucherInfoResponse;
import com.programmers.application.dto.request.VoucherCreationRequest;
import com.programmers.application.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public UUID createVoucher(VoucherCreationRequest voucherCreationRequest) {
        Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);
        voucherRepository.save(voucher);
        return voucher.getVoucherId();
    }

    @Override
    public List<VoucherInfoResponse> findVoucherList() {
        List<Voucher> voucherList = voucherRepository.findAll();
        return voucherList.stream()
                .map(ResponseFactory::createVoucherInfoResponse)
                .toList();
    }

    @Override
    public VoucherInfoResponse findVoucherByVoucherId(UUID voucherId) {
        return voucherRepository.findByVoucherId(voucherId)
                .map(ResponseFactory::createVoucherInfoResponse)
                .orElseThrow(() -> new NoSuchElementException(String.format("해당 바우처 아이디로 조회되는 바우처가 없습니다. 입력값: %s", voucherId)));
    }
}
