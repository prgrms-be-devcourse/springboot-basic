package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher.request.CreateVoucherRequest;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher.request.VoucherDto;
import com.programmers.kwonjoosung.springbootbasicjoosung.exception.DataNotExistException;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;



@Service
public class VoucherService {
    public static final String VOUCHER = "voucher";
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherDto saveVoucher(CreateVoucherRequest request) {
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.valueOf(request.getVoucherType()), request.getDiscount());
        return new VoucherDto(voucherRepository.insert(voucher));
    }

    public VoucherDto findVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .map(VoucherDto::new)
                .orElseThrow(() -> new DataNotExistException(voucherId.toString(), VOUCHER)); // Repository에서 해도 되지 않나?
    }

    public List<VoucherDto> findVoucher(VoucherType voucherType) {
        List<Voucher> vouchers = voucherRepository.findByType(voucherType);
        return vouchers.stream().map(VoucherDto::new).toList();
    }

    public List<VoucherDto> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream().map(VoucherDto::new).toList();
    }

    public VoucherDto updateVoucher(UUID voucherId, VoucherType voucherType, long discount) {
        Voucher newVoucher = VoucherFactory.createVoucher(voucherType, voucherId, discount);
        return new VoucherDto(voucherRepository.update(newVoucher));
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

}
