package programmers.org.voucher.repository.util.statement.insert;

import static programmers.org.voucher.repository.util.constant.Symbol.*;

public class Values {

    private static final String VALUES = "VALUES";

    private StringBuilder query;

    public Values(StringBuilder query) {
        this.query = query;
    }

    public StringBuilder getQuery() {
        return query;
    }

    static public class Builder {
        private StringBuilder query = new StringBuilder();
        private StringBuilder questionMarks = new StringBuilder();

        public Builder() {
            query.append(LEFT_PARENTHESIS.getSymbol());

            questionMarks.append(BLANK.getSymbol())
                    .append(VALUES)
                    .append(BLANK.getSymbol())
                    .append(LEFT_PARENTHESIS.getSymbol());
        }

        public Builder query(String column) {
            query.append(column)
                    .append(COMMA.getSymbol());

            questionMarks.append(QUESTION_MARK.getSymbol())
                    .append(COMMA.getSymbol());

            return this;
        }

        public Values build() {
            query.deleteCharAt(query.length() - 1).append(RIGHT_PARENTHESIS.getSymbol());
            questionMarks.deleteCharAt(questionMarks.length() - 1).append(RIGHT_PARENTHESIS.getSymbol());

            query.append(questionMarks);

            return new Values(query);
        }
    }
}
