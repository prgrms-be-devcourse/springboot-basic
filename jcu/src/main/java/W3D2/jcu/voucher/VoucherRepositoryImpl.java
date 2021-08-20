package W3D2.jcu.voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VoucherRepositoryImpl implements VoucherRepository{

    // 3일차 강의내용을 그대로 사용해서 HashMap을 사용했습니다.
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
    
    // 추가된 메소드, 인터페이스에도 프로토타입 추가
    @Override
    public Map<UUID, Voucher> findAll(){
        return storage;
    }
}
