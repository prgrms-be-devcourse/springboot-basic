package W3D2.jcu.voucher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    Map<UUID, Voucher> findAll();
    void readStorage() throws IOException;
    void writeStorage() throws FileNotFoundException;

    // Quest : 프로토타입 추가
    //  - 구현체에 새로운 메소드가 추가될때마다 인터페이스에 프로토타입을 추가시켜야 할까?
}
