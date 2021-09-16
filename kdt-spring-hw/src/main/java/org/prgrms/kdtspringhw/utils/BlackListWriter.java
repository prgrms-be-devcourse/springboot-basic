package org.prgrms.kdtspringhw.utils;

import org.prgrms.kdtspringhw.blacklist.BlackList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

public class BlackListWriter implements CsvWriter<BlackList> {
    private final String file;

    public BlackListWriter(String file) {
        this.file = file;
    }

    @Override
    public void writeCsv(Map<UUID, BlackList> map) throws IOException {
        BufferedWriter bw = null;
        try {
            bw = Files.newBufferedWriter(Path.of(file), Charset.forName("UTF-8"));
            for (UUID uuid : map.keySet()) {
                bw.write(map.get(uuid).getBlackListId().toString());
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
