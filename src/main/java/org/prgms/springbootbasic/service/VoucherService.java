package org.prgms.springbootbasic.service;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.prgms.springbootbasic.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public VoucherType nameToType(String policyName) {
        return VoucherType.getTypeFromName(policyName);
    }

    public void upsert(VoucherType voucherType, long discountDegree) {
        VoucherPolicy voucherPolicy = voucherType.create();
        Voucher voucher = new Voucher(UUID.randomUUID(), discountDegree, voucherPolicy);

        voucherRepository.upsert(voucher);
    }

    public Optional<Voucher> findById(UUID voucherId){
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> findAll(){
        return voucherRepository.findAll();
    }
}
