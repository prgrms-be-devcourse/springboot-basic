package org.programmers.VoucherManagement.voucher.application;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.voucher.domain.*;
import org.programmers.VoucherManagement.voucher.dao.VoucherRepository;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherReq;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListRes;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherRes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository repository;

    public GetVoucherRes saveVoucher(CreateVoucherReq createVoucherReq) {
        DiscountType discountType = createVoucherReq.getDiscountType();

        Voucher voucher = switch (discountType) {
            case FIXED ->
                    new FixedAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(createVoucherReq.getDiscountValue()));
            case PERCENT ->
                    new PercentAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(createVoucherReq.getDiscountValue()));
        };
        voucher = repository.save(voucher);
        return GetVoucherRes.toDto(voucher);
    }

    public GetVoucherListRes getVoucherList() {
        List<GetVoucherRes> getVoucherResList = repository.findAll()
                .stream()
                .map(GetVoucherRes::toDto)
                .collect(Collectors.toList());
        return new GetVoucherListRes(getVoucherResList);
    }

}
