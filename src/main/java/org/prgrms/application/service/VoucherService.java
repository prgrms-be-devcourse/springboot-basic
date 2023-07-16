package org.prgrms.application.service;

import org.prgrms.application.domain.voucher.*;
import org.prgrms.application.entity.VoucherEntity;
import org.prgrms.application.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

@Service
public class VoucherService {

    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, double discountAmount){
        long voucherId = abs(new Random().nextLong());
        Voucher voucher = Voucher.of(voucherId, voucherType, discountAmount);

        VoucherEntity voucherEntity = voucherRepository.insert(voucher.toEntity());

        return voucherEntity.toDomain();
    }

    public List<Voucher> getVouchers(){
        List<VoucherEntity> voucherEntities = voucherRepository.findAll();
        return voucherEntities.stream().map(VoucherEntity::toDomain).collect(Collectors.toList());
    }

    public void deleteVoucher(Long voucherId) {
        voucherRepository.deleteById(voucherId);
    }

}
