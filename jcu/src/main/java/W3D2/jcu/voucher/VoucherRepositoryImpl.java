package W3D2.jcu.voucher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class VoucherRepositoryImpl implements VoucherRepository{
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public void readStorage() throws IOException {
        try{
            BufferedReader br = new BufferedReader(new FileReader("D:/storage.txt"));
            StringTokenizer st;
            String input;
            while((input = br.readLine())!=null) {
                st = new StringTokenizer(input);
                // 질문 필드멤버가 추가되면....?
                // 어떻게 설계하는것이 좋을까
                String type = st.nextToken();
                UUID id = UUID.fromString(st.nextToken());
                Long value = Long.valueOf(st.nextToken());
                // enum 업데이트 전
                if(type.equals("FIXED")) insert(new FixedAmountVoucher(id, value));
                else insert(new PercentDiscountVoucher(id, value));
            }
            br.close();
        } catch (FileNotFoundException e) {}

    }

    @Override
    public void writeStorage() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("D:/storage.txt");

        for (Voucher output : storage.values()) {
            StringBuilder sb = new StringBuilder();
            // ID값까지 유지되어 저장되도록 했습니다.
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
    public Map<UUID, Voucher> findAll(){
        return storage;
    }
}
