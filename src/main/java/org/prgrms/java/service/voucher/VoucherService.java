package org.prgrms.java.service.voucher;

import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;
import org.prgrms.java.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher saveVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherException(String.format("Can not find a voucher for %s", voucherId)));
    }

    public List<Voucher> getVoucherByOwner(UUID customerId) {
        return voucherRepository.findByCustomer(customerId);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public Voucher useVoucher(Voucher voucher) {
        if (voucher.isUsed()) {
            throw new VoucherException("This voucher has been already used.");
        }

        voucher.setUsed(true);
        return voucherRepository.update(voucher);
    }

    public Voucher allocateVoucher(Voucher voucher, UUID ownerId) {
        if (voucher.getOwnerId() != null) {
            throw new VoucherException("This voucher has been already allocated.");
        }

        voucher.setOwnerId(ownerId);
        return voucherRepository.update(voucher);
    }

    public Voucher detachOwnerFromVoucher(Voucher voucher) {
        voucher.setOwnerId(null);
        return voucherRepository.update(voucher);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    public long deleteAllVouchers() {
        return voucherRepository.deleteAll();
    }
}
