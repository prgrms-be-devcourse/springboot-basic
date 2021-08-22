package org.prgrms.kdt.voucher;

import com.opencsv.exceptions.CsvValidationException;
import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final CsvVoucherRepository csvVoucherRepository;

    public VoucherService(@Qualifier("csv") VoucherRepository voucherRepository, CsvVoucherRepository csvVoucherRepository) {
        this.voucherRepository = voucherRepository;
        this.csvVoucherRepository = csvVoucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public Voucher createFixedAmountVoucher(int fixedAmount) {
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), fixedAmount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher createPercentDiscountVoucher(int percentDiscount) {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), percentDiscount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Map<UUID, Voucher> getVoucherList() {
        return voucherRepository.getStorage();
    }

    public void saveVoucher(String filePath) throws IOException {
        csvVoucherRepository.save(filePath);
    }

    public void loadVoucher(String filePath) {
        var voucherList = csvVoucherRepository.load(filePath);
        if (voucherList.isPresent()) {
            for (Voucher voucher: voucherList.get().values()) {
                voucherRepository.insert(voucher);
            }
        }
    }

    public void useVoucher(Voucher voucher) {
    }
}
