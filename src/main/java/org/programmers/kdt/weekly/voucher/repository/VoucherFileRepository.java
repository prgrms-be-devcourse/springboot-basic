package org.programmers.kdt.weekly.voucher.repository;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;


@Profile("local")
@Repository
public class VoucherFileRepository implements VoucherRepository {

    private final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);
    private final Resource FILE_PATH = new ClassPathResource("voucherFileDB.csv");

    @Override
    public void insert(Voucher voucher) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH.getFilename());
            BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(fileOutputStream));
            String line =
                voucher.getVoucherType() + "," + voucher.getVoucherId() + "," + voucher.getValue()
                    + "\n";
            bufferedWriter.write(line);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int count() {
        int size = 0;
        try {
            InputStream inputStream = new BufferedInputStream(
                new FileInputStream(FILE_PATH.toString()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (bufferedReader.readLine() != null) {
                size++;
            }
        } catch (IOException e) {
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
        return size;
    }


    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        String line;
        try {
            InputStream inputStream = FILE_PATH.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineSplitComma = line.split(",");
                VoucherType.findByType(lineSplitComma[0])
                    .create(UUID.fromString(lineSplitComma[1]),
                        Integer.parseInt(lineSplitComma[2]));
            }

        } catch (IOException | NullPointerException e) {
            logger.error("File database error -> {}", e.getMessage());
        }
        return voucherList;
    }
}
