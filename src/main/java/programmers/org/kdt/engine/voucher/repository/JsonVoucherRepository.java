package programmers.org.kdt.engine.voucher.repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import programmers.org.kdt.engine.voucher.type.FixedAmountVoucher;
import programmers.org.kdt.engine.voucher.type.PercentDiscountVoucher;
import programmers.org.kdt.engine.voucher.type.Voucher;
import programmers.org.kdt.engine.voucher.type.VoucherStatus;

@Repository
//@Qualifier("json")
@Profile("default")
public class JsonVoucherRepository implements VoucherRepository, InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(JsonVoucherRepository.class);
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
        return putDataToJson(voucher);
    }

    @Override
    public Set<Entry<UUID, Voucher>> getAllEntry() {
        updateStorage();
        return storage.entrySet();
    }

    @Override
    public List<Voucher> findAll() {
        updateStorage();
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteAll() {
        deleteJsonFile();
        createJsonFile();
        storage.clear();
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
        logger.debug("Store voucher to Json file successfully"+ voucher.toString());
        return Optional.of(voucher);
    }

    private Optional<JSONArray> getDataFromJson() {
        JSONParser parser = new JSONParser();
        Optional<JSONArray> jsonArray = Optional.empty();
        try {
            // if file empty
            if(new FileReader(jsonFileName).read() == -1) {
                logger.info("Json file is empty");
                return jsonArray;
            }

            FileReader fileReader = new FileReader(jsonFileName);
            jsonArray = Optional.of((JSONArray)parser.parse(fileReader));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        logger.debug("Get Json data successfully");
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

            if (voucherStatus == VoucherStatus.FIXEDAMOUNTVOUCHER) {
                voucher = new FixedAmountVoucher(id, value);
            } else if (voucherStatus == VoucherStatus.PERCENTDISCOUNTVOUCHER) {
                voucher = new PercentDiscountVoucher(id, value);
            }

            if (voucher != null) {
                storage.put(voucher.getVoucherId(), voucher);
            } else {
                logger.error("Put json voucher to storage");
            }

        }
    }

    private void createJsonFile() {
        File file = new File(jsonFileName);

        //create json if not exist
        if(!file.exists()) {
            boolean ret = false;
            try {
                ret = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!ret) System.exit(-1);
            logger.info("Json file create");
            return;
        }
        logger.info("Json file already exist");
    }

    private void deleteJsonFile() {
        File file = new File(jsonFileName);
        if (file.exists())
            file.delete();
        logger.info("Delete Json file success");
    }

    @Override
    public void afterPropertiesSet() {
        createJsonFile();
    }

}

