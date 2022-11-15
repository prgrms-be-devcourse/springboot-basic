package com.program.commandLine.repository;

import com.program.commandLine.CommandLineProgramApplication;
import com.program.commandLine.voucher.Voucher;
import com.program.commandLine.service.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component(value = "voucherRepository")
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private final Logger logger = LoggerFactory.getLogger(CommandLineProgramApplication.class);

    private File voucherFile;

    private final VoucherFactory voucherFactory;

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Value("${voucher.file_repository.path}")
    private String voucherFilePath;

    public FileVoucherRepository(VoucherFactory voucherFactory) {
        this.voucherFactory = voucherFactory;
    }

    @PostConstruct
    private void readVoucherFile(){
        voucherFile = new File(voucherFilePath);
        if (voucherFile.exists()) {
            try {
                fileRead();
            } catch (Exception e) {
                logger.warn("바우처 리소스 파일 열기에 실패" + e.getMessage());
                throw new RuntimeException("! Failed to open voucher file");
            }
        }
    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insertVoucher(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    private void fileRead() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(voucherFile));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] readVoucher = line.split(" ");
            if (readVoucher.length < 3) continue;
            UUID voucherId = UUID.fromString(readVoucher[0]);
            String voucherType = readVoucher[1];
            int voucherDiscount = Integer.parseInt(readVoucher[2]);
            Voucher voucher = voucherFactory.createVoucher(voucherType,voucherId, voucherDiscount);
            storage.put(voucherId, voucher);
        }
        reader.close();
        logger.info("read 버퍼 정상 종료");
    }

    @PreDestroy
    private void fileWrite() throws IOException {
        if (voucherFile.exists()) voucherFile.delete();
        voucherFile.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(voucherFile, true));
        for (UUID key : storage.keySet()) {
            String writeVoucher = key + " "
                    + storage.get(key).getVoucherType() + " "
                    + storage.get(key).getVoucherDiscount();
            writer.write(writeVoucher);
            writer.newLine();
        }
        writer.flush();
        writer.close();
        logger.info("write 버퍼 정상 종료");
    }
}
