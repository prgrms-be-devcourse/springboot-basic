package org.prgrms.kdtspringdemo.util.queryBuilder.query;

import org.prgrms.kdtspringdemo.util.JdbcUtils;
import org.prgrms.kdtspringdemo.util.queryBuilder.Entity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.prgrms.kdtspringdemo.util.JdbcUtils.uuidToBin;

public class Update {
    private final Class<? extends Entity> table;
    private final Map<String, Object> values;
    private final Where where;
    private final String query;

    private Update(Class<? extends Entity> table, Map<String, Object> values, Where where) {
        this.table = table;
        this.values = values;
        this.where = where;
        this.query = generateQuery();
    }

    private String generateQuery() {
        String tableName = this.table.getSimpleName().toLowerCase();
        List<String> entrySet = this.values.entrySet().stream()
                .map(entry -> {
                    if (entry.getValue() instanceof UUID uuid) {
                        return entry.getKey() + " = " + uuidToBin(uuid);
                    }

                    return String.format("%s = '%s'", entry.getKey(), entry.getValue().toString());
                })
                .toList();
        String entries = String.join(", ", entrySet);
        String whereQuery = this.where.getQuery();

        return "UPDATE %s SET %s%s".formatted(tableName, entries, whereQuery);
    }

    public String getQuery() {
        return query;
    }

    public static UpdateCriteria builder() {
        return new UpdateCriteria();
    }

    public static class UpdateCriteria {
        private Class<? extends Entity> table;

        private UpdateCriteria() {
        }

        public SetCriteria update(Class<? extends Entity> entity) {
            this.table = entity;

            return new SetCriteria(entity);
        }

    }

    public static class SetCriteria {
        private final Class<? extends Entity> table;
        private Map<String, Object> values;

        private SetCriteria(Class<? extends Entity> table) {
            this.table = table;
        }

        public WhereCriteria set(Map<String, Object> values) {
            this.values = values;
            return new WhereCriteria(table, values);
        }
    }

    public static class WhereCriteria {
        private final Class<? extends Entity> table;
        private final Map<String, Object> values;
        private Where where;

        private WhereCriteria(Class<? extends Entity> table, Map<String, Object> values) {
            this.table = table;
            this.values = values;
        }

        public Builder where(Where where) {
            this.where = where;

            return new Builder(this.table, this.values, this.where);
        }
    }

    public static class Builder {
        private final Class<? extends Entity> table;
        private final Map<String, Object> values;
        private final Where where;

        private Builder(Class<? extends Entity> table, Map<String, Object> values, Where where) {
            this.table = table;
            this.values = values;
            this.where = where;
        }

        public Update build() {
            return new Update(table, values, where);
        }
    }
}
