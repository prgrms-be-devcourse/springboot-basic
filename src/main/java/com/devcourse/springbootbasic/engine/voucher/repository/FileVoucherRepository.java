package com.devcourse.springbootbasic.engine.voucher.repository;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Profile({"default"})
public class FileVoucherRepository implements VoucherRepository {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private File getFile() {
        try {
            return applicationContext.getResource("file:src/main/resources/voucher_record.txt")
                    .getFile();
        } catch (IOException e) {
            throw new InvalidDataException(InvalidDataException.INVALID_FILE_ACCESS);
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getFile(), true);
            String voucherInfo = "%s\n".formatted(voucher.toString());
            fileOutputStream.write(voucherInfo.getBytes());
        } catch (IOException e) {
            throw new InvalidDataException(InvalidDataException.INVALID_FILE_ACCESS);
        }
        return voucher;
    }

    @Override
    public List<String> findAll(){
        List<String> voucherRecord = new ArrayList<>();
        try {
            File file = getFile();
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file)
            );
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                voucherRecord.add(record);
            }
        } catch (IOException e) {
            throw new InvalidDataException(InvalidDataException.INVALID_FILE_ACCESS);
        }
        return voucherRecord;
    }

    @Override
    public void setVoucherMap(Map<UUID, Voucher> map) {}

    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
