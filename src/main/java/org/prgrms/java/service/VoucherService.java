package org.prgrms.java.service;

import org.prgrms.java.common.Mapper;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;
import org.prgrms.java.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher saveVoucher(UUID ownerId, String type, long amount, LocalDateTime expiredAt) {
        Voucher voucher = Mapper.mapToVoucher(type, UUID.randomUUID(), ownerId, amount, LocalDateTime.now(), expiredAt, false);
        return voucherRepository.insert(voucher);
    }

    public Voucher getVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherException(String.format("Can not find a voucher for %s", voucherId)));
    }

    public List<Voucher> getVoucherByOwnerId(UUID customerId) {
        return voucherRepository.findByCustomer(customerId);
    }

    public List<Voucher> getAllExpiredVouchers() {
        return voucherRepository.findExpiredVouchers();
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher updateVoucher(UUID voucherId, UUID ownerId, LocalDateTime expiredAt, boolean used) {
        Voucher voucher = getVoucherById(voucherId);
        voucher.setOwnerId(ownerId);
        voucher.setExpiredAt(expiredAt);
        voucher.setUsed(used);

        return voucherRepository.update(voucher);
    }

    public Voucher useVoucher(UUID voucherId) {
        Voucher voucher = getVoucherById(voucherId);
        if (voucher.isUsed()) {
            throw new VoucherException("This voucher has been already used.");
        }

        voucher.setUsed(true);
        return voucherRepository.update(voucher);
    }

    public Voucher allocateVoucher(UUID voucherId, UUID ownerId) {
        Voucher voucher = getVoucherById(voucherId);
        if (voucher.getOwnerId() != null) {
            throw new VoucherException("This voucher has been already allocated.");
        }

        voucher.setOwnerId(ownerId);
        return voucherRepository.update(voucher);
    }

    public Voucher detachOwnerFromVoucher(UUID voucherId) {
        Voucher voucher = getVoucherById(voucherId);
        voucher.setOwnerId(null);
        return voucherRepository.update(voucher);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }
}
