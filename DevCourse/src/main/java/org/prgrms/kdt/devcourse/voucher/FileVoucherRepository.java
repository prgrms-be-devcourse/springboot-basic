package org.prgrms.kdt.devcourse.voucher;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
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
        return null;
    }
}
