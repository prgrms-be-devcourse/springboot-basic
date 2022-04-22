package org.programmers.kdt.weekly.voucher.repository;

//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import org.programmers.kdt.weekly.voucher.model.Voucher;
//import org.programmers.kdt.weekly.voucher.VoucherDto;
//import org.programmers.kdt.weekly.voucher.model.VoucherType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Repository;
//
//
////@Profile("dev1")
//@Repository
//public class VoucherFileRepository implements VoucherRepository {
//
//    private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);
//    private static final String EMPTY = "";
//    private static final File file = new File("voucher_database.csv");
//
//    @Override
//    public Voucher insert(Voucher voucher) {
//        try {
//            FileWriter fileWriter = new FileWriter(file, true);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            String line = voucher.serializeVoucher();
//            bufferedWriter.write(line);
//            bufferedWriter.flush();
//        } catch (IOException e) {
//            logger.error("voucher insert() IOException  error ", e);
//        }
//
//        return voucher;
//    }
//
//    @Override
//    public List<Voucher> findAll() {
//        List<Voucher> vouchers = new ArrayList<>();
//        String line = EMPTY;
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//            while ((line = bufferedReader.readLine()) != null) {
//                var splitByComma = line.split(",");
//                Optional<Voucher> voucher = VoucherType.valueOf(splitByComma[0])
//                    .create(new VoucherDto(UUID.fromString(splitByComma[1]),
//                        Integer.parseInt(splitByComma[2]),
//                        Timestamp.valueOf(splitByComma[3]).toLocalDateTime()));
//                vouchers.add(voucher.get());
//            }
//
//        } catch (IOException | NullPointerException e) {
//            logger.error("voucher findAll() IOException  error ", e);
//        }
//
//        return List.copyOf(vouchers);
//    }
//
//    @Override
//    public Optional<Voucher> findById(UUID voucherId) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Voucher update(Voucher voucher) {
//        return null;
//    }
//
//    @Override
//    public void deleteById(UUID voucherId) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//}