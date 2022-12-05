package org.prgrms.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.memory.VoucherDBMemory;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherType;
import org.prgrms.dto.VoucherDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VoucherService {

    private final VoucherDBMemory voucherDBMemory;

    public VoucherService(VoucherDBMemory voucherDBMemory) {
        this.voucherDBMemory = voucherDBMemory;
    }

    @Transactional
    public Voucher save(VoucherDTO voucherDTO) {
        VoucherType voucherType = VoucherType.of(voucherDTO.type());
        Voucher voucher =  voucherType.generateVoucher(voucherType.generateAmount(voucherDTO.amount()));
        return voucherDBMemory.save(voucher);
    }

    public List<Voucher> findAll() {
        return voucherDBMemory.findAll();
    }

    @Transactional
    public Optional<Voucher> findById(UUID id) {
        return voucherDBMemory.findById(id);
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherDBMemory.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        voucherDBMemory.deleteAll();
    }

    @Transactional
    public Voucher update(Voucher voucher) {
        return voucherDBMemory.update(voucher);
    }

    public List<Voucher> findByPeriod(String startDate, String endDate) {
        return voucherDBMemory.findByCreateDate(startDate, endDate);
    }

    public List<Voucher> findByType(String type) {
        return voucherDBMemory.findByType(type);
    }
}
