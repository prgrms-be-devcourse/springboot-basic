package com.programmers.kdtspringorder.voucher.service;

import com.programmers.kdtspringorder.voucher.VoucherType;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.factory.VoucherFactory;
import com.programmers.kdtspringorder.voucher.repository.VoucherRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(@Qualifier("jdbc") VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public Voucher findByID(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {

    }

    public Voucher createVoucher(VoucherType voucherType, long value) {
        Voucher voucher = voucherFactory.createVoucher(voucherType, value);
        voucherRepository.save(voucher);
        return voucher;
    }

    public List<Voucher> findAll(){
        return voucherRepository.findAll();
    }

    public List<Voucher> findByCustomerId(UUID customerId) {
        return voucherRepository.findByCustomerId(customerId);
    }

    public List<Voucher> findAllWithoutCustomerId() {
        return voucherRepository.findAllWithoutCustomerId();
    }

    public void allocateVoucher(Voucher voucher, UUID customerId) {
        voucher.allocateVoucherToCustomer(customerId);
        voucherRepository.allocateVoucher(voucher.getVoucherId(), customerId);
    }

    public void deallocateVoucher(Voucher voucher) {
        voucher.removeVoucherFromCustomer();
        voucherRepository.deallocateVoucher(voucher.getVoucherId());
    }

    public void deleteVoucher(UUID voucherId){
        voucherRepository.delete(voucherId);
    }
}

