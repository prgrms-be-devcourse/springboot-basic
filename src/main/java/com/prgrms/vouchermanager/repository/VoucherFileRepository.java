package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Voucher;
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

    public void create(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);

        fileIO.updateFile(vouchers);
    }

    public List<Voucher> list() {
        return vouchers
                .values()
                .stream()
                .toList();
    }
}
