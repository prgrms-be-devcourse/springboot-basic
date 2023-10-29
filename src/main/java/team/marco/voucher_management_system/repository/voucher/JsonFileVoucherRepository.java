package team.marco.voucher_management_system.repository.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.model.voucher.Voucher;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class JsonFileVoucherRepository implements VoucherRepository, DisposableBean {
    private final Map<UUID, Voucher> voucherMap;
    private final ObjectMapper objectMapper;
    private final File file;

    public JsonFileVoucherRepository(@Value("${file.path.voucher_data}") String path) {
        objectMapper = new ObjectMapper();
        file = new File(path);

        voucherMap = loadVoucherMap();
    }

    private Map<UUID, Voucher> loadVoucherMap() {
        Map<UUID, Voucher> loadedVouchers = new ConcurrentHashMap<>();
        if (!file.exists()) {
            return loadedVouchers;
        }

        ObjectReader objectReader = objectMapper.readerForListOf(LoadedJsonVoucher.class);
        List<LoadedJsonVoucher> jsonVouchers;

        try {
            jsonVouchers = objectReader.readValue(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        jsonVouchers.forEach(data -> loadedVouchers.put(data.getId(), data.jsonVoucherToVoucher()));

        return loadedVouchers;
    }

    @Override
    public Voucher save(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMap.values().stream()
                .toList();
    }

    @Override
    public List<Voucher> findByOwner(UUID ownerId) {
        return voucherMap.values().stream()
                .filter(v -> v.getOwnerId() == ownerId)
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        if(voucherMap.containsKey(voucherId)) {
            return Optional.of(voucherMap.get(voucherId));
        } else return Optional.empty();
    }

    @Override
    public Voucher update(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);

        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        voucherMap.remove(voucherId);
    }

    @Override
    public void destroy() {
        try {
            objectMapper.writeValue(file, voucherMap.values());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
