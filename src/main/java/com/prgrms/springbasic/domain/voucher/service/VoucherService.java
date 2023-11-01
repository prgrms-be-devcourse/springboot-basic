package com.prgrms.springbasic.domain.voucher.service;

import com.prgrms.springbasic.domain.voucher.dto.CreateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.UpdateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.entity.DiscountType;
import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponse saveVoucher(CreateVoucherRequest request) {
        Voucher voucher = voucherRepository.saveVoucher(Voucher.createVoucher(UUID.randomUUID(), request.discountType(), request.discountValue(), LocalDateTime.now()));
        return VoucherResponse.from(voucher);
    }

    @Transactional(readOnly = true)
    public List<VoucherResponse> findAll() {
        return voucherRepository.findAll().stream()
                .map(VoucherResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public VoucherResponse findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findVoucherById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException("Voucher not found"));
        return VoucherResponse.from(voucher);
    }

    @Transactional
    public void updateVoucher(UpdateVoucherRequest request) {
        Voucher voucher = voucherRepository.findVoucherById(request.voucherId())
                .orElseThrow(() -> new IllegalArgumentException("Voucher not found"));
        voucher.update(request.discountValue());
        voucherRepository.updateVoucher(voucher);
    }

    @Transactional
    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    @Transactional
    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    @Transactional(readOnly = true)
    public List<VoucherResponse> findByCondition(String date, String discountType) {
        List<Voucher> vouchers = new ArrayList<>();
        if (date != null) {
            LocalDate localDate = LocalDate.parse(date);
            if (discountType != null) {
                vouchers = voucherRepository.findByCreatedAtAndDiscountType(localDate, DiscountType.find(discountType));
            } else {
                vouchers = voucherRepository.findByCreatedAt(LocalDate.parse(date));
            }
        } else {
            logger.info("No voucher found");
        }
        return vouchers.stream()
                .map(VoucherResponse::from)
                .toList();
    }
}
