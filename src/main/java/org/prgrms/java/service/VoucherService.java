package org.prgrms.java.service;

import org.prgrms.java.common.Mapper;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.badrequest.VoucherBadRequestException;
import org.prgrms.java.exception.notfound.VoucherNotFoundException;
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

    public Voucher getVoucherById(String voucherId) {
        try {
            return voucherRepository.findById(UUID.fromString(voucherId))
                    .orElseThrow(VoucherNotFoundException::new);
        } catch (IllegalArgumentException e) {
            throw new VoucherBadRequestException();
        }
    }

    public List<Voucher> getVoucherByOwnerId(String customerId) {
        try {
            return voucherRepository.findByCustomer(UUID.fromString(customerId));
        } catch (IllegalArgumentException e) {
            throw new VoucherBadRequestException();
        }
    }

    public List<Voucher> getAllExpiredVouchers() {
        return voucherRepository.findExpiredVouchers();
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher updateVoucher(String voucherId, String ownerId, LocalDateTime expiredAt, boolean used) {
        Voucher voucher = getVoucherById(voucherId);
        try {
            voucher.setOwnerId(UUID.fromString(ownerId));
        } catch (IllegalArgumentException e) {
            throw new VoucherBadRequestException();
        }
        voucher.setExpiredAt(expiredAt);
        voucher.setUsed(used);

        return voucherRepository.update(voucher);
    }

    public Voucher useVoucher(String voucherId) {
        Voucher voucher = getVoucherById(voucherId);
        if (voucher.isUsed()) {
            throw new VoucherBadRequestException("이미 사용된 바우처입니다.");
        }

        voucher.setUsed(true);
        return voucherRepository.update(voucher);
    }

    public Voucher allocateVoucher(String voucherId, String ownerId) {
        Voucher voucher = getVoucherById(voucherId);
        if (voucher.getOwnerId() != null) {
            throw new VoucherBadRequestException("다른 사용자가 보유 중인 바우처입니다.");
        }

        try {
            voucher.setOwnerId(UUID.fromString(ownerId));
        } catch (IllegalArgumentException e) {
            throw new VoucherBadRequestException();
        }
        return voucherRepository.update(voucher);
    }

    public Voucher detachOwnerFromVoucher(String voucherId) {
        Voucher voucher = getVoucherById(voucherId);
        voucher.setOwnerId(null);
        return voucherRepository.update(voucher);
    }

    public void deleteVoucher(String voucherId) {
        try {
            voucherRepository.delete(UUID.fromString(voucherId));
        } catch (IllegalArgumentException e) {
            throw new VoucherBadRequestException();
        }
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }
}
