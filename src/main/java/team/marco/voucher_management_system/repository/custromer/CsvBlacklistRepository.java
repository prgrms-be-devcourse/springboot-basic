package team.marco.voucher_management_system.repository.custromer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.controller.customer.dto.CustomerIdAndName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static team.marco.voucher_management_system.util.UUIDUtil.stringToUUID;

@Repository
public class CsvBlacklistRepository implements BlacklistRepository {
    private static final String DELIMITER_REGULAR_EXPRESSION = "[;,]";
    private final List<CustomerIdAndName> blacklist;

    public CsvBlacklistRepository(@Value("${file.path.blacklist}") String path) {
        blacklist = loadBlacklist(path);
    }

    private List<CustomerIdAndName> loadBlacklist(String path) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            reader.readLine(); // skip header
            return storedDataToCustomerIdAndName(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public List<CustomerIdAndName> findAll() {
        return Collections.unmodifiableList(blacklist);
    }

    private static List<CustomerIdAndName> storedDataToCustomerIdAndName(BufferedReader reader) {
        return reader.lines()
                .map(s -> s.split(DELIMITER_REGULAR_EXPRESSION))
                .map(data -> new CustomerIdAndName(stringToUUID(data[0]), data[1]))
                .toList();
    }
}
