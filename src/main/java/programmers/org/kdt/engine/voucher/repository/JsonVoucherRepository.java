package programmers.org.kdt.engine.voucher.repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import programmers.org.kdt.engine.voucher.FixedAmountVoucher;
import programmers.org.kdt.engine.voucher.PercentDiscountVoucher;
import programmers.org.kdt.engine.voucher.Voucher;
import programmers.org.kdt.engine.voucher.VoucherStatus;

@Repository
public class JsonVoucherRepository implements VoucherRepository, InitializingBean {
    private Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    private final String jsonFileName = "Voucher.json";

    private final String voucherIdKey = "id";
    private final String voucherStatusKey = "status";
    private final String voucherValueKey = "value";

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        if(storage.containsKey(voucherId)) return Optional.ofNullable(storage.get(voucherId));
        // if storage have not voucherId
        updateStorage();
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(),voucher);
        System.out.println(voucher);
        return putDataToJson(voucher);
    }

    @Override
    public Set<Entry<UUID, Voucher>> getAllEntry() {
        updateStorage();
        return storage.entrySet();
    }

    private Optional<Voucher> putDataToJson(Voucher voucher) {
        JSONArray jsonArray = this.getDataFromJson().orElse(null);
        JSONObject additionalObject = new JSONObject();

        additionalObject.put(voucherIdKey, voucher.getVoucherId().toString());
        additionalObject.put(voucherStatusKey, voucher.getVoucherStatus().toString());
        additionalObject.put(voucherValueKey, voucher.getValue());
        if(jsonArray == null) jsonArray = new JSONArray();

        jsonArray.add(additionalObject);

        // Write to json file
        try {
            FileWriter fileWriter = new FileWriter(jsonFileName);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(voucher);
    }

    private Optional<JSONArray> getDataFromJson() {
        JSONParser parser = new JSONParser();
        Optional<JSONArray> jsonArray = Optional.empty();
        try {
            // if file empty
            if(new FileReader(jsonFileName).read() == -1) return jsonArray;

            FileReader fileReader = new FileReader(jsonFileName);
            System.out.println(fileReader);
            JSONArray jsonArray1 = (JSONArray)parser.parse(fileReader);
            System.out.println(jsonArray1);
            jsonArray = Optional.of(jsonArray1);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private void updateStorage() {
        JSONArray jsonArray = this.getDataFromJson().orElse(null);
        storage.clear();
        if(jsonArray == null) {
            return;
        }
        for (Object o : jsonArray) {
            Voucher voucher = null;

            JSONObject obj = (JSONObject) o;
            UUID id = UUID.fromString(String.valueOf(obj.get(voucherIdKey)));
            long value = (long) obj.get(voucherValueKey);
            VoucherStatus voucherStatus = VoucherStatus.fromString(
                String.valueOf(obj.get(voucherStatusKey))
            );

            if (voucherStatus == VoucherStatus.FixedAmountVoucher) {
                voucher = new FixedAmountVoucher(id, value);
            } else if (voucherStatus == VoucherStatus.PercentDiscountVoucher) {
                voucher = new PercentDiscountVoucher(id, value);
            }

            if (voucher != null) {
                storage.put(voucher.getVoucherId(), voucher);
            } else {
                System.out.println("ERROR : put json voucher to storage");
            }

        }
    }

    @Override
    public void afterPropertiesSet() {
        File file = new File(jsonFileName);

        //create json if not exist
        try {
            if(file.createNewFile()) {
                System.out.println("Json file create");
            } else {
                System.out.println("Json file already exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

