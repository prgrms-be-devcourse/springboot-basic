package com.prgms.springbootbasic.service;

import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.dto.ModifiedVoucherDto;
import com.prgms.springbootbasic.dto.NewVoucherDto;
import com.prgms.springbootbasic.dto.VoucherDto;
import com.prgms.springbootbasic.persistent.VouchersStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VoucherService {

    private final VouchersStorage vouchersStorage;

    public VoucherService(VouchersStorage vouchersStorage) {
        this.vouchersStorage = vouchersStorage;
    }

    public List<VoucherDto> findAllVouchers() {
        List<Voucher> vouchers = vouchersStorage.findAll();
        return vouchers.stream()
                    .map(VoucherDto::from)
                    .toList();
    }

    @Transactional
    public void createVoucher(NewVoucherDto newVoucherDto) {
        Voucher voucher = newVoucherDto.toEntity();
        vouchersStorage.save(voucher);
    }

    @Transactional
    public void updateVoucher(UUID voucherId, ModifiedVoucherDto modifiedVoucherDto) {
        Voucher voucher = vouchersStorage.findById(voucherId);
        voucher.changeAmount(modifiedVoucherDto.amount());
        vouchersStorage.update(voucher);
    }

    @Transactional
    public void deleteVoucher(UUID voucherId) {
        vouchersStorage.deleteOne(voucherId);
    }

}
