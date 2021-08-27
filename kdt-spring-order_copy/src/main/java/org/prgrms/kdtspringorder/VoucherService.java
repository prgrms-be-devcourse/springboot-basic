package org.prgrms.kdtspringorder;

import org.springframework.stereotype.Service;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.text.MessageFormat;
@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    private final List<Voucher> vouchers = new ArrayList<>();

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));

    }

    public Voucher createVoucher(VoucherType voucherType) {
        UUID voucherId = UUID.randomUUID();
        if (voucherType.equals(VoucherType.FixedAmountVoucher)) {
            return new FixedAmountVoucher(voucherId, 1000);
        } else  {
            return new FixedAmountVoucher(voucherId, 10);
        }
    }
    public void addVoucher(Voucher voucher){
        voucherRepository.insert(voucher);
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.getAllVoucher();
    }
    public void useVoucher(Voucher voucher) {
    }
}
