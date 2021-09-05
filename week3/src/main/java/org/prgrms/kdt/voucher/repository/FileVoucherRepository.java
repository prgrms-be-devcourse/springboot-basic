package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.util.VoucherFileReader;
import org.prgrms.kdt.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> memory = new ConcurrentHashMap<>();
    private static final String PATH = "src/main/resources/static/";
    private static final String FILENAME = "voucher.csv";

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(memory.getOrDefault(voucherId, null));
    }

    @Override
    public List<Voucher> findAll() {
//        return List.of(memory.values().toArray(new Voucher[]{}));
        return Arrays.asList(memory.values().toArray(new Voucher[]{}));
    }

    @Override
    public int insert(Voucher voucher) {
        memory.put(voucher.getVoucherId(), voucher);
        return memory.size();
    }

    @PostConstruct
    void fileToMem() {
        VoucherFileReader voucherFileReader = new VoucherFileReader();
        try {
            memory.putAll(voucherFileReader.readFile(PATH + FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void MemToFile() throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(PATH + FILENAME, true));

        StringBuilder sb = new StringBuilder();
        for (Voucher voucher : findAll()) {
            sb.append(voucher.getVoucherId()).append(",")
                    .append(voucher.getClass().getSimpleName()).append(",")
                    .append(voucher.getAmount()).append("\n");
        }

        br.write(sb.toString());
        br.flush();
        br.close();
    }

}
