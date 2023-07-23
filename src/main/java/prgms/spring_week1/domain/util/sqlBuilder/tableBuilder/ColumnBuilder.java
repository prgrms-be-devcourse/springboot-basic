package prgms.spring_week1.domain.util.sqlBuilder.tableBuilder;

public class ColumnBuilder {
    private StringBuilder columnBuilder;

    public ColumnBuilder(StringBuilder columnBuilder) {
        this.columnBuilder = columnBuilder;
    }

    public ValuesBuilder values(String... values) {
        columnBuilder.append(" VALUES (");

        for (String value : values) {
            columnBuilder.append(value+",");
        }

        columnBuilder.setLength(columnBuilder.length()-1);
        columnBuilder.append(")");

        return new ValuesBuilder(columnBuilder);
    }

    public String build(){
        return columnBuilder.toString();
    }
}
