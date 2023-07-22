package programmers.org.voucher.repository.util.statement;

import programmers.org.voucher.repository.util.constant.Table;

import static programmers.org.voucher.repository.util.constant.Symbol.BLANK;

public class From extends Statement {
    private static final String FROM = "FROM";

    public From(StringBuilder query) {
        this.query = query;
    }

    static public class Builder {
        private StringBuilder query = new StringBuilder();

        public Builder() {
            query.append(BLANK.getSymbol()).append(FROM);
        }

        public Builder query(Table table) {
            query.append(BLANK.getSymbol()).append(table);
            return this;
        }

        public From build() {
            return new From(query);
        }
    }
}
