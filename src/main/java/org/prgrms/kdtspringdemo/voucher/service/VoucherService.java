package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.VoucherType;
import org.prgrms.kdtspringdemo.voucher.Voucher;
import org.prgrms.kdtspringdemo.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public Voucher saveVoucher(String voucherType, String value) {
        Voucher voucher = VoucherType.createVoucher(voucherType, Long.parseLong(value));
        voucherRepository.insert(voucher);
        if (voucher == null)
        {
            logger.error(MessageFormat.format("Invalid create command. Your input -> {0}, {1}", voucherType, value));
            System.out.println("[ERROR]Invalid create command");
            System.out.println(MessageFormat.format("Your input : {0}, {1}", voucherType, value));
        }
        return voucher;
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public void deleteVoucher(String voucherId) {
        voucherRepository.deleteByVoucherId(voucherId);
    }
}
