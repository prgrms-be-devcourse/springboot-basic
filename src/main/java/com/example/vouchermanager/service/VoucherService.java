package com.example.vouchermanager.service;

import com.example.vouchermanager.console.VoucherType;
import com.example.vouchermanager.domain.FixedAmountVoucher;
import com.example.vouchermanager.domain.PercentAmountVoucher;
import com.example.vouchermanager.domain.Voucher;
import com.example.vouchermanager.message.LogMessage;
import com.example.vouchermanager.repository.VoucherMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VoucherService {

    VoucherMemoryRepository repository;

    @Autowired
    public VoucherService(VoucherMemoryRepository repository) {
        this.repository = repository;
    }

    public void create(VoucherType voucherType, long discount) {
        log.info(LogMessage.SERVICE_CREATE_START.getMessage());

        Voucher voucher = null;
        if(voucherType == VoucherType.FIXED) {
            voucher = new FixedAmountVoucher(discount);
        } else if(voucherType == VoucherType.PERCENT) {
            voucher = new PercentAmountVoucher(discount);
        }

        log.info(LogMessage.VOUCHER_TYPE_INFO.getMessage(), voucher);

        repository.create(voucher);
    }
    public List<Voucher> list() {
        log.info(LogMessage.SERVICE_LIST_START.getMessage());

        return repository.list();
    }
}
