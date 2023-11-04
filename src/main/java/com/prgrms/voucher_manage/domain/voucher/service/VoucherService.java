package com.prgrms.voucher_manage.domain.voucher.service;

import com.prgrms.voucher_manage.domain.voucher.controller.dto.CreateVoucherReq;
import com.prgrms.voucher_manage.domain.voucher.controller.dto.UpdateVoucherReq;
import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import com.prgrms.voucher_manage.domain.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;
import static com.prgrms.voucher_manage.exception.ErrorMessage.*;


@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public Voucher createVoucher(CreateVoucherReq dto) {
        return voucherRepository.save(getVoucherEntity(dto.type(), dto.discountAmount()));
    }

    public List<Voucher> getVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        if (vouchers.isEmpty()) {
            throw new RuntimeException(VOUCHER_NOT_EXISTS.getMessage());
        }
        return vouchers;
    }

    public Voucher findVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public void updateVoucher(UpdateVoucherReq dto) {
        voucherRepository.update(getVoucherEntity(dto.id(), dto.type(), dto.discountAmount()));
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
    public Voucher getVoucherEntity(UUID id, VoucherType type, Long discountAmount){
        if (type == FIXED) {
            return new FixedAmountVoucher(id, discountAmount);
        } else {
            return new PercentDiscountVoucher(id, discountAmount);
        }
    }

    public Voucher getVoucherEntity(VoucherType type, Long discountAmount){
        if (type == FIXED) {
            return new FixedAmountVoucher(discountAmount);
        } else {
            return new PercentDiscountVoucher(discountAmount);
        }
    }
}
