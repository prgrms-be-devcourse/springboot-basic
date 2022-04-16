package com.mountain.voucherApp.voucher;

import com.mountain.voucherApp.repository.VoucherRepository;
import com.mountain.voucherApp.utils.DiscountPolicyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherEntity createVoucher(int policyId, long amount) {
        VoucherEntity voucherEntity = new VoucherEntity(UUID.randomUUID(), policyId, amount);
        // select id from voucher where policyId = :policyId and amount = :amount;
        // TODO if not Exist insert, else no insert
        voucherRepository.insert(voucherEntity);
        return voucherEntity;
    }

    public List<VoucherEntity> findAll() {
        return voucherRepository.findAll();
    }
}

