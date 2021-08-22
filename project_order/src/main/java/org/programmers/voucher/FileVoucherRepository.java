package org.programmers.voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Qualifier("fileVoucherRepository")
public class FileVoucherRepository implements VoucherRepository {

    private final File file;
    private final BufferedWriter bw;
    private BufferedReader br;

    public FileVoucherRepository() throws IOException {
        file = new File("vouchers.txt");

        if (!file.exists()) {
            System.out.println("create new file");
            file.createNewFile();
        }

        bw = new BufferedWriter(new FileWriter(file, true));
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            bw.write(voucher.getClass().getSimpleName() + " " + voucher.getVoucherId() + " " + voucher.getValue() + '\n');
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    @Override
    public List<Voucher> getAllVouchers() {
        List<Voucher> voucherList = new ArrayList<>();
        String str;

        try {
            br = new BufferedReader(new FileReader(file));

            while (true) {
                if ((str = br.readLine()) == null) break;

                String[] voucherInfo = str.split(" ");
                if ("FixedAmountVoucher".equals(voucherInfo[0]))
                    voucherList.add(new FixedAmountVoucher(UUID.fromString(voucherInfo[1]), Long.parseLong(voucherInfo[2])));
                else if ("PercentDiscountVoucher".equals(voucherInfo[0]))
                    voucherList.add(new PercentDiscountVoucher(UUID.fromString(voucherInfo[1]), Long.parseLong(voucherInfo[2])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return voucherList;
    }

}
