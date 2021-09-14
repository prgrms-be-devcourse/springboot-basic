package org.prgrms.kdt.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.prgrms.kdt.domain.CreateVoucherRequest;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherEntity;
import org.prgrms.kdt.enumType.VoucherStatus;
import org.prgrms.kdt.factory.VoucherFactory;
import org.prgrms.kdt.jdbcRepository.VoucherJdbcRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VoucherService {

    @Autowired
    private final VoucherRepository voucherRepository;

    @Autowired
    private VoucherFactory voucherFactory;


    public VoucherEntity createVoucher(CreateVoucherRequest createVoucherRequest){
        var voucherType = VoucherStatus.valueOf(createVoucherRequest.getVoucherType());
        var discount = Long.parseLong(createVoucherRequest.getDiscount());
        var voucherEntity = VoucherEntity.builder()
                .voucherId(UUID.randomUUID())
                .voucherType(voucherType)
                .createdAt(LocalDateTime.now())
                .discount(discount).build();
        return voucherRepository.insert(voucherEntity);
    }

    public List<VoucherEntity> findAll() {
        return voucherRepository.findAll();
    }

    public VoucherEntity getVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId).get();
    }

    public void removeAll() {
        voucherRepository.deleteAll();
    }

    public VoucherEntity commandCreateVoucher(int voucherStatus) {
        var voucherType = voucherStatus == 1 ? VoucherStatus.FIXED : VoucherStatus.PERCENT;
        var v = voucherFactory.getDiscounterVoucher(voucherType);
        var voucherEntity = VoucherEntity.builder()
                .voucherId(v.getVoucherId())
                .voucherType(voucherType)
                .discount(v.discountCoupon())
                .createdAt(LocalDateTime.now())
                .build();
        voucherRepository.insert(voucherEntity);
        return voucherEntity;
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    //
//    public Voucher getVoucher(UUID voucherId) {
//        return memoryVoucherRepository.findById(voucherId)
//                .orElseThrow(() -> new RuntimeException(String.format("Can not find a voucher %s", voucherId)));
//    }
//
//    public List<Voucher> findAll() {
//        return memoryVoucherRepository.findAll();
//    }


}
