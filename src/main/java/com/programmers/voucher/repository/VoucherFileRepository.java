package com.programmers.voucher.repository;

import com.programmers.voucher.model.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;

import java.io.IOException;
import java.util.List;

@Repository
@Primary
public class VoucherFileRepository implements VoucherRepository {

    private final String FILE_NAME = "src/main/resources/file/voucher.txt";
    private FileWriter writer;

    public VoucherFileRepository() {
        try {
            writer = new FileWriter(FILE_NAME, true);
        } catch (IOException e) {
            throw new RuntimeException("파일 생성에 실패하였습니다.");
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            writer.write(voucher.toString()+"\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("파일에 바우처 저장하기 실패하였습니다.");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
