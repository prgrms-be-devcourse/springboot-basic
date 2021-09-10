package org.programmers.voucher.service;

import org.programmers.voucher.model.CreateVoucherRequest;
import org.programmers.voucher.model.Voucher;
import org.programmers.voucher.model.VoucherBase;
import org.programmers.voucher.model.VoucherType;
import org.programmers.voucher.repository.VoucherJdbcRepository;
import org.programmers.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Profile("prod")
public class VoucherJdbcService {
    private final VoucherRepository voucherRepository;

    public VoucherJdbcService(VoucherJdbcRepository voucherJdbcRepository) {
        this.voucherRepository = voucherJdbcRepository;
    }

    public void save(VoucherBase voucherBase) {
        voucherRepository.create(voucherBase);
    }

    public void save(CreateVoucherRequest createVoucherRequest){
        VoucherBase voucherBase = new VoucherBase(UUID.randomUUID(),createVoucherRequest.getVoucherType(),createVoucherRequest.getVoucherValue(), LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        voucherRepository.create(voucherBase);
    }

    public VoucherBase findByVoucherTypeAndVoucherValue(VoucherType voucherType, Long value) {
        return voucherRepository.findByVoucherTypeAndVoucherValue(voucherType, value).orElseThrow(NoSuchElementException::new);
    }

    public void update(VoucherType voucherType, long value, long changeValue) {
        voucherRepository.update(voucherType, value, changeValue);
    }

    public void update(UUID voucherId, VoucherType voucherType, long changeValue){
        voucherRepository.update(voucherId,voucherType,changeValue);
    }

    public List<VoucherBase> findAllVouchers() {
        return voucherRepository.findAll();
    }

    public VoucherBase findById(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(NoSuchElementException::new);
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }

    public void deleteByVoucherTypeAndVoucherValue(VoucherType voucherType, long value) {
        voucherRepository.deleteByVoucherTypeAndValue(voucherType, value);
    }

    public void deleteByVoucherId(UUID voucherId) {
        voucherRepository.deleteByVoucherId(voucherId);
    }
}
