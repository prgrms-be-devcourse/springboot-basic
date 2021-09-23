package org.prgrms.kdtspringorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;
import java.util.*;
import java.text.MessageFormat;
@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));

    }

    public void addVoucher(Voucher voucher){
        logger.info("Initialized voucher repository.");
        voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> getAllVouchers(){
        return voucherRepository.getAllVoucher();
    }
    public void useVoucher(Voucher voucher) {
    }

    public static VoucherType from(String value){
        return VoucherType.valueOf(value.toUpperCase());
    }
}
