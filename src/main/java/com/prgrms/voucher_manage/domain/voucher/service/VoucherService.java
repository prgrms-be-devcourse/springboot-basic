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
import static com.prgrms.voucher_manage.base.ErrorMessage.*;


@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public Voucher createVoucher(CreateVoucherReq dto) {
        return voucherRepository.save(getVoucherEntity(dto.type(), dto.discountAmount()));
    }

    public List<Voucher> getVouchers() {
        List<Voucher> vouchers = voucherRepository.getAll();
        if (vouchers.isEmpty()) {
            throw new RuntimeException(VOUCHER_NOT_EXISTS.getMessage());
        }
        return vouchers;
    }

    public List<Voucher> getVouchersByType(VoucherType type){
        List<Voucher> vouchers = voucherRepository.getByType(type);
        if (vouchers.isEmpty()){
            throw new RuntimeException(VOUCHER_TYPE_NOT_EXISTS.getMessage());
        }
        return vouchers;
    }

    public Voucher getVoucherById(UUID voucherId) {
        return voucherRepository.getById(voucherId);
    }

    public void updateVoucher(UUID voucherId, UpdateVoucherReq dto) {
        Voucher voucher = getVoucherIfExists(voucherId);
        voucherRepository.update(getVoucherEntity(voucherId, voucher.getType(), dto.discountAmount()));
    }

    public void deleteVoucher(UUID voucherId) {
        getVoucherIfExists(voucherId);
        voucherRepository.deleteById(voucherId);
    }

    public Voucher getVoucherIfExists(UUID voucherId){
        Voucher voucher = voucherRepository.getById(voucherId);
        if (voucher==null)
            throw new RuntimeException(VOUCHER_NOT_EXISTS.getMessage());
        return voucher;
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
