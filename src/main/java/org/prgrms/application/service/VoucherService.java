package org.prgrms.application.service;

import org.prgrms.application.domain.voucher.*;
import org.prgrms.application.entity.VoucherEntity;
import org.prgrms.application.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class VoucherService {

    protected VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public abstract void createVoucher(VoucherType voucherType, double voucherDetail);

    public List<Voucher> getVouchers() {
        List<VoucherEntity> voucherEntities = voucherRepository.findAll();
        for(VoucherEntity v : voucherEntities){
            System.out.println(v.getVoucherId());
            System.out.println(v.getVoucherType());
            System.out.println(v.getDiscountAmount());
        }

        return voucherEntities.stream().map(this::toDomain).collect(Collectors.toList());
    }

    protected Voucher toDomain(VoucherEntity voucherEntity){
        switch (voucherEntity.getVoucherType()){
            case "FIXED":
                return new FixedAmountVoucher(voucherEntity.getVoucherId(), VoucherType.valueOf(voucherEntity.getVoucherType()), voucherEntity.getDiscountAmount());

            case "PERCENT":
                System.out.println(voucherEntity.getDiscountAmount()); // 0인 값ㅇ ㅣ들어온다.
                return new PercentAmountVoucher(voucherEntity.getVoucherId(), VoucherType.valueOf(voucherEntity.getVoucherType()), voucherEntity.getDiscountAmount());

            default:
                throw new IllegalArgumentException("알 수 없는 voucherType입니다.");
        }
    }

    protected VoucherEntity toEntity(Voucher voucher){
        return new VoucherEntity(voucher.getVoucherId(),voucher.getVoucherType().toString(), voucher.getDiscountAmount());
    }

}
