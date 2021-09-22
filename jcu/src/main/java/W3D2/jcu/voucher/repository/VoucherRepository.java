package W3D2.jcu.voucher.repository;

import W3D2.jcu.voucher.model.Voucher;
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

    // default 메소드 이용
}
