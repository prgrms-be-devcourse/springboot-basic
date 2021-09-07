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

//@Repository
//@Qualifier("fileVoucherRepository")
public class FileVoucherRepository implements VoucherRepository {
    private final File file;

    public FileVoucherRepository() throws IOException {
        file = new File("Vouchers.txt");

        if(!file.exists()){
            System.out.println("create new file");
            file.createNewFile();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) throws IOException {
        Reader fileReader = new FileReader("Voucher.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = "";
        while (null != (line = bufferedReader.readLine())) {
            String[] data = line.split(", ");
            UUID fileVoucherId = UUID.fromString(data[0]);

            if (fileVoucherId == voucherId) {
                String voucherType = data[1];
                long discountValue = Long.parseLong(data[2]);

                return Optional.ofNullable(makeVoucher(voucherType, voucherId, discountValue));
            }
        }
        return Optional.empty();
    }

    @Override
    public Voucher save(Voucher voucher) throws IOException {
        FileWriter fileWriter = new FileWriter("Vouchers.txt", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        UUID voucherId = voucher.getVoucherId();
        String[] voucherInfo = voucher.getClass().toString().split("\\.");
        String voucherType = voucherInfo[voucherInfo.length - 1];
        long discountValue = voucher.getDiscountValue();

        bufferedWriter.append(voucherId + ", " + voucherType + ", " + discountValue);
        bufferedWriter.newLine();

        return voucher;
    }

    @Override
    public List<Voucher> getVoucherList() throws IOException {
        List<Voucher> voucherList = new ArrayList<>();
        Reader fileReader = new FileReader("Vouchers.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = "";
        while (null != (line = bufferedReader.readLine())) {
            String[] data = line.split(", ");
            UUID voucherId = UUID.fromString(data[0]);
            String voucherType = data[1];
            Long discountValue = Long.parseLong(data[2]);

            voucherList.add(makeVoucher(voucherType, voucherId, discountValue));
        }

        return voucherList;
    }

    private Voucher makeVoucher(String voucherType, UUID voucherId, long discountValue) {
        Voucher voucher = null;
        if (voucherType.equals("FixedAmountVoucher")) {
            voucher = new FixedAmountVoucher(voucherId, discountValue);
        } else if (voucherType.equals("PercentDiscountVoucher")) {
            voucher = new PercentDiscountVoucher(voucherId, discountValue);
        }
        return voucher;
    }
}
