package org.prgrms.kdt.repository;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Map<UUID, Voucher> getVoucherList() {
        return storage;
    }


    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @PostConstruct
    public void readStorage(){
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File("./VoucherList.txt");
            if(file.exists()) {
                BufferedReader input = new BufferedReader(new FileReader("./VoucherList.txt"));
                while ((sb.append(input.readLine())).length() != -1 && !sb.toString().equals("null")) {
                    String[] st = sb.toString().split(" ");
                    Voucher voucher;
                    if (st[2].equals("Fixed")) {
                        voucher = new FixedAmountVoucher(UUID.fromString(st[0]), Long.parseLong(st[1]));
                    } else
                        voucher = new PercentDiscountVoucher(UUID.fromString(st[0]), Long.parseLong((st[1])));
                    storage.put(voucher.getVoucherId(), voucher);
                    sb.setLength(0);
                }
                input.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void saveStorage(){
        StringBuilder sb = new StringBuilder();
        try {
            FileOutputStream output = new FileOutputStream("./VoucherList.txt");

            for (UUID id : storage.keySet()){
                Voucher voucher = storage.get(id);
                sb.append(voucher.getVoucherId() + " ");
                sb.append(voucher.getVoucherAmount() + " ");
                sb.append(voucher.getVoucherType() + "\n");
                output.write(sb.toString().getBytes());
                //System.out.println(sb.toString());
                sb.setLength(0);
            }

            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
