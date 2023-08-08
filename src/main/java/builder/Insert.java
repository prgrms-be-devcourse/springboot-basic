package builder;

import builder.builderEntity.Entity;
import com.prgms.voucher.voucherproject.util.JdbcUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Insert {
    private final Class<? extends Entity> table;
    private final Map<String, Object> values;
    private final String query;

    public Insert(Class<? extends Entity> table, Map<String, Object> values) {
        this.table = table;
        this.values = values;
        this.query = generateQuery();
    }

    public static InsertCriteria into(Class<? extends Entity> table) {
        return new InsertCriteria(table);
    }

    private String generateQuery() {
        List<String> columnSet = this.values.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .toList();

        List<String> valueSet = this.values.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .map(value -> {
                    if (value instanceof UUID uuid) {
                        return "\'" + JdbcUtils.UUID_TO_BIN(uuid) + "\'";
                    }

                    return String.format("\'%s\'", value);
                })
                .toList();

        String columns = String.join(", ", columnSet);
        String values = String.join(", ", valueSet);
        String tableName = this.table.getSimpleName().toLowerCase();
        return "INSERT INTO %s (%s) VALUES (%s)".formatted(tableName, columns, values);
    }

    public String getQuery() {
        return this.query;
    }

    public static class InsertCriteria {
        private final Class<? extends Entity> table;
        private Map<String, Object> values;

        private InsertCriteria(Class<? extends Entity> table) {
            this.table = table;
        }

        public Builder values(Map<String, Object> values) {
            this.values = values;
            return new Builder(this.table, this.values);
        }
    }

    public static class Builder {
        private final Class<? extends Entity> table;
        private final Map<String, Object> values;

        private Builder(Class<? extends Entity> table, Map<String, Object> values) {
            this.table = table;
            this.values = values;
        }

        public Insert build() {
            return new Insert(this.table, this.values);
        }
    }
}
