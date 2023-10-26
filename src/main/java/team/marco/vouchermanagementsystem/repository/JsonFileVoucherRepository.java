package team.marco.vouchermanagementsystem.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.voucher.Voucher;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile({"prod", "dev"})
@Repository
public class JsonFileVoucherRepository implements VoucherRepository, DisposableBean {
    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    private final File file;

    public JsonFileVoucherRepository(@Value("${file.path.voucher_data}") String path) {
        objectMapper = new ObjectMapper();
        file = new File(path);

        loadVoucherMap();
    }

    private void loadVoucherMap() {
        if (!file.exists()) {
            return;
        }

        ObjectReader objectReader = objectMapper.readerForListOf(LoadedJsonVoucher.class);
        List<LoadedJsonVoucher> jsonVouchers;

        try {
            jsonVouchers = objectReader.readValue(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        jsonVouchers.forEach(data -> voucherMap.put(data.getId(), data.convertToVoucher()));
    }

    @Override
    public void save(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMap.values().stream()
                .toList();
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
