package org.devcourse.springbasic.domain.voucher.service;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.voucher.dao.VoucherRepository;
import org.devcourse.springbasic.domain.voucher.domain.Voucher;
import org.devcourse.springbasic.domain.voucher.domain.VoucherFactory;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Transactional
    public UUID save(VoucherDto.SaveRequest request) {
        VoucherType voucherType = request.getVoucherType();
        Voucher voucher = VoucherFactory.createVoucher(voucherType, request.getDiscountRate());
        return voucherRepository.save(voucher);
    }

    @Transactional
    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public List<VoucherDto.Response> findByCriteria(VoucherDto.Request request) {

        List<Voucher> vouchers = voucherRepository.findByCriteria(
                request.getCreationStartDate(),
                request.getCreationEndDate(),
                request.getDiscountType() == null ? null : VoucherType.findVoucherType(request.getDiscountType())
        );

        return vouchers.stream()
                .map(voucher -> new VoucherDto.Response(
                        voucher.getVoucherId(),
                        voucher.getVoucherType().getVoucherName(),
                        voucher.getDiscountRate() + voucher.getVoucherType().getUnit())
                )
                .collect(Collectors.toList());
    }

    public VoucherDto.Response findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 바우처입니다."));
        return new VoucherDto.Response(
                voucher.getVoucherId(),
                voucher.getVoucherType().getVoucherName(),
                voucher.getDiscountRate() + voucher.getVoucherType().getUnit()
        );
    }

}