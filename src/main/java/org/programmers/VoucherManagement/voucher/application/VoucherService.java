package org.programmers.VoucherManagement.voucher.application;

import org.programmers.VoucherManagement.voucher.domain.DiscountValue;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.domain.VoucherFactory;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;
import org.programmers.VoucherManagement.voucher.dto.UpdateVoucherRequest;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_FOUND_VOUCHER;


@Component
public class VoucherService {
    private final VoucherRepository repository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.repository = voucherRepository;
    }

    public void updateVoucher(UUID voucherId, UpdateVoucherRequest updateVoucherRequest) {
        Voucher voucher = repository.findById(voucherId).orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));
        DiscountValue discountValue = new DiscountValue(updateVoucherRequest.getDiscountValue());
        discountValue.validateValue(voucher.getDiscountType());

        voucher.changeDiscountValue(discountValue);
        repository.update(voucher);
    }

    public void saveVoucher(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = VoucherFactory.createVoucher(createVoucherRequest);
        repository.insert(voucher);
    }

    public GetVoucherListResponse getVoucherList() {
        return new GetVoucherListResponse(repository.findAll());
    }

    public void deleteVoucher(UUID voucherId) {
        Voucher voucher = repository.findById(voucherId).orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));
        repository.delete(voucher);
    }

}
