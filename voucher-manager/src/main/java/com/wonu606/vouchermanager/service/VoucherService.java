package com.wonu606.vouchermanager.service;

import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.repository.VoucherRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class VoucherService {

    private final VoucherFactory factory;
    private final VoucherRepository repository;

    public VoucherService(VoucherRepository repository) {
        this.repository = repository;
        this.factory = new VoucherFactory(repository);
    }

    public Voucher createVoucher(String typeName, UUID uuid, double discount) {
        VoucherType type = VoucherType.getVoucherTypeByName(typeName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 바우처 타입입니다."));

        return factory.create(type, uuid, discount);
    }

    public List<Voucher> getVoucherList() {
        return repository.findAll();
    }
}
