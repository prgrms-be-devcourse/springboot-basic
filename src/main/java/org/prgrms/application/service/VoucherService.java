package org.prgrms.application.service;

import org.prgrms.application.domain.voucher.*;
import org.prgrms.application.entity.VoucherEntity;
import org.prgrms.application.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

import static java.lang.Math.abs;

@Service
public class VoucherService {

    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(String voucherType, String discountAmount){
        long voucherId = abs(new Random().nextLong());
        Voucher voucher = Voucher.of(voucherId, voucherType, discountAmount);
        VoucherEntity voucherEntity = voucherRepository.insert(toEntity(voucher));

        return toDomain(voucherEntity);
    }

    private Voucher toDomain(VoucherEntity voucherEntity){
        VoucherType voucherType = VoucherType.findBySelection(voucherEntity.getVoucherType());

        return new Voucher(voucherEntity.getVoucherId(), voucherType, voucherEntity.getDiscountAmount());
    }

    private VoucherEntity toEntity(Voucher voucher){

        return new VoucherEntity(voucher.getVoucherId(), voucher.getVoucherType().name(), voucher.getDiscountAmount());
    }

}
