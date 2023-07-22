package org.prgrms.application.service;

import org.prgrms.application.domain.voucher.*;
import org.prgrms.application.domain.voucher.typepolicy.VoucherTypePolicy;
import org.prgrms.application.entity.VoucherEntity;
import org.prgrms.application.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Long createVoucher(VoucherType voucherType, double discountAmount){
        long voucherId = abs(new Random().nextLong());
        VoucherTypePolicy voucherTypePolicy = voucherType.applyPolicy(discountAmount);
        Voucher voucher = Voucher.of(voucherId,voucherTypePolicy);

        return voucherRepository.insert(voucher.toEntity()).getVoucherId();
    }

    public List<VoucherDto> getVouchers(){
        List<VoucherEntity> voucherEntities = voucherRepository.findAll();
        List<Voucher> vouchers = voucherEntities.stream().map(VoucherEntity::toDomain).collect(Collectors.toList());

        return vouchers.stream().map(VoucherDto::of).collect(Collectors.toList());
    }

    public void deleteVoucher(Long voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public List<VoucherDto> getVouchersByType(VoucherType voucherType) {
        List<VoucherEntity> voucherEntities = voucherRepository.findByType(voucherType);
        List<Voucher> vouchers = voucherEntities.stream().map(VoucherEntity::toDomain).collect(Collectors.toList());

        return  vouchers.stream().map(VoucherDto::of).collect(Collectors.toList());
    }
}
