package com.prgms.VoucherApp.domain.voucher.model;

import com.prgms.VoucherApp.domain.voucher.dto.VoucherCreateRequest;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResponse;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherUpdateRequest;
import com.prgms.VoucherApp.domain.voucher.dto.VouchersResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VoucherService {

    private final VoucherDao voucherDao;

    public VoucherService(VoucherDao voucherDao) {
        this.voucherDao = voucherDao;
    }

    @Transactional
    public VoucherResponse save(VoucherCreateRequest requestDto) {
        Voucher voucher = switch (requestDto.voucherType()) {
            case FIXED_VOUCHER -> new FixedAmountVoucher(UUID.randomUUID(), requestDto.amount());
            case PERCENT_VOUCHER -> new PercentDiscountVoucher(UUID.randomUUID(), requestDto.amount());
        };
        voucherDao.save(voucher);

        return new VoucherResponse(voucher);
    }

    public Optional<VoucherResponse> findOne(UUID id) {
        return voucherDao.findById(id)
            .map(VoucherResponse::new);
    }

    public VouchersResponse findAll() {
        List<Voucher> findVouchers = voucherDao.findAll();

        List<VoucherResponse> convertVoucherResponse = findVouchers.stream()
            .map(VoucherResponse::new)
            .toList();

        return new VouchersResponse(convertVoucherResponse);
    }

    public VouchersResponse findByVoucherType(VoucherType voucherType) {
        List<Voucher> findVouchers = voucherDao.findByVoucherType(voucherType);

        List<VoucherResponse> convertVoucherResponse = findVouchers.stream()
            .map(VoucherResponse::new)
            .toList();

        return new VouchersResponse(convertVoucherResponse);
    }

    @Transactional
    public void update(VoucherUpdateRequest voucherUpdateRequest) {
        Voucher voucher = switch (voucherUpdateRequest.voucherType()) {
            case FIXED_VOUCHER -> new FixedAmountVoucher(voucherUpdateRequest.id(), voucherUpdateRequest.amount());
            case PERCENT_VOUCHER ->
                new PercentDiscountVoucher(voucherUpdateRequest.id(), voucherUpdateRequest.amount());
        };

        voucherDao.update(voucher);
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherDao.deleteById(id);
    }
}
