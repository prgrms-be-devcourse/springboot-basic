package org.programmers.kdtspring.repository.voucher;

import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.PercentDiscountVoucher;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Repository
//@Profile("default")
//public class FileVoucherRepository implements VoucherRepository {
//
//    private final String fileName = "voucherList.txt";
//    private final FileWriter fw = new FileWriter(fileName, true);
//    private final BufferedWriter bf = new BufferedWriter(fw);
//    private final FileReader fr = new FileReader(fileName);
//    private final BufferedReader br = new BufferedReader(fr);
//    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
//
//    public FileVoucherRepository() throws IOException {
//    }
//
//    @Override
//    public void save(Voucher voucher) {
//
//        logger.info("[FileVoucherRepository] save(Voucher voucher) called");
//
//        try {
//            String stringToWrite = voucher.getVoucherId() + ", " +
//                    voucher.getDiscount() + ", " + voucher.getVoucherType();
//            bf.write(stringToWrite);
//            bf.newLine();
//            bf.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        logger.info("Voucher saved");
//    }
//
//    @Override
//    public List<Voucher> findAll() throws IOException {
//
//        logger.info("[FileVoucherRepository] findAll() called");
//        List<Voucher> vouchers = new ArrayList<>();
//        String readLine = null;
//        while ((readLine = br.readLine()) != null) {
//            String[] readLineSplit = readLine.split(",");
//            if (readLineSplit[2].equalsIgnoreCase(String.valueOf(VoucherType.FixedAmountVoucher))) {
//                vouchers.add(new FixedAmountVoucher(UUID.fromString(readLineSplit[0]), Long.parseLong(readLineSplit[1].trim())));
//            } else {
//                vouchers.add(new PercentDiscountVoucher(UUID.fromString(readLineSplit[0]), Long.parseLong(readLineSplit[1].trim())));
//            }
//        }
//        return vouchers;
////    }
//
//
//}
