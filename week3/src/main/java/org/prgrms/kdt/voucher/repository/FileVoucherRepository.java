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
//      return new ArrayList<>(memory.values());
//      이부분을 List.of(memory.values())로 처리하니 에러가났습니다..;;
//      List.of(Element e) 로 처리해야하는데 어떻게 변경을 해야할까요...?
        // 위 List.of()안에 인자로 원소를하나하나 넣어야하는데 어떻게 처리를 해야할지 모르겠어요..

        // java8 불변객체로 처리하는 방법은 아래방법이 있다고 해서 이러헥 대체해봅니다..!
       return Collections.unmodifiableList(memory.values().stream().toList());
    }

    @Override
    public void insert(Voucher voucher) {
        memory.put(voucher.getVoucherId(), voucher);
    }

    @PostConstruct
    void fileToMem() {
        VoucherFileReader voucherFileReader = new VoucherFileReader();
        memory.putAll(voucherFileReader.readFile(PATH + FILENAME));
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
