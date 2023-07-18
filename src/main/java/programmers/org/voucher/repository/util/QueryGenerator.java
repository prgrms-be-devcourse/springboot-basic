package programmers.org.voucher.repository.util;

public class QueryGenerator {

    private static final String SELECT = "SELECT";
    private static final String FROM = "FROM";
    private static final String WHERE = "WHERE";
    private static final String INSERT_INTO = "INSERT INTO";
    private static final String UPDATE = "UPDATE";
    private static final String SET = "SET";
    private static final String DELETE_FROM = "DELETE FROM";
    private static final String VALUES = "VALUES";

    private static final String BLANK = " ";
    private static final String EQUAL = "=";
    private static final String QUESTION_MARK = "?";
    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";
    private static final String COMMA = ",";

    static public class Builder {
        private StringBuilder stringBuilder = new StringBuilder();

        public Builder() {
        }

        public Builder select(String column) {
            stringBuilder.append(SELECT)
                    .append(BLANK)
                    .append(column)
                    .append(BLANK);

            return this;
        }

        public Builder from(String table) {
            stringBuilder.append(FROM)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public Builder where(String column) {
            stringBuilder.append(BLANK)
                    .append(WHERE)
                    .append(BLANK)
                    .append(column)
                    .append(EQUAL)
                    .append(QUESTION_MARK);

            return this;
        }

        public Builder insertInto(String table) {
            stringBuilder.append(INSERT_INTO)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public Builder values(String... args) {
            StringBuilder questionMarks = new StringBuilder();
            stringBuilder.append(LEFT_PARENTHESIS);
            questionMarks.append(BLANK)
                    .append(VALUES)
                    .append(LEFT_PARENTHESIS);

            for (String str : args) {
                stringBuilder.append(str).append(COMMA);
                questionMarks.append(QUESTION_MARK).append(COMMA);
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(RIGHT_PARENTHESIS);

            questionMarks.deleteCharAt(questionMarks.length() - 1);

            stringBuilder.append(questionMarks);
            stringBuilder.append(RIGHT_PARENTHESIS);

            return this;
        }

        public Builder update(String table) {
            stringBuilder.append(UPDATE)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public Builder set(String... args) {
            stringBuilder.append(BLANK)
                    .append(SET);

            for (String str : args) {
                stringBuilder.append(BLANK)
                        .append(str)
                        .append(EQUAL)
                        .append(QUESTION_MARK)
                        .append(COMMA);
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            return this;
        }

        public Builder deleteFrom(String table) {
            stringBuilder.append(DELETE_FROM)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public String build() {
            return stringBuilder.toString();
        }
    }
}
