package com.devcourse.springbootbasic.engine.voucher.service;

import com.devcourse.springbootbasic.engine.model.VoucherType;
import com.devcourse.springbootbasic.engine.voucher.domain.FixedAmountVoucher;
import com.devcourse.springbootbasic.engine.voucher.domain.PercentDiscountVoucher;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import com.devcourse.springbootbasic.engine.voucher.domain.VoucherDto;
import com.devcourse.springbootbasic.engine.voucher.domain.factory.FixedVoucherFactory;
import com.devcourse.springbootbasic.engine.voucher.domain.factory.PercentVoucherFactory;
import com.devcourse.springbootbasic.engine.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    private Voucher convertDtoToDomain(VoucherDto voucherDto) {
        if (voucherDto.voucherType().equals(VoucherType.FIXED_AMOUNT)) {
            return new FixedVoucherFactory().create(voucherDto.discountValue());
        }
        return new PercentVoucherFactory().create(voucherDto.discountValue());
    }

    public Voucher createVoucher(VoucherDto voucherDto) {
        return voucherRepository.insert(convertDtoToDomain(voucherDto));
    }
    // TODO resolve this ok?
    public <T> List<T> getAllVouchers() {
        return voucherRepository.findAll();
    } //
}

/*
* data
* 1. request, param (spring request parameter)
* 2. entity -> (db 혹은 persist layer)
* 3. response, result ->
*
* controller -> request, param
* service -> response, entity
* <- layer -> 집어넣을를 instance ->
* repository -> entity (Voucher)
*
* */
