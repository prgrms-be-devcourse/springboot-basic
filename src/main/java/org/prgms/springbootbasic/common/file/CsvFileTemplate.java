package org.prgms.springbootbasic.common.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@Profile("test")
public class CsvFileTemplate {
    private static final Logger log = LoggerFactory.getLogger(CsvFileTemplate.class);

    public <T> List<T> read(String path, Function<String, T> mapToObj) {
        log.info("read started");

        List<T> ret = new ArrayList<>();
        File file = new File(path);

        if (filePathNotExist(file)){
            return ret;
        }

        log.info("file exists.");

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path), StandardCharsets.UTF_8) ) ){
            String line = br.readLine(); // 한 줄 생략

            while((line = br.readLine()) != null) {
                ret.add(mapToObj.apply(line));
            }
        } catch (IOException e) {
            log.error("Unable to read the file due to an unexpected error.");
            throw new RuntimeException(e);
        }

        return ret;
    }

    public <T> void write(String path, List<T> objs, Function<T, String> objToString, String firstLine) {
        log.debug("write started");

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(path), StandardCharsets.UTF_8) ) ){
            bw.write(firstLine);
            bw.newLine();

            for (T obj : objs) {
                String objToStr = objToString.apply(obj);
                bw.write(objToStr);
            }

        } catch (FileNotFoundException e){
            log.error("File is not found.");
            throw new RuntimeException("File is not found.");
        } catch (IOException e){
            log.error("IOException.");
            throw new RuntimeException("Writing error occurred.");
        }
    }

    private boolean filePathNotExist(File file) {
        try {
            if(file.getParentFile().mkdirs()) {
                return true;
            }

            file.createNewFile();
        } catch (SecurityException e){
            log.error("Security problem occurred.");
            throw e;
        } catch (IOException e){
            log.error("An error occurred during file checking.");
            throw new RuntimeException(e);
        }
        log.info("File directory created.");

        return false;
    }
}
