package com.pgms.part1.domain.voucher.repository;

import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.util.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Profile("local")
@Repository
public class VoucherFileRepository implements VoucherRepository {
    private final Logger log = LoggerFactory.getLogger(VoucherFileRepository.class);
    private final static Map<Long, Voucher> voucherMap = new HashMap<Long, Voucher>();
    private final String filePath;
    private File file;
    private final FileService fileService;

    public VoucherFileRepository(@Value("${file.path.voucher}") String filePath, FileService fileService) {
        this.filePath = filePath;
        file = new File(filePath);
        this.fileService = fileService;
        if(!file.exists()) {
            fileService.createFile(file);
        }
        voucherMapper(fileService.loadFile(filePath));
    }

    private void voucherMapper(List<String[]> voucherInfoList){
        try {
            voucherInfoList.stream().forEach(data -> {
                Long id = Long.parseLong(data[0]);
                int discount = Integer.parseInt(data[1]);
                VoucherDiscountType discountType = Enum.valueOf(VoucherDiscountType.class, data[2]);
                voucherMap.put(id, Voucher.newVocher(id, discount, discountType));
            });
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new RuntimeException("file format is not correct");
        }
    }

    private List<String> voucherToString(){
        List<String> voucherStringList = new ArrayList<>();

        for (Voucher voucher : voucherMap.values()) {
            voucherStringList.add(voucher.getId() + "," +
                    voucher.getDiscount() + "," +
                    voucher.getVoucherDiscountType() + "\n");
        }

        return voucherStringList;
    }

    @Override
    public List<Voucher> list() {
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public void add(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);
        fileService.saveFile(voucherToString(),filePath);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Voucher> findVoucherByWallets(List<Wallet> wallet) {
        return null;
    }
}
