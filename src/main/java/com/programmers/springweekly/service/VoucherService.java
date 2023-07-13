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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
                .orElseThrow(() -> new NoSuchElementException("찾는 바우처가 없습니다, 바우처가 저장되어있는지 확인해보세요."));

        return new VoucherResponse(voucher);
    }

    public VoucherListResponse findAll() {
        List<Voucher> voucherList = voucherRepository.findAll();

        if (CollectionUtils.isEmpty(voucherList)) {
            throw new NoSuchElementException("바우처가 저장되어 있지 않습니다.");
        }

        return new VoucherListResponse(voucherList.stream().map(VoucherResponse::new).toList());
    }

    public int deleteById(UUID voucherId) {
        if (!voucherRepository.existById(voucherId)) {
            throw new NoSuchElementException("삭제하려고 입력한 바우처 ID는 존재하지 않는 ID입니다. 다시 입력해주세요");
        }

        return voucherRepository.deleteById(voucherId);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    public boolean existById(UUID voucherId) {
        return voucherRepository.existById(voucherId);
    }

}
