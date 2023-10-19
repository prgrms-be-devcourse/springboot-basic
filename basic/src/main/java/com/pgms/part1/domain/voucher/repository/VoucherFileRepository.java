package com.pgms.part1.domain.voucher.repository;

import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Profile("file")
@Repository
public class VoucherFileRepository implements VoucherRepository {
    private final static Map<UUID, Voucher> voucherMap = new HashMap<>();
    private String FILE_PATH;
    private File file;

    public VoucherFileRepository(@Value("${file.path.voucher}") String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
        file = new File(FILE_PATH);
        if(!file.exists()) {
            createFile();
        }
        loadfile();
    }

    private void loadfile() {
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(",");

                UUID id = UUID.fromString(data[0]);
                int discount = Integer.parseInt(data[1]);
                VoucherDiscountType discountType = Enum.valueOf(VoucherDiscountType.class, data[1]);

                if(discountType == VoucherDiscountType.PERCENT_DISCOUNT)
                    voucherMap.put(id, Voucher.newPercentDiscountVoucher(id, discount));
                else if(discountType == VoucherDiscountType.FIXED_AMOUNT_DISCOUNT)
                    voucherMap.put(id, Voucher.newFixedAmountDiscountVoucher(id, discount));
            }
        } catch (IOException e) {
            throw new RuntimeException("can not load file!!");
        }
    }

    private void createFile() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveFile(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Voucher voucher : voucherMap.values()) {
                bw.write(voucher.getId() + "," +
                        voucher.getDiscount() + "," +
                        voucher.getVoucherDiscountType() + "\n");
            }
        }
        catch (IOException e){
            throw new RuntimeException("can not save file!!");
        }
    }

    @Override
    public List<Voucher> list() {
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public void add(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);
        saveFile();
    }
}
