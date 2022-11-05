package org.programmers.spbw1.voucher;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
public class VoucherService {
    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID id){
        return voucherRepository
                .getVoucherById(id)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("No such Voucher, id :  {0}", id)));
    }
}
