package org.prgrms.kdt.voucher;

import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository {
    @NonNull
    private static final String PATH = "voucher-list.csv";
    private final File file = new File(PATH);

    private Map<UUID, Voucher> voucherLoadingMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void postConstruct() throws IOException {
        if(file.exists() == false)
            file.createNewFile();

        var br = new BufferedReader(new FileReader(file));

        String line;
        while((line = br.readLine()) != null){
            var st = new StringTokenizer(line, ",");

            var voucherType = VoucherType.valueOf(st.nextToken());
            var voucherId = UUID.fromString(st.nextToken());
            var size = Long.parseLong(st.nextToken());

            var newVoucher = Voucher.voucherFactory(voucherType, size, voucherId);
            if(newVoucher.isPresent())
                voucherLoadingMap.put(voucherId, newVoucher.get());
        }
        br.close();

        System.out.println("FileVoucherRepository created");
    }

    @PreDestroy
    public void preDestroy() throws IOException {
        var bw = new BufferedWriter(new FileWriter(file));

        for(var voucher : voucherLoadingMap.values()){
            bw.write(voucher.toString());
            bw.newLine();
        }
        bw.flush();
        bw.close();

        System.out.println("file saved successfully!!!");
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public boolean save(Voucher voucher) {
        if(voucher == null)
                return false;

        voucherLoadingMap.put(voucher.getVoucherId(), voucher);
        return true;
    }

    @Override
    public List<Voucher> getAllVouchers() {
        var voucherList = new ArrayList<>(voucherLoadingMap.values());

        return voucherList;
    }
}
