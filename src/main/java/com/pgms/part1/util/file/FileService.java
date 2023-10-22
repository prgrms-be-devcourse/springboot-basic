package com.pgms.part1.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final Logger log = LoggerFactory.getLogger(FileService.class);

    public List<String[]> loadFile(String file_path) {
        List<String[]> infoList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                infoList.add(data);
            }
            log.info("file loaded");
        } catch (IOException e) {
            log.error("load file error");
            throw new RuntimeException("can not load file!!");
        }
        return infoList;
    }

    public void createFile(File file) {
        try {
            file.createNewFile();
            log.info("new file created");
        } catch (IOException e) {
            log.error("create file error");
            throw new RuntimeException("can not create file!!");
        }
    }

    public void saveFile(List<String> dataList, String file_path){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file_path))) {
            for (String data : dataList) {
                bw.write(data);
            }
            log.info("file saved");
        }
        catch (IOException e){
            log.error("save file error");
            throw new RuntimeException("can not save file!!");
        }
    }
}
