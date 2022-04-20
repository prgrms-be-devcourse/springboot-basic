package org.programmers.springbootbasic.voucher.repository;

import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherDTO;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    @Value("${path.voucher}")
    private String file;

    private VoucherType voucherType;


    public File createFile(File file) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        ) {
        } catch (IOException e) {
            logger.error("IOException 에러입니다. {0}", e);
        }
        return file;
    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String line;
        try (
                FileReader fileReader = new FileReader(createFile(new File(file)));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            while ((line = bufferedReader.readLine())!=null) {
                String[] voucherInfo = line.split(" ");
                if (voucherInfo[2].equals(voucherId.toString())) {
                    voucherType = VoucherType.findByType(voucherInfo[0]);
                    return Optional.of(voucherType.create(new VoucherDTO(UUID.fromString(voucherInfo[2]), Long.parseLong(voucherInfo[4]), LocalDateTime.parse(voucherInfo[6]))));
                }
            }
        } catch (IOException e) {
            logger.error("IOException 에러입니다. {0}", e);
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
                FileReader fileReader = new FileReader(createFile(new File(file)));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            while ((line = bufferedReader.readLine())!=null) {
                String[] voucherInfo = line.split(" ");
                voucherType = VoucherType.findByType(voucherInfo[0]);
                voucherList.add(voucherType.create(new VoucherDTO(UUID.fromString(voucherInfo[2]), Long.parseLong(voucherInfo[4]), LocalDateTime.parse(voucherInfo[6]))));
            }
        } catch (FileNotFoundException e) {
            logger.warn("FileNotFoundException 입니다.", e);
        }
        catch (IOException e) {
            logger.error("모두 찾기 IOException 에러입니다. {0}", e);
        }
        return voucherList;
    }
}


