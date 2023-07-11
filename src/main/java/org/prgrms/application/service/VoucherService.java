package org.prgrms.application.service;

import org.prgrms.application.domain.voucher.*;
import org.prgrms.application.entity.VoucherEntity;
import org.prgrms.application.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.lang.Math.abs;

@Service
public class VoucherService {

    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(String voucherType, double discountAmount){
        long voucherId = abs(new Random().nextLong());
        System.out.println(voucherType);
        Voucher voucher = Voucher.of(voucherId, voucherType, discountAmount);
        VoucherEntity voucherEntity = voucherRepository.insert(toEntity(voucher));

        return toDomain(voucherEntity);
    }

    public List<Voucher> getVouchers(){
        List<VoucherEntity> voucherEntities = voucherRepository.findAll();
        List<Voucher> vouchers = new ArrayList<>();
        for (VoucherEntity voucherEntity : voucherEntities) {
            vouchers.add(toDomain(voucherEntity));
        }
        return vouchers;
    }

    public void deleteVoucher(Long voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    private Voucher toDomain(VoucherEntity voucherEntity){
        VoucherType voucherType = VoucherType.findBySelection(voucherEntity.getVoucherType());

        return new Voucher(voucherEntity.getVoucherId(), voucherType, voucherEntity.getDiscountAmount());
    }

    private VoucherEntity toEntity(Voucher voucher){

        return new VoucherEntity(voucher.getVoucherId(), voucher.getVoucherType().name(), voucher.getDiscountAmount());
    }


}
