package com.devcourse.springbootbasic.application.domain.voucher.repository;

import com.devcourse.springbootbasic.application.global.constant.YamlProperties;
import com.devcourse.springbootbasic.application.domain.voucher.data.Voucher;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.global.constant.Message;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Profile({"default"})
public class FileVoucherRepository implements VoucherRepository {

    private final ConfigurableApplicationContext applicationContext;
    private final YamlProperties yamlProperties;

    FileVoucherRepository(ConfigurableApplicationContext applicationContext, YamlProperties yamlProperties) {
        this.applicationContext = applicationContext;
        this.yamlProperties = yamlProperties;
    }

    private File getFile() {
        try {
            return applicationContext.getResource(
                    String.format("file:%s", yamlProperties.getVoucherRecordPath())
            ).getFile();
        } catch (IOException e) {
            throw new InvalidDataException(Message.INVALID_FILE_ACCESS, e.getCause());
        }
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(getFile(), true)
        ) {
            String voucherInfo = "%s\n".formatted(voucher.toString());
            fileOutputStream.write(voucherInfo.getBytes());
            return Optional.of(voucher);
        } catch (IOException e) {
            throw new InvalidDataException(Message.INVALID_FILE_ACCESS, e.getCause());
        }
    }

    @Override
    public List<String> findAll() {
        List<String> voucherRecord = new ArrayList<>();
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()))
        ) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                voucherRecord.add(record);
            }
        } catch (IOException e) {
            throw new InvalidDataException(Message.INVALID_FILE_ACCESS, e.getCause());
        }
        return voucherRecord;
    }
}
