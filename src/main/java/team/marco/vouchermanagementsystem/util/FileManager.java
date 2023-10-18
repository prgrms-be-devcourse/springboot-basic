package team.marco.vouchermanagementsystem.util;

import java.util.List;

public interface FileManager<T> {
    List<T> load();

    void save(T data);

    void close();
}
