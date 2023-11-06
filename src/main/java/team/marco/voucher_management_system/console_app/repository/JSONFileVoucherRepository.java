package team.marco.voucher_management_system.console_app.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.console_app.properties.FilePathProperties;
import team.marco.voucher_management_system.model.LoadedVoucher;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.repository.VoucherRepository;
import team.marco.voucher_management_system.type_enum.VoucherType;

@Profile("dev")
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
    public int deleteById(UUID id) {
        if (!voucherMap.containsKey(id)) {
            return 0;
        }

        voucherMap.remove(id);

        return 1;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMap.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        if (!voucherMap.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(voucherMap.get(id));
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return voucherMap.values()
                .stream()
                .filter(voucher -> voucher.isSameType(voucherType))
                .toList();
    }

    @Override
    public List<Voucher> findByCreateAt(LocalDateTime from, LocalDateTime to) {
        return findAll().stream()
                .filter(voucher -> voucher.isCreatedBetween(from, to))
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
