package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.dto.voucher.request.VoucherCreateRequest;
import com.programmers.springweekly.dto.voucher.request.VoucherUpdateRequest;
import com.programmers.springweekly.dto.voucher.response.VoucherListResponse;
import com.programmers.springweekly.dto.voucher.response.VoucherResponse;
import com.programmers.springweekly.repository.voucher.VoucherRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public void save(VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = VoucherFactory.createVoucher(
                UUID.randomUUID(),
                voucherCreateRequest.getVoucherType(),
                voucherCreateRequest.getDiscountAmount()
        );

        voucherRepository.save(voucher);
    }

    public void update(VoucherUpdateRequest voucherUpdateRequest) {
        Voucher voucher = VoucherFactory.createVoucher(
                voucherUpdateRequest.getVoucherId(),
                voucherUpdateRequest.getVoucherType(),
                voucherUpdateRequest.getDiscountAmount()
        );

        voucherRepository.update(voucher);
    }

    public VoucherResponse findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NoSuchElementException("찾는 바우처가 없습니다."));

        return new VoucherResponse(voucher);
    }

    public VoucherListResponse findAll() {
        List<Voucher> voucherList = voucherRepository.findAll();
        return new VoucherListResponse(voucherList.stream().map(VoucherResponse::new).collect(Collectors.toList()));
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.existById(voucherId);

        voucherRepository.deleteById(voucherId);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    public void existById(UUID voucherId) {
        voucherRepository.existById(voucherId);
    }

}
