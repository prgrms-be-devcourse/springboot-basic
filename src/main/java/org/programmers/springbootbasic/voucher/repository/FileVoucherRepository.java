package org.programmers.springbootbasic.voucher.repository;

import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherType;
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
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    private final File file = new File("voucher_file_database.csv");
    VoucherType voucherType;

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String line;
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] voucherInfo = line.split(" ");
                if (voucherInfo[2].equals(voucherId.toString())) {
                    voucherType = VoucherType.findByType(voucherInfo[0]);
                    return Optional.of(voucherType.createWithUUID(UUID.fromString(voucherInfo[2]), Long.parseLong(voucherInfo[4])));
                }
            }
        } catch (IOException e) {
            logger.error("아이디로 찾기 IOException 에러입니다. {0}", e);
        }
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try (
                FileWriter fileWriter = new FileWriter(file, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            bufferedWriter.write(voucher.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error("입력 IOException 에러입니다. {0}", e);
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();

        String line;
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] voucherInfo = line.split(" ");
                voucherType = VoucherType.findByType(voucherInfo[0]);
                voucherList.add(voucherType.createWithUUID(UUID.fromString(voucherInfo[2]), Long.parseLong(voucherInfo[4])));
            }
        } catch (IOException e) {
            logger.error("모두 찾기 IOException 에러입니다. {0}r", e);
        }
        return voucherList;
    }
}


