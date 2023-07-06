package com.wonu606.vouchermanager.service.voucher;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherDto;
import com.wonu606.vouchermanager.repository.Repository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VoucherService {

    private final VoucherFactory factory;
    private final Repository repository;

    public Voucher createVoucher(VoucherDto voucherDto) {
        Voucher voucher = factory.create(voucherDto);
        return repository.save(voucher);
    }

    public List<Voucher> getVoucherList() {
        return repository.findAll();
    }
}
