package org.prgrms.kdtbespring.voucher;

import org.prgrms.kdtbespring.fileio.ReadFile;
import org.prgrms.kdtbespring.fileio.VoucherReadCsvFile;
import org.prgrms.kdtbespring.fileio.VoucherWriteCsvFile;
import org.prgrms.kdtbespring.fileio.WriteFile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
@Qualifier("file")
public class FileVoucherRepository implements VoucherRepository{
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        ReadFile readCsvFile = new VoucherReadCsvFile();
        Optional<Voucher> voucher = readCsvFile.readRecord(voucherId);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        ReadFile readCsvFile = new VoucherReadCsvFile();
        List<Voucher> vouchers = readCsvFile.readFile();
        return vouchers;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        WriteFile writeCsvFile = new VoucherWriteCsvFile();
        writeCsvFile.writeFile(voucher);
        return voucher;
    }
}
