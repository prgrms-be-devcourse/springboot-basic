package team.marco.vouchermanagementsystem.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SequenceWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class JSONFileManager<T> implements FileManager<T> {
    private final File file;
    private final ObjectMapper objectMapper;
    private final ObjectReader objectReader;
    private SequenceWriter sequenceWriter;

    private String path = "./src/main/resources/data.json";

    public JSONFileManager(Class<T> type) {
        file = new File(path);
        objectMapper = new ObjectMapper();
        objectReader = objectMapper.readerForListOf(type);
    }

    @Override
    public List<T> load() {
        if (!file.exists()) {
            return Collections.emptyList();
        }

        try {
            List<T> loadedData = objectReader.readValue(Files.readString(Paths.get(path)));
            FileWriter fileWriter = new FileWriter(file, false);

            sequenceWriter = objectMapper.writer().writeValuesAsArray(fileWriter);

            for (T t : loadedData) {
                sequenceWriter.write(t);
            }

            return loadedData;
        } catch (IOException e) {
            throw new RuntimeException("파일을 불러오는 과정에서 오류가 발생했습니다.");
        }
    }

    @Override
    public void save(T data) {
        try {
            sequenceWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("파일을 생성할 수 없습니다. (%s)".formatted(path));
        }
    }

    @Override
    public void close() {
        try {
            sequenceWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 과정에서 오류가 발생했습니다.");
        }
    }
}
