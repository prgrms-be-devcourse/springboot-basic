package builder;

public class Sort {
    private final String column;
    private final SortType sortRule;
    private final String query;

    public Sort(String column, SortType sortRule) {
        this.column = column;
        this.sortRule = sortRule;
        this.query = generateQuery();
    }

    public static Sort asc(String column) {
        return new Sort(column, SortType.ASC);
    }

    public static Sort desc(String column) {
        return new Sort(column, SortType.DESC);
    }

    private String generateQuery() {
        return "%s %s".formatted(this.column, this.sortRule.name());
    }

    public String getQuery() {
        return this.query;
    }

    public enum SortType {
        ASC,
        DESC;
    }
}
