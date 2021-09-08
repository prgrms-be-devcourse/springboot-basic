package org.prgrms.kdt.assignments;


import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class VoucherFileRepository {

    private static final String VoucherFileName = "voucher.txt";


//    @Override
//    public void saveFile(VoucherData voucherData) {
//
//        FileUtil.write("1234", VoucherFileName);
//    }
////"VoucherNumber : "+String.valueOf(voucherData.getVoucherNumber()) + "DiscountAmount : " + voucherData.getDiscountAmount()
//    @Override
//    public void readFile() {
//        FileUtil.readText(new ClassPathResource(VoucherFileName));
//    }

//    @Override
//    public Optional<Voucher> findById(UUID voucherId) {
//        return Optional.empty();
//    }
//
//
//    @Override
//    public Voucher insert(Voucher voucher) {
//        System.out.println("저장");
//        saveFile(voucher);
//        return voucher;
//    }
//
//    @Override
//    public Map<UUID, Voucher> findAll() {
//        readFile();
//        return Collections.emptyMap();
//    }
}
