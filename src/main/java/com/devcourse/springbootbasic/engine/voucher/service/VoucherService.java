package com.devcourse.springbootbasic.engine.voucher.service;

import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import com.devcourse.springbootbasic.engine.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(Voucher voucher) { // dto
        return voucherRepository.insert(voucher);
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
