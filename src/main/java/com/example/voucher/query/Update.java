package com.example.voucher.query;

import java.util.Map;
import java.util.stream.Collectors;
import com.example.voucher.query.marker.Entity;

public class Update {

    private final int REMOVE_LENGTH =1;
    private Class<? extends Entity> table;
    private final Map<String, Object> values;
    private final Where where;
    private final String query;

    private Update(Class<? extends Entity> table, Map<String, Object> values) {
        this(table, values, null);
    }

    private Update(Class<? extends Entity> table, Map<String, Object> values, Where where) {
        this.table = table;
        this.values = values;
        this.where = where;
        this.query = makeQuery();
    }

    public String getQuery() {
        return query;
    }

    private String makeQuery() {
        String table = this.table.getSimpleName().toUpperCase();
        String values = this.values.entrySet().stream()
            .map(entry -> {
                if (entry.getValue() instanceof String) {
                    return String.format("%s=%s,", entry.getKey(), entry.getValue());
                }

                return String.format("%s=%s,", entry.getKey(), entry.getValue().toString());
            })
            .collect(Collectors.joining(" "));
        values = values.substring(0, values.length() - REMOVE_LENGTH);
        String where = this.where == null ? "" : this.where.getQuery();

        return String.format("UPDATE %s SET %s %s ", table, values, where).trim();
    }

    public static UpdateCriteria builder() {
        return new UpdateCriteria();
    }

    public static class UpdateCriteria {

        private Class<? extends Entity> table;

        public UpdateCriteria() {
        }

        public SetCriteria updateInto(Class<? extends Entity> table) {
            this.table = table;

            return new SetCriteria(this.table);
        }

    }

    public static class SetCriteria {

        private Class<? extends Entity> table;
        private Map<String, Object> values;

        public SetCriteria(Class<? extends Entity> table) {
            this.table = table;
        }

        public WhereCriteria set(Map<String, Object> values) {
            this.values = values;
            return new WhereCriteria(this.table, this.values);
        }

    }

    public static class WhereCriteria {

        private Class<? extends Entity> table;
        private Map<String, Object> values;
        private Where where;

        public WhereCriteria(Class<? extends Entity> table, Map<String, Object> values) {
            this.table = table;
            this.values = values;
        }

        public Update build() {
            return new Update(table, values);
        }

        public Builder where(Where where) {
            this.where = where;

            return new Builder(table, values, where);
        }

    }

    public static class Builder {

        private Class<? extends Entity> table;
        private Map<String, Object> values;
        private Where where;

        public Builder(Class<? extends Entity> table, Map<String, Object> values, Where where) {
            this.table = table;
            this.values = values;
            this.where = where;
        }

        public Update build() {
            return new Update(table, values, where);
        }

    }

}
