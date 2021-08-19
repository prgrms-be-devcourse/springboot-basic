package org.prgrms.kdt.devcourse.voucher;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository{
    private final String filePath = System.getProperty("user.dir")+"/voucher.csv";
    private File csvFile = new File(filePath);
    @Override
    public Optional<Voucher> findById(UUID voucherId) {



        return Optional.empty();

    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(csvFile,true));

            String newData = voucher.getVoucherId()+","+voucher.getVoucherAmount()+","+voucher.getVoucherType();
            bufferedWriter.write(newData);
            bufferedWriter.newLine();

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> filedVoucherList = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
            String line;
            while ((line = bufferedReader.readLine())!=null){
                String [] dataArr = line.split(",");

                UUID dataUUID = UUID.fromString(dataArr[0]);

                    long dataVoucherAmount = Long.parseLong(dataArr[1]);
                    VoucherType dataVoucherType = VoucherType.valueOf(dataArr[2]);

                    if(dataVoucherType == VoucherType.FIXED_AMOUNT)
                        filedVoucherList.add(new FixedAmountVoucher(dataUUID, dataVoucherAmount));
                    else if(dataVoucherType == VoucherType.PERCENTAGE)
                        filedVoucherList.add(new FixedAmountVoucher(dataUUID, dataVoucherAmount));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return filedVoucherList;
    }
}
