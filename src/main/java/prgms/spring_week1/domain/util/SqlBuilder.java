package prgms.spring_week1.domain.util;

public class SqlBuilder {
    private static int INVALID_LAST_COMMA = 1;
    private static int INVALID_LAST_AND = 5;

    public static class SelectBuilder {
        StringBuilder selectSqlBuilder = new StringBuilder();

        public SelectBuilder select(String... column) {
            selectSqlBuilder.append("SELECT ");

            for (String columnValue : column) {
                selectSqlBuilder.append(columnValue + ",");
            }

            selectSqlBuilder.setLength(selectSqlBuilder.length() - INVALID_LAST_COMMA);

            return this;
        }

        public SelectBuilder from(String table) {
            selectSqlBuilder.append(" FROM ").append(table);
            return this;
        }

        public SelectBuilder where(String where) {
            selectSqlBuilder.append(" WHERE ").append(where);
            return this;
        }

        public String build() {
            return selectSqlBuilder.toString();
        }
    }

    public static class InsertBuilder {
        StringBuilder insertSqlBuilder = new StringBuilder();

        public InsertBuilder insert(String table) {
            insertSqlBuilder.append("INSET INTO ").append(table);
            return this;
        }

        public InsertBuilder insertColumns(String... columns) {
            insertSqlBuilder.append("(");

            for (String column : columns) {
                insertSqlBuilder.append(insertSqlBuilder + ",");
            }

            insertSqlBuilder.setLength(insertSqlBuilder.length() - INVALID_LAST_COMMA);
            insertSqlBuilder.append(")");

            return this;
        }

        public InsertBuilder values(String... values) {
            insertSqlBuilder.append(" VALUES (");

            for (String value : values) {
                insertSqlBuilder.append(value);
            }

            insertSqlBuilder.append(")");

            return this;
        }

        public String build() {
            return insertSqlBuilder.toString();
        }
    }

    public static class UpdateBuilder {
        StringBuilder updateSqlBuilder = new StringBuilder();

        public UpdateBuilder update(String table) {
            updateSqlBuilder.append("UPDATE ").append(table);
            return this;
        }

        public UpdateBuilder set(String... column) {
            updateSqlBuilder.append(" SET ");

            for (String columnValue : column) {
                updateSqlBuilder.append(columnValue + ",");
            }

            updateSqlBuilder.setLength(updateSqlBuilder.length() - INVALID_LAST_COMMA);

            return this;
        }

        public UpdateBuilder where(String... condition) {
            updateSqlBuilder.append(" WHERE ");

            for (String conditionValue : condition) {
                updateSqlBuilder.append(conditionValue + " AND ");
            }

            updateSqlBuilder.setLength(updateSqlBuilder.length() - INVALID_LAST_AND);
            return this;
        }

        public String build() {
            return updateSqlBuilder.toString();
        }
    }

    public static class DeleteBuilder {
        StringBuilder deleteSqlBuilder = new StringBuilder();

        public DeleteBuilder delete(String delete) {
            deleteSqlBuilder.append("DELETE FROM ").append(delete);
            return this;
        }

        public DeleteBuilder where(String where) {
            deleteSqlBuilder.append(" WHERE ").append(where);
            return this;
        }

        public String build() {
            return deleteSqlBuilder.toString();
        }
    }
}

