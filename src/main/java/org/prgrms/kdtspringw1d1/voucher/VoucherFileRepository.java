package org.prgrms.kdtspringw1d1.voucher;

import org.prgrms.kdtspringw1d1.utils.FileIOUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class VoucherFileRepository implements VoucherRepository{

    private final static String fileName = "voucher.txt";
    private final FileIOUtils<Voucher> fileIOUtils = new FileIOUtils<Voucher>();

    @Override
    public Optional<Voucher> findById(UUID voucherId){
        List<Voucher> vouchers = fileIOUtils.readFile(fileName);
        return Optional.ofNullable(vouchers.stream().filter(voucher -> voucher.getVoucherId()==voucherId).findAny().orElse(null));
    }

    @Override
    public Voucher createFixedAmountVoucher(){
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        return fileIOUtils.writeFile(fileName,fixedAmountVoucher);
    }

    @Override
    public Voucher createPercentDiscountVoucher(){
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        return fileIOUtils.writeFile(fileName,percentDiscountVoucher);
    }

    @Override
    public List<Voucher> findAll(){
        List<Voucher> vouchers = fileIOUtils.readFile(fileName);
        return Optional.ofNullable(vouchers).orElse(null);
    }
}
