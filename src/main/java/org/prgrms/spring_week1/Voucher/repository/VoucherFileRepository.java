package org.prgrms.spring_week1.Voucher.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.spring_week1.Voucher.model.Voucher;
import org.prgrms.spring_week1.Voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;


public class VoucherFileRepository implements VoucherRepository {

    @Value("${file.voucher.path}")
    private String path;

    private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);

    @Override
    public Voucher insert(Voucher voucher) {
        StringBuilder sb = new StringBuilder();

        // try with resources로 해당 구문 벗어나면 AutoCloseable은 close 된다.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            // UUID,voucher.toString() 형식으로 작성
            sb.append(voucher.getVoucherId());
            sb.append(",");
            sb.append(voucher.toString());
            bw.write(sb.toString());
            bw.newLine(); // 개행

        } catch (FileNotFoundException e) {
            logger.error("Got error while opening file ", e);
        } catch (IOException e) {
            logger.error("Got error while writing in file ", e);
        }

        return voucher;
    }

    @Override
    public List<Voucher> getAllVoucher() {
        String line = " ";
        List<Voucher> vouchers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] strings = line.split(",");
                // vouchers.add(strings[1]); // voucher.toString()만 slicing

            }

        } catch (FileNotFoundException e) {
            logger.error("Got error while opening file ", e);
        } catch (IOException e) {
            logger.error("Got error while reading file ", e);
        }

        return null;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return null;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public List<Voucher> findByCustomer(UUID customerId) {
        return null;
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return null;
    }
}
