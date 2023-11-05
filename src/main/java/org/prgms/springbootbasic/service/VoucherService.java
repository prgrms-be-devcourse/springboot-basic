package org.prgms.springbootbasic.service;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.prgms.springbootbasic.exception.EntityNotFoundException;
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

    public VoucherType convertToType(int voucherSeq) {
        return VoucherType.getTypeFromSeq(voucherSeq);
    }

    public VoucherType convertToType(String policyName) {
        return VoucherType.getTypeFromName(policyName);
    }

    public Voucher insert(VoucherType voucherType, long discountDegree) {
        VoucherPolicy voucherPolicy = voucherType.create();
        Voucher voucher = new Voucher(UUID.randomUUID(), discountDegree, voucherPolicy);

        return voucherRepository.upsert(voucher);
    }

    public Voucher update(UUID voucherId, VoucherType voucherType, long discountDegree) {
        findById(voucherId).orElseThrow(EntityNotFoundException::new);

        Voucher updateVoucher = new Voucher(voucherId, discountDegree, voucherType.create());

        return voucherRepository.upsert(updateVoucher);
    }

    public Optional<Voucher> findById(UUID voucherId){
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> findAll(){
        return voucherRepository.findAll();
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
