package org.programmers.kdt.weekly.voucher.repository;

import org.programmers.kdt.weekly.voucher.model.FixedAmountVoucher;
import org.programmers.kdt.weekly.voucher.model.PercentDiscountVoucher;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.UUID;


@Profile("local")
@Repository
public class FileRepository implements VoucherRepository {
    private final File FILE = new File("fileDB.csv");
    private final BufferedWriter BUFFER_WRITER = FileBuffer.getBufferWriter(FILE);
    private BufferedReader bufferedReader;

    @Override
    public void insert(UUID voucherId, Voucher voucher) {
        try {
            BUFFER_WRITER.write(voucher.getVoucherType() + "," + voucherId + "," + voucher.getValue());
            BUFFER_WRITER.newLine();
            BUFFER_WRITER.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getSize() {
        int size = 0;
        try {
            bufferedReader = FileBuffer.getBufferedReader(FILE);
            while ((bufferedReader.readLine()) != null) {
                size++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }


    @Override
    public void showAll() {

        String line = "";
        try {
            bufferedReader = FileBuffer.getBufferedReader(FILE);
            while ((line = bufferedReader.readLine()) != null) {
                String arr[] = line.split(",");
                if (arr[0].equals(VoucherType.FixedVoucherRepository.toString())) {
                    System.out.println(FixedAmountVoucher.builder()
                            .voucherId(UUID.fromString(arr[1]))
                            .value(Integer.parseInt(arr[2]))
                            .build()
                            .toString());
                } else {
                    System.out.println(PercentDiscountVoucher.builder()
                            .voucherId(UUID.fromString(arr[1]))
                            .value(Integer.parseInt(arr[2]))
                            .build()
                            .toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
