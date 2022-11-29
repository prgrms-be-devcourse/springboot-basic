package org.prgrms.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.memory.VoucherDBMemory;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.dto.VoucherDTO;
import org.prgrms.voucher.voucherType.VoucherType;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherDBMemory voucherDBMemory;

    public VoucherService(VoucherDBMemory voucherDBMemory) {
        this.voucherDBMemory = voucherDBMemory;
    }

    public Voucher save(VoucherDTO voucherDTO) {
        VoucherType voucherType = VoucherType.of(voucherDTO.getType());
        Voucher voucher = voucherType.generateVoucher(voucherType.generateAmount(voucherDTO.getAmount()));
        return voucherDBMemory.save(voucher);
    }

    public List<Voucher> findAll() {
        return voucherDBMemory.findAll();
    }

    public Optional<Voucher> findById(UUID id) {
        return voucherDBMemory.findById(id);
    }

    public void deleteById(UUID id) {
        voucherDBMemory.deleteById(id);
    }

    public void deleteAll() {
        voucherDBMemory.deleteAll();
    }

    public Voucher update(Voucher voucher) {
        return voucherDBMemory.update(voucher);
    }
}
