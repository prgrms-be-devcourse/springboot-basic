package com.devcourse.springbootbasic.application.repository.voucher;

import com.devcourse.springbootbasic.application.constant.Message;
import com.devcourse.springbootbasic.application.constant.YamlProperties;
import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile({"default"})
public class FileVoucherRepository implements VoucherRepository {

    private final String filepath;

    public FileVoucherRepository(YamlProperties yamlProperties) {
        this.filepath = yamlProperties.getVoucherRecordPath();
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath, true))
        ) {
            String voucherInfo = "%s\n".formatted(voucher.toString());
            bufferedWriter.write(voucherInfo);
            return Optional.of(voucher);
        } catch (IOException e) {
            throw new InvalidDataException(Message.INVALID_FILE_ACCESS.getMessageText(), e.getCause());
        }
    }

    @Override
    public List<String> findAll() {
        List<String> vouchers = new ArrayList<>();
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))
        ) {
            String voucherInfo;
            while ((voucherInfo = bufferedReader.readLine()) != null) {
                vouchers.add(voucherInfo);
            }
        } catch (IOException e) {
            throw new InvalidDataException(Message.INVALID_FILE_ACCESS.getMessageText(), e.getCause());
        }
        return vouchers;
    }
}
