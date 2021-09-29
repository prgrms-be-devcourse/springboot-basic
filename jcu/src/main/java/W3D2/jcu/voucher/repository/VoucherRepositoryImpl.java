package W3D2.jcu.voucher.repository;

import W3D2.jcu.Utils;
import W3D2.jcu.voucher.model.FixedAmountVoucher;
import W3D2.jcu.voucher.model.PercentDiscountVoucher;
import W3D2.jcu.voucher.model.Voucher;
import W3D2.jcu.voucher.model.VoucherStatus;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class VoucherRepositoryImpl implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public void readStorage() { // csv 읽어오기
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/storage.csv"));
            StringTokenizer st;
            String input;
            while((input = br.readLine())!=null) {
                st = new StringTokenizer(input);

                String type = st.nextToken();
                UUID id = UUID.fromString(st.nextToken());
                Long amount = Long.parseLong(st.nextToken());
                VoucherStatus code = VoucherStatus.from(type);
                this.insert(code.execute(id, amount));
            }
            br.close();
        } catch (FileNotFoundException e) {} catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void writeStorage() throws FileNotFoundException { // csv 저장하기
        PrintWriter pw = new PrintWriter("src/main/resources/storage.csv");

        for (Voucher output : storage.values()) {
            StringBuilder sb = new StringBuilder();
            sb.append(output.getInfo());
            pw.println(sb);
        }
        pw.close();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return storage;
    }
}
