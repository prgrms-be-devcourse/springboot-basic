package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.io.FileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("prod")
public class VoucherFileRepository implements VoucherRepository {

    private final Map<UUID, Voucher> vouchers = new HashMap<>();
    private final FileIO fileIO;
    @Autowired
    public VoucherFileRepository(@Value("${csv.voucher}") String fileName) {
        this.fileIO = new FileIO(fileName);
        fileIO.fileToVoucherMap(vouchers);
    }

    @Override
    public Voucher create(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);

        fileIO.updateFile(vouchers);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers
                .values()
                .stream()
                .toList();
    }

    @Override
    public Voucher findById(UUID id) {
        return vouchers.get(id);
    }

    @Override
    public Voucher updateDiscount(Voucher updateVoucher) {
        vouchers.computeIfPresent(updateVoucher.getId(), (k, v) -> updateVoucher);

        fileIO.updateFile(vouchers);
        return updateVoucher;
    }

    @Override
    public int delete(UUID id) {
        vouchers.remove(id);
        return 1;
    }
}
