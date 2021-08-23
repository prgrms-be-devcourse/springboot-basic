package org.prgrms.kdt.devcourse.voucher;

import org.prgrms.kdt.devcourse.io.FileLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
@Profile("default")
public class FileVoucherRepository implements VoucherRepository{
    private Map<UUID,Voucher> voucherList = new ConcurrentHashMap<>();

    @Value("${file.voucher.voucher}")
    private String voucherFileName;

    @PostConstruct
    public void postConstruct() {

        List<String> fileLines = FileLoader.loadFile(voucherFileName);
        for(String line : fileLines){
            String [] oneLineDataArr = line.split(",");
            try {
                UUID filedDataUUID = UUID.fromString(oneLineDataArr[0]);
                long filedDataVoucherAmount = Long.parseLong(oneLineDataArr[1]);
                VoucherType filedDataVoucherType = VoucherType.valueOf(oneLineDataArr[2]);

                if(filedDataVoucherType == VoucherType.FIXED)
                    voucherList.put(filedDataUUID, new FixedAmountVoucher(filedDataUUID, filedDataVoucherAmount));
                else if(filedDataVoucherType == VoucherType.PERCENT)
                    voucherList.put(filedDataUUID, new PercentDiscountVoucher(filedDataUUID, filedDataVoucherAmount));
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }

        }

    }

    @PreDestroy
    public void preDestroy() throws IOException {
        String filePath = System.getProperty("user.dir")+"/"+voucherFileName;
        File csvFile = new File(filePath);


        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(csvFile));
        for (Map.Entry<UUID, Voucher> entry : voucherList.entrySet()) {
            Voucher voucher = entry.getValue();
            String newData = voucher.getVoucherId() + "," + voucher.getVoucherAmount() + "," + voucher.getVoucherType();
            bufferedWriter.write(newData);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherList.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        voucherList.put(voucher.getVoucherId(),voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherList.values().stream().toList();
    }
}
