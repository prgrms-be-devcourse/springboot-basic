package org.prgms.springbootbasic.service;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.prgms.springbootbasic.repository.VoucherRepository;
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
        VoucherPolicy voucherPolicy = voucherType.create(discountDegree); // 생성 비즈니스 로직이 있다.

        voucherRepository.create(voucherPolicy);
    }

    public List<VoucherPolicy> findAll(){
        return voucherRepository.findAll();
    }
}
