package org.programmers.VoucherManagement.voucher.application;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.voucher.dao.VoucherRepository;
import org.programmers.VoucherManagement.voucher.domain.FixedAmountVoucher;
import org.programmers.VoucherManagement.voucher.domain.PercentAmountVoucher;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository repository;

    public Voucher saveVoucher(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = null; //todo : 이걸 null로 처리하는 것 외에 더 좋은 방법이 있을까요?

        if (createVoucherRequest.getDiscountType().isFixed()) {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), createVoucherRequest.getDiscountType(), createVoucherRequest.getDiscountValue());
        }
        if (createVoucherRequest.getDiscountType().isPercent()) {
            voucher = new PercentAmountVoucher(UUID.randomUUID(), createVoucherRequest.getDiscountType(), createVoucherRequest.getDiscountValue());
        }
        return repository.save(voucher);
    }

    public List<Voucher> getVoucherList() {
        return repository.findAll();
    }
}
