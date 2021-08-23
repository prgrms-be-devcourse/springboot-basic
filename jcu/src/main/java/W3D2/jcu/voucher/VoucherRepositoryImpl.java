package W3D2.jcu.voucher;

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

                // Quest : Class에 새로운 멤버 추가
                //  - Voucher에 새로운 멤버가 추가된다면...? (type, id, amount, ...)
                //  - 어떻게 설계하는 것이 좋을까?

                String type = st.nextToken();
                UUID id = UUID.fromString(st.nextToken());
                Long amount = Long.parseLong(st.nextToken());
                // enum 업데이트 전
                if(type.equals("FIXED")) insert(new FixedAmountVoucher(id, amount));
                else insert(new PercentDiscountVoucher(id, amount));
            }
            br.close();
        } catch (FileNotFoundException e) {}

    }

    @Override
    public void writeStorage() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("D:/storage.txt");

        for (Voucher output : storage.values()) {
            StringBuilder sb = new StringBuilder();
            // ID값까지 유지되어 저장되도록 함.
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
