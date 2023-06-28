package org.programmers.VoucherManagement.voucher.application;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.DiscountType;
import org.programmers.VoucherManagement.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.dao.VoucherRepository;
import org.programmers.VoucherManagement.voucher.domain.FixedAmountVoucher;
import org.programmers.VoucherManagement.voucher.domain.PercentAmountVoucher;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.programmers.VoucherManagement.exception.VoucherExceptionMessage.NOT_EXIST_DISCOUNT_TYPE;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository repository;

    public GetVoucherResponse saveVoucher(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher;
        DiscountType discountType = createVoucherRequest.getDiscountType();
        voucher = switch (discountType) {
            case FIXED ->
                    new FixedAmountVoucher(UUID.randomUUID(), discountType, createVoucherRequest.getDiscountValue());
            case PERCENT ->
                    new PercentAmountVoucher(UUID.randomUUID(), discountType, createVoucherRequest.getDiscountValue());
            default -> throw new VoucherException(NOT_EXIST_DISCOUNT_TYPE); // 유효하지 않은 할인 유형 예외 발생
        };

        voucher = repository.save(voucher);
        return GetVoucherResponse.toDto(voucher);
    }

    public List<GetVoucherResponse> getVoucherList() {
        return repository.findAll()
                .stream()
                .map(GetVoucherResponse::toDto)
                .collect(Collectors.toList());
    }
}
