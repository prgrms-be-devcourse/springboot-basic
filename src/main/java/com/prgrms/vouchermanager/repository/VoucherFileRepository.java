package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.console.Reader;
import com.prgrms.vouchermanager.domain.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.exception.FileIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("prod")
public class VoucherFileRepository implements VoucherRepository {

    private final Map<UUID, Voucher> vouchers = new HashMap<>();
    private final BufferedReader vf;

    @Value("${csv.voucher}")
    String path;

    @Autowired
    public VoucherFileRepository(Reader reader) {
        this.vf = reader.getVf();
        fileToMap();
    }

    public void create(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
        updateFile();
    }

    public List<Voucher> list() {
        return vouchers
                .values()
                .stream()
                .toList();
    }

    private void fileToMap() {
        String line = "";

        while(true) {
            try {
                if ((line = vf.readLine()) == null) break;
            } catch (IOException e) {
                throw new FileIOException();
            }
            String[] split = line.split(",");
            Voucher voucher = null;
            UUID id = UUID.fromString(split[0]);
            long discount = Long.parseLong(split[2]);

            if(split[1].equals("fixed")) voucher = new FixedAmountVoucher(id, discount);
            else if(split[1].equals("percent")) voucher = new PercentAmountVoucher(id, discount);

            vouchers.put(id, voucher);
        }
    }

    private void updateFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));

            vouchers.forEach((key, voucher) -> {
                UUID id = voucher.getId();
                String type = voucher instanceof FixedAmountVoucher ? "fixed" : "percent";
                long discount = voucher instanceof FixedAmountVoucher ?
                        ((FixedAmountVoucher) voucher).getAmount() : ((PercentAmountVoucher) voucher).getPercent();

                try {
                    bw.write(id + "," + type + "," + discount + "\n");
                } catch (IOException e) {
                    throw new FileIOException();
                }
            });
            bw.close();
        } catch (IOException e) {
            throw new FileIOException();
        }
    }
}
