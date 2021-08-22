package org.prgrms.kdt.Voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Qualifier("file")
public class FileVoucherRepository implements VoucherRepository{
    ManageFileVoucher file=new ManageFileVoucher();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher save(Voucher voucher)  {
        file.write(voucher);
       return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return file.ReadAll();
    }
}
