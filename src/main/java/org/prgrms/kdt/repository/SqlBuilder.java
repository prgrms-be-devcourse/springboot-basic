package org.prgrms.kdt.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SqlBuilder {

    private String sql;
    private Map<String, Object> params;

    public SqlBuilder(String sql, Map<String, Object> params) {
        this.sql = sql;
        this.params = params;
    }

    public static Builder builder() {
        return new SqlBuilder.Builder();
    }

    public String getSql() {
        return sql;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public static class Builder {

        private StringBuilder sql = new StringBuilder();
        private Map<String, Object> params = new HashMap<>();

        public Builder() {
        }

        public Builder SELECT(String field) {
            sql.append("SELECT " + field);
            return this;
        }

        public Builder SELECT() {
            this.SELECT("*");
            return this;
        }

        public Builder FROM(String table) {
            sql.append(" FROM " + table);
            return this;
        }

        public Builder WHERE(String condition, String key, Object value) {
            if (value == null) {
                return this;
            }

            if (value instanceof Optional<?>) {
                ((Optional) value).ifPresent(val -> appendWhere(condition, key, val));
                return this;
            }

            appendWhere(condition, key, value);
            return this;
        }

        private void appendWhere(String condition, String key, Object value) {
            String prefix = " WHERE ";
            if (sql.indexOf("WHERE") >= 0) {
                prefix = " AND ";
            }
            sql.append(prefix + condition);
            params.put(key, value);
        }

        public SqlBuilder build() {
            return new SqlBuilder(sql.toString(), params);
        }
    }
}