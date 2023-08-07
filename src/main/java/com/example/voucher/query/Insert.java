package com.example.voucher.query;

import java.util.Map;

import com.example.voucher.query.marker.Entity;

public class Insert {

    private final Class<? extends Entity> table;
    private final Map<String, Object> values;
    private final String query;

    public Insert(Class<? extends Entity> table, Map<String, Object> values) {
        this.table = table;
        this.values = values;
        this.query = makeQuery();
    }

    public String getQuery() {
        return this.query;
    }

    private String makeQuery() {
        String table = this.table.getSimpleName().toUpperCase();

        var columSet = this.values.entrySet()
            .stream()
            .map(Map.Entry::getKey)
            .toList();

        var valueSet = this.values.entrySet().stream()
            .map(Map.Entry::getValue)
            .map(value -> {
                if (value instanceof String) {
                    return String.format("%s", value);
                }

                return value.toString();
            })
            .toList();

        String columns = String.join(", ", columSet);
        String values = String.join(", ", valueSet);

        return "INSERT INTO %s (%s) VALUES (%s)".formatted(table, columns, values);
    }

    public static InsertCriteria into(Class<? extends Entity> table) {
        return new InsertCriteria(table);
    }

    public static class InsertCriteria {
        private final Class<? extends Entity> table;

        private InsertCriteria(Class<? extends Entity> table) {
            this.table = table;
        }

        public Insert values(Map<String, Object> values) {
            return new Insert(table, values);
        }
    }

}

