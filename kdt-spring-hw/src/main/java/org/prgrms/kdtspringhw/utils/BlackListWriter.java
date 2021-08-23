package org.prgrms.kdtspringhw.utils;

import org.prgrms.kdtspringhw.blacklist.BlackList;
import org.springframework.core.io.Resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

public class BlackListWriter implements CsvWriter {
    @Override
    public void writeCsv(Resource resource, Map<UUID,BlackList> map) throws IOException {
        File file = resource.getFile();
        BufferedWriter bw = null;
        try {
            bw = Files.newBufferedWriter(file.toPath(), Charset.forName("UTF-8"));

            for (UUID uuid : map.keySet()) {
                bw.write(map.get(uuid).getBlackListId().toString());
                bw.newLine();
            }
            bw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
