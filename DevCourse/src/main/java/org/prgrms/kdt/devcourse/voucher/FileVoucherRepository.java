package org.prgrms.kdt.devcourse.voucher;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
@Profile("default")
public class FileVoucherRepository implements VoucherRepository{
    private Map<UUID,Voucher> voucherList = new ConcurrentHashMap<>();
    private final String filePath = System.getProperty("user.dir")+"/voucher.csv";
    private final File csvFile = new File(filePath);


    @PostConstruct
    public void postConstruct() throws IOException {

            if(!csvFile.exists())
                return;

            BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
            String line;
            while ((line = bufferedReader.readLine())!=null){
                String [] oneLineDataArr = line.split(",");

                UUID filedDataUUID = UUID.fromString(oneLineDataArr[0]);
                long filedDataVoucherAmount = Long.parseLong(oneLineDataArr[1]);
                VoucherType filedDataVoucherType = VoucherType.valueOf(oneLineDataArr[2]);

                if(filedDataVoucherType == VoucherType.FIXED)
                    voucherList.put(filedDataUUID, new FixedAmountVoucher(filedDataUUID, filedDataVoucherAmount));
                else if(filedDataVoucherType == VoucherType.PERCENT)
                    voucherList.put(filedDataUUID, new PercentDiscountVoucher(filedDataUUID, filedDataVoucherAmount));

            }
            bufferedReader.close();

    }

    @PreDestroy
    public void preDestroy() throws IOException {
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
