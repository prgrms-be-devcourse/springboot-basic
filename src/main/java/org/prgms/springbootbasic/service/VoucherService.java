package org.prgms.springbootbasic.service;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.prgms.springbootbasic.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherType seqToType(int voucherSeq) {
        return VoucherType.getTypeFromSeq(voucherSeq);
    }

    public void create(VoucherType voucherType, int discountDegree) {
        VoucherPolicy voucherPolicy = voucherType.create(discountDegree);

        voucherRepository.create(voucherPolicy);
    }

    public List<VoucherPolicy> findAll(){
        return voucherRepository.findAll();
    }
}
