package org.prgms.order.voucher.service;

import org.prgms.order.voucher.entity.Voucher;
import org.prgms.order.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(
                        MessageFormat.format("Can not find a voucher for {0}", voucherId)
                ));
    }

    public void useVoucher(Voucher voucher) {
    }


    public Voucher insert(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> findAllVoucher() {
        return voucherRepository.findAllVoucher();
    }

    public String getVoucherInfoById(UUID voucherId) {
        return voucherRepository.getVoucherInfoById(voucherId);
    }
}
