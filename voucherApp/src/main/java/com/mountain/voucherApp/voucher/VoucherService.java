package com.mountain.voucherApp.voucher;

import com.mountain.voucherApp.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mountain.voucherApp.constants.Message.CREATE_NEW_VOUCHER;

@Service
public class VoucherService {
    private static final Logger log = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherEntity createVoucher(int policyId, long amount) {
        VoucherEntity newVoucherEntity = new VoucherEntity(UUID.randomUUID(), policyId, amount);
        Optional<VoucherEntity> findEntity = voucherRepository.findByPolicyIdAndDiscountAmount(policyId, amount);
        if (!findEntity.isPresent()) {
            voucherRepository.insert(newVoucherEntity);
            log.info(CREATE_NEW_VOUCHER);
        }
        return findEntity.orElse(newVoucherEntity);
    }

    public List<VoucherEntity> findAll() {
        return voucherRepository.findAll();
    }
}

