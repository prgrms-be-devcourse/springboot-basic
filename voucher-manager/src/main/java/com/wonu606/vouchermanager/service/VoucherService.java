package com.wonu606.vouchermanager.service;

import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.VoucherDto;
import com.wonu606.vouchermanager.repository.VoucherRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VoucherService {

    private final VoucherFactory factory;
    private final VoucherRepository repository;

    public Voucher createVoucher(VoucherDto voucherDto) {
        Voucher voucher = factory.create(voucherDto);
        return repository.save(voucher);
    }

    public List<Voucher> getVoucherList() {
        return repository.findAll();
    }
}
