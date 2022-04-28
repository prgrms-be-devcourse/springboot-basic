package com.mountain.voucherApp.application.service;

import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;
import com.mountain.voucherApp.application.port.out.VoucherPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherPort voucherPort;

    @Autowired
    public VoucherService(VoucherPort voucherPort) {
        this.voucherPort = voucherPort;
    }

    public List<VoucherEntity> findAll() {
        return voucherPort.findAll();
    }
}
