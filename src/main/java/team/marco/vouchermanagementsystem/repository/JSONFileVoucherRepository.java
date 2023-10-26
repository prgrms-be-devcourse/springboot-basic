package team.marco.vouchermanagementsystem.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.LoadedVoucher;
import team.marco.vouchermanagementsystem.model.Voucher;
import team.marco.vouchermanagementsystem.model.VoucherType;
import team.marco.vouchermanagementsystem.properties.FilePathProperties;

@Profile({"local", "dev"})
@Repository
public class JSONFileVoucherRepository implements VoucherRepository, DisposableBean {
    private final Map<UUID, Voucher> voucherMap;
    private final ObjectMapper objectMapper;
    private final File file;

    public JSONFileVoucherRepository(FilePathProperties filePathProperties) {
        objectMapper = new ObjectMapper();
        file = new File(filePathProperties.voucherData());

        voucherMap = load();
    }

    private Map<UUID, Voucher> load() {
        if (!file.exists()) {
            return new HashMap<>();
        }

        ObjectReader objectReader = objectMapper.readerForListOf(LoadedVoucher.class);
        List<LoadedVoucher> loadedVouchers;

        try {
            loadedVouchers = objectReader.readValue(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        Map<UUID, Voucher> convertedMap = new HashMap<>();

        loadedVouchers.forEach(data -> convertedMap.put(data.getId(), VoucherType.convertVoucher(data)));

        return convertedMap;
    }

    @Override
    public void save(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMap.values()
                .stream()
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
