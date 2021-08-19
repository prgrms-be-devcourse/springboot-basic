package org.programmers.kdt.voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Qualifier("File")
public class FileVoucherRepository implements VoucherRepository {
    private final String path = "";
    private final String filename = "VoucherData.txt";
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            FileReader fileReader = new FileReader(path + filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            while (null != (line = bufferedReader.readLine())) {
                String[] data = line.split(" ");
                UUID uuid = UUID.fromString(data[0]);

                if (voucherId.equals(uuid)) {
                    String className = data[1];
                    Long discount = Long.parseLong(data[2]);

                    if (className.equals("FixedAmountVoucher")) {
                        return Optional.of(new FixedAmountVoucher(uuid, discount));
                    } else if (className.equals("PercentDiscountVoucher")) {
                        return Optional.of(new PercentDiscountVoucher(uuid, discount));
                    }
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e. printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            FileWriter fileWriter = new FileWriter(path + filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            UUID uuid = voucher.getVoucherId();
            String[] voucherClassInfo = voucher.getClass().toString().split("\\.");
            String voucherType = voucherClassInfo[voucherClassInfo.length - 1];
            Long discount = voucher.getDiscount();

            bufferedWriter.append(uuid + " " + voucherType + " " + discount);
            bufferedWriter.newLine();

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(path + filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            while (null != (line = bufferedReader.readLine())) {
                // TODO: Make voucher and add it to the list
                String[] data = line.split(" ");
                UUID uuid = UUID.fromString(data[0]);
                String className = data[1];
                Long discount = Long.parseLong(data[2]);

                if (className.equals("FixedAmountVoucher")) {
                    voucherList.add(new FixedAmountVoucher(uuid, discount));
                } else if (className.equals("PercentDiscountVoucher")) {
                    voucherList.add(new PercentDiscountVoucher(uuid, discount));
                }
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e. printStackTrace();
        }

        return voucherList;
    }
}
