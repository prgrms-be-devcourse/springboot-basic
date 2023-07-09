package org.programmers.VoucherManagement.voucher.application;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.domain.VoucherFactory;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherResponse;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherRepository;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository repository;
    private final VoucherFactory voucherFactory;

    public GetVoucherResponse saveVoucher(CreateVoucherRequest createVoucherRequest) {

        Voucher voucher = voucherFactory.createVoucher(createVoucherRequest);

        voucher = repository.save(voucher);
        return GetVoucherResponse.toDto(voucher);
    }

    public GetVoucherListResponse getVoucherList() {
        return new GetVoucherListResponse(repository.findAll());
    }

}
