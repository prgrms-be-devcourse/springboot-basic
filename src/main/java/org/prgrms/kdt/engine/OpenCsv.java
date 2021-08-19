package org.prgrms.kdt.engine;

import com.opencsv.CSVWriter;
import org.prgrms.kdt.voucher.Voucher;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class OpenCsv {

    public void saveToFile(Map<UUID, Voucher> data, String filePath) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(filePath));
        String[] header = "UUID,Voucher".split(",");
        writer.writeNext(header);
        for (var entry: data.entrySet()) {
            writer.writeNext(new String[]{entry.getKey().toString(), String.valueOf(entry.getValue())});
        }
        System.out.println(data);
        writer.close();
    }
}
