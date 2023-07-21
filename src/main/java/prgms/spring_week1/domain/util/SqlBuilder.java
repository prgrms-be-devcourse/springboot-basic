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

        public FromBuilder from(String table) {
            return new FromBuilder(selectSqlBuilder.append(" FROM ").append(table));
        }

        public String build(){
            return selectSqlBuilder.toString();
        }
    }

    public static class InsertBuilder {
        StringBuilder insertSqlBuilder = new StringBuilder();

        public InsertBuilder insert(String table) {
            insertSqlBuilder.append("INSET INTO ").append(table);
            return this;
        }

        public ColumnBuilder columns(String... columns) {
            insertSqlBuilder.append("(");

            for (String column : columns) {
                insertSqlBuilder.append(insertSqlBuilder + ",");
            }

            insertSqlBuilder.setLength(insertSqlBuilder.length() - INVALID_LAST_COMMA);
            insertSqlBuilder.append(")");

            return new ColumnBuilder(insertSqlBuilder);
        }

        public String build(){
            return insertSqlBuilder.toString();
        }
    }

    public static class UpdateBuilder {
        StringBuilder updateSqlBuilder = new StringBuilder();

        public UpdateBuilder update(String table) {
            updateSqlBuilder.append("UPDATE ").append(table);
            return this;
        }

        public SetBuilder set(String... column) {
            updateSqlBuilder.append(" SET ");

            for (String columnValue : column) {
                updateSqlBuilder.append(columnValue + ",");
            }

            updateSqlBuilder.setLength(updateSqlBuilder.length() - INVALID_LAST_COMMA);

            return new SetBuilder(updateSqlBuilder);
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

        public WhereBuilder where(String condition) {
            return new WhereBuilder(deleteSqlBuilder.append(" WHERE ").append(condition));
        }

        public String build(){
            return deleteSqlBuilder.toString();
        }
    }

    public static class FromBuilder {
        StringBuilder fromBuilder;

        public FromBuilder(StringBuilder fromBuilder) {
            this.fromBuilder = fromBuilder;
        }

        public WhereBuilder where(String condition) {
            return new WhereBuilder(fromBuilder.append(" WHERE ").append(condition));
        }

        public String build(){
            return fromBuilder.toString();
        }
    }

    public static class ColumnBuilder {
        StringBuilder columnBuilder;

        public ColumnBuilder(StringBuilder columnBuilder) {
            this.columnBuilder = columnBuilder;
        }

        public ValuesBuilder values(String... values) {
            columnBuilder.append(" VALUES (");

            for (String value : values) {
                columnBuilder.append(value);
            }

            columnBuilder.append(")");

            return new ValuesBuilder(columnBuilder);
        }

        public String build(){
            return columnBuilder.toString();
        }
    }

    public static class ValuesBuilder {
        StringBuilder valuesBuilder;

        public ValuesBuilder(StringBuilder valuesBuilder) {
            this.valuesBuilder = valuesBuilder;
        }

        public String build(){
            return valuesBuilder.toString();
        }
    }

    public static class SetBuilder {
        StringBuilder setBuilder;

        public SetBuilder(StringBuilder setBuilder) {
            this.setBuilder = setBuilder;
        }

        public WhereBuilder where(String... condition) {
            setBuilder.append(" WHERE ");

            for (String conditionValue : condition) {
                setBuilder.append(conditionValue + " AND ");
            }

            setBuilder.setLength(setBuilder.length() - INVALID_LAST_AND);
            return new WhereBuilder(setBuilder);
        }

        public String build(){
            return setBuilder.toString();
        }
    }

    public static class WhereBuilder {
        StringBuilder whereBuilder;

        public WhereBuilder(StringBuilder whereBuilder) {
            this.whereBuilder = whereBuilder;
        }

        public String build(){
            return whereBuilder.toString();
        }
    }


}

