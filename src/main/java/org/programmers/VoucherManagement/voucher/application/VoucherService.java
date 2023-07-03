package org.programmers.VoucherManagement.voucher.application;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.voucher.domain.*;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherRepository;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        List<GetVoucherResponse> getVoucherResponseList = repository.findAll()
                .stream()
                .map(GetVoucherResponse::toDto)
                .collect(Collectors.toList());
        return new GetVoucherListResponse(getVoucherResponseList);
    }

}
