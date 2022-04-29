package org.prgrms.kdtspringvoucher.voucher.entity;

import org.prgrms.kdtspringvoucher.utils.Util;
import org.prgrms.kdtspringvoucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRespository) {
        this.voucherRepository = voucherRespository;
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.findAll();
    }

    public List<Voucher> getVouchersByCustomer(String customerId){
        return voucherRepository.findVouchersByCustomer(UUID.fromString(customerId));
    }


    public void deleteVoucher(String customerId, String voucherId) {
        boolean isVoucher = voucherRepository.validateVoucher(UUID.fromString(voucherId));
        if (isVoucher == false) {
            throw new RuntimeException("voucher id가 존재하지 않습니다.");
        }

        try {
            voucherRepository.deleteVoucherFromCustomer(UUID.fromString(customerId), UUID.fromString(voucherId));
        } catch (Exception e) {
            throw new RuntimeException("delete voucher from wallet exception: ", e);
        }
    }

    public void assignVoucherToCustoemr(String customerId, String voucherId) {
        try {
            voucherRepository.allocateVoucherToCustomer(UUID.fromString(customerId), UUID.fromString(voucherId));
        } catch (Exception e) {
            throw new RuntimeException("allocateVoucherToCustomer exception: ", e);
        }
    }


    public Optional<Voucher> createVoucher(Voucher voucher) {
        try {
            return voucherRepository.save(voucher);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean validateVoucher(String voucherId) {
        if (Util.isValidUUID(voucherId) == false) {
            return false;
        }
        var customer = voucherRepository.findById(UUID.fromString(voucherId));
        return !customer.isEmpty();
    }
}
