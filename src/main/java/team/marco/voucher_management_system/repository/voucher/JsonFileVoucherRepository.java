package team.marco.voucher_management_system.repository.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.domain.voucher.VoucherType;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JsonFileVoucherRepository implements VoucherRepository, DisposableBean {
    private final Map<Long, Voucher> voucherMap;
    private final ObjectMapper objectMapper;
    private final File file;

    public JsonFileVoucherRepository(@Value("${file.path.voucher_data}") String path) {
        objectMapper = new ObjectMapper();
        file = new File(path);

        voucherMap = loadVoucherMap();
    }

    private Map<Long, Voucher> loadVoucherMap() {
        Map<Long, Voucher> loadedVouchers = new ConcurrentHashMap<>();
        if (!file.exists()) {
            return loadedVouchers;
        }

        ObjectReader objectReader = objectMapper.readerForListOf(Voucher.class);
        List<Voucher> vouchers;

        try {
            vouchers = objectReader.readValue(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        vouchers.forEach(voucher -> loadedVouchers.put(voucher.getId(), voucher));

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
    public List<Voucher> findAllByVoucherType(VoucherType type) {
        return voucherMap.values().stream()
                .filter(v -> isSameType(type, v))
                .toList();
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) {
        if(voucherMap.containsKey(voucherId)) {
            return Optional.of(voucherMap.get(voucherId));
        } else return Optional.empty();
    }

    @Override
    public void deleteById(Long voucherId) {
        voucherMap.remove(voucherId);
    }

    @Override
    public Optional<Long> findLatestVoucherId() {
        return voucherMap.keySet().stream()
                .max(Comparator.naturalOrder());
    }

    @Override
    public void destroy() {
        try {
            List<Voucher> vouchers = voucherMap.values().stream().toList();
            objectMapper.writeValue(file, vouchers);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static boolean isSameType(VoucherType type, Voucher v) {
        return v.getVoucherType() == type;
    }
}
