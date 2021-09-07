package prgms.springbasic.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import prgms.springbasic.voucher.FixedAmountVoucher;
import prgms.springbasic.voucher.PercentDiscountVoucher;
import prgms.springbasic.voucher.Voucher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Qualifier("fileVoucherRepository")
public class FileVoucherRepository implements VoucherRepository {

    private static final String path = "src/main/VoucherData.csv";
    private final File file;
    private final BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public FileVoucherRepository() throws IOException {
        file = new File(path);

        if (!file.exists()) {
            file.createNewFile();
        }

        bufferedWriter = new BufferedWriter(new FileWriter(file, true));
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String line;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null) {
                String[] splited = line.split(", ");
                if (splited[2].equals(String.valueOf(voucherId))) {
                    if (splited[0].equals("FixedAmountVoucher")) {
                        return Optional.of(new FixedAmountVoucher(UUID.fromString(splited[2]), Long.parseLong(splited[1])));
                    } else if (splited[0].equals("PercentDiscountVoucher")) {
                        return Optional.of(new PercentDiscountVoucher(UUID.fromString(splited[2]), Long.parseLong(splited[1])));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            bufferedWriter.write(voucher.toString() + '\n');
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    @Override
    public List<Voucher> getVoucherList() {
        List<Voucher> voucherList = new ArrayList<>();
        String line;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));

            while ((line = bufferedReader.readLine()) != null) {
                String[] component = line.split(", ");
                if ("FixedAmountVoucher".equals(component[0]))
                    voucherList.add(new FixedAmountVoucher(UUID.fromString(component[2]), Long.parseLong(component[1])));
                else if ("PercentDiscountVoucher".equals(component[0]))
                    voucherList.add(new PercentDiscountVoucher(UUID.fromString(component[2]), Long.parseLong(component[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return voucherList;
    }
}
