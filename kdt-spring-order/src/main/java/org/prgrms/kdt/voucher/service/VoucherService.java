package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.common.exception.ExceptionMessage;
import org.prgrms.kdt.voucher.domain.*;
import org.prgrms.kdt.voucher.exception.VoucherNotFoundException;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new VoucherNotFoundException(ExceptionMessage.VOUCHER_NOT_FOUNDED.getMessage()));
    }

    public void useVoucher(Voucher voucher) {
    }

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, long discount){
        return VoucherFactory.createVoucher(voucherType, voucherId, discount);
    }

    public void addVoucher(Voucher voucher){
        voucherRepository.insert(voucher);
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.findAllVouchers();
    }

    public void clearAllVouchers(){
        voucherRepository.clearAllVouchers();
    }
}
