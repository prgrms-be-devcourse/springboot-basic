package org.prgrms.application.service;

import org.prgrms.application.domain.voucher.*;
import org.prgrms.application.entity.VoucherEntity;
import org.prgrms.application.repository.voucher.VoucherRepository;
import java.util.List;
import java.util.stream.Collectors;

import static org.prgrms.application.domain.voucher.VoucherType.FIXED;
import static org.prgrms.application.domain.voucher.VoucherType.PERCENT;

public abstract class VoucherService {

    protected VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public abstract void createVoucher(VoucherType voucherType, double voucherDetail);

    public List<Voucher> getVouchers() {
        List<VoucherEntity> voucherEntities = voucherRepository.findAll();
        return voucherEntities.stream().map(this::toDomain).collect(Collectors.toList());
    }

    protected Voucher toDomain(VoucherEntity voucherEntity){
        switch (voucherEntity.getVoucherType()){
            case "FIXED":
                return new FixedAmountVoucher(voucherEntity.getVoucherId(), FIXED, voucherEntity.getDiscountAmount());

            case "PERCENT":
                return new PercentAmountVoucher(voucherEntity.getVoucherId(), PERCENT, voucherEntity.getDiscountAmount());

            default:
                throw new IllegalArgumentException("알 수 없는 voucherType입니다.");
        }
    }

    protected VoucherEntity toEntity(Voucher voucher){
        return new VoucherEntity(voucher.getVoucherId(),voucher.getVoucherType().toString(),voucher.getDiscountAmount());
    }

}
