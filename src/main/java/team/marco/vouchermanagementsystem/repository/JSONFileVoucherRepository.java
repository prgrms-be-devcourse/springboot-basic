package team.marco.vouchermanagementsystem.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.Voucher;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Primary
public class JSONFileVoucherRepository implements VoucherRepository, DisposableBean {
    private String path = "./src/main/resources/data.json";
    private final Map<UUID, Voucher> voucherMap;
    private final ObjectMapper objectMapper;
    private final File file;

    public JSONFileVoucherRepository() {
        objectMapper = new ObjectMapper();
        file = new File(path);
        voucherMap = load();
    }

    private Map<UUID, Voucher> load() {
        ObjectReader objectReader = objectMapper.readerForListOf(LoadedJSONVoucher.class);
        List<LoadedJSONVoucher> loadedList;

        try {
            loadedList = objectReader.readValue(file);
        } catch (IOException e) {
            throw new RuntimeException("파일을 불러오는 과정에서 오류가 발생했습니다.");
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
            throw new RuntimeException(e);
        }
    }
}
