package com.mountain.voucherApp.application.service;

import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;
import com.mountain.voucherApp.application.port.out.CustomerPort;
import com.mountain.voucherApp.application.port.out.VoucherPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherPort voucherPort;
    private final CustomerPort customerPort;

    @Autowired
    public VoucherService(VoucherPort voucherPort, CustomerPort customerPort) {
        this.voucherPort = voucherPort;
        this.customerPort = customerPort;
    }

    public List<VoucherEntity> findAll() {
        return voucherPort.findAll();
    }

    @Transactional
    public void deleteById(UUID voucherId) {
        customerPort.removeVoucherId(voucherId);
        voucherPort.deleteById(voucherId);
    }
}
