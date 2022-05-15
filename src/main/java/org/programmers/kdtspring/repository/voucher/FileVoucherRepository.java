package org.programmers.kdtspring.repository.voucher;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.PercentDiscountVoucher;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.programmers.kdtspring.exception.NotAvailableMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    private final String fileName = "voucherList.txt";
    private final FileWriter fw = new FileWriter(fileName, true);
    private final BufferedWriter bf = new BufferedWriter(fw);
    private final FileReader fr = new FileReader(fileName);
    private final BufferedReader br = new BufferedReader(fr);

    public FileVoucherRepository() throws IOException {
    }

    @Override
    public void insert(Voucher voucher) {

        logger.info("[FileVoucherRepository] save(Voucher voucher) called");

        try {
            String stringToWrite = voucher.getVoucherId() + "," +
                    voucher.getDiscount() + "," + voucher.getVoucherType();
            bf.write(stringToWrite);
            bf.newLine();
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Voucher saved");
    }

    @Override
    public List<Voucher> findAll() {

        logger.info("[FileVoucherRepository] findAll() called");
        List<Voucher> vouchers = new ArrayList<>();
        String readLine;
        try {
            while ((readLine = br.readLine()) != null) {
                String[] readLineSplit = readLine.split(",");
                if (readLineSplit[2].equalsIgnoreCase(String.valueOf(VoucherType.FixedAmountVoucher))) {
                    vouchers.add(new FixedAmountVoucher(UUID.fromString(readLineSplit[0]), Integer.parseInt(readLineSplit[1]), String.valueOf(readLineSplit[2])));
                } else {
                    vouchers.add(new PercentDiscountVoucher(UUID.fromString(readLineSplit[0]), Integer.parseInt(readLineSplit[1]), String.valueOf(readLineSplit[2])));
                }
            }
        } catch (IOException e) {
            logger.error("{} error occurred", IOException.class);
        }
        return vouchers;
    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        throw new NotAvailableMethod("이 메서드는 사용할 수 없습니다.");
    }

    @Override
    public List<Voucher> findByCustomer(Customer customer) {
        throw new NotAvailableMethod("이 메서드는 사용할 수 없습니다.");
    }

    @Override
    public List<Voucher> findByType(String voucherType) {
        throw new NotAvailableMethod("이 메서드는 사용할 수 없습니다.");
    }

    @Override
    public void updateCustomerId(Voucher voucher) {
        throw new NotAvailableMethod("이 메서드는 사용할 수 없습니다.");
    }

    @Override
    public void deleteOne(Voucher voucher) {
        throw new NotAvailableMethod("이 메서드는 사용할 수 없습니다.");
    }

    @Override
    public void deleteAll() {
        throw new NotAvailableMethod("이 메서드는 사용할 수 없습니다.");
    }
}