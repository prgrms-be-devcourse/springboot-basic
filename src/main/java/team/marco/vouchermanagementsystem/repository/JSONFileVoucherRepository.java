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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Profile("dev")
@Repository
public class JSONFileVoucherRepository implements VoucherRepository, DisposableBean {
    private final Map<UUID, Voucher> voucherMap;
    private final ObjectMapper objectMapper;
    private final File file;

    public JSONFileVoucherRepository(@Value("${file.path.voucher_data}") String path) {
        objectMapper = new ObjectMapper();
        file = new File(path);

        voucherMap = load();
    }

    private Map<UUID, Voucher> load() {
        if (!file.exists()) {
            return new HashMap<>();
        }

        ObjectReader objectReader = objectMapper.readerForListOf(LoadedJSONVoucher.class);
        List<LoadedJSONVoucher> loadedList;

        try {
            loadedList = objectReader.readValue(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        Map<UUID, Voucher> convertedMap = new HashMap<>();

        loadedList.forEach(data -> convertedMap.put(data.getId(), data.convertToVoucher()));

        return convertedMap;
    }

    @Override
    public void save(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMap.values().stream().toList();
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
