package com.prgrms.management.voucher.repository;

import com.prgrms.management.voucher.domain.FixedAmountVoucher;
import com.prgrms.management.voucher.domain.PercentAmountVoucher;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {
    private final String VOUCHER_FILE_NAME = "src/main/resources/voucher.csv";

    @Override
    public Voucher insert(Voucher voucher) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(VOUCHER_FILE_NAME, true))) {
            bufferedWriter.write(voucher.getVoucherId() + "," + voucher.getAmount() + "," + voucher.getVoucherType());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        //try-with-resource
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(VOUCHER_FILE_NAME))) {
            String reader;
            while ((reader = bufferedReader.readLine()) != null) {
                String[] voucherInfo = reader.split(",");
                VoucherType type = VoucherType.of(voucherInfo[2]);
                if (type.equals(VoucherType.FIXED))
                    voucherList.add(new FixedAmountVoucher(UUID.fromString(voucherInfo[0]), Long.parseLong(voucherInfo[1])));
                else
                    voucherList.add(new PercentAmountVoucher(UUID.fromString(voucherInfo[0]), Long.parseLong(voucherInfo[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucherList;
    }
}
