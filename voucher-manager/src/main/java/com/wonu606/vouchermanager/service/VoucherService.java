package com.wonu606.vouchermanager.service;

import com.wonu606.vouchermanager.domain.Voucher;
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

    public Voucher createVoucher(String typeName, UUID uuid, double discount) {
        VoucherType type = VoucherType.getVoucherTypeByName(typeName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 바우처 타입입니다."));

        Voucher voucher = factory.create(type, uuid, discount);
        return repository.save(voucher);
    }

    public List<Voucher> getVoucherList() {
        return repository.findAll();
    }
}
