package programmers.org.voucher.repository.util.statement;

import programmers.org.voucher.repository.util.constant.Table;

import static programmers.org.voucher.repository.util.constant.Symbol.BLANK;

public class Update extends Statement {
    private static final String UPDATE = "UPDATE";

    public Update(StringBuilder query) {
        this.query = query;
    }

    static public class Builder {
        private StringBuilder query = new StringBuilder();

        public Builder() {
            query.append(UPDATE);
        }

        public Builder query(Table table) {
            query.append(BLANK.getSymbol())
                    .append(table);

            return this;
        }

        public Update build() {
            return new Update(query);
        }
    }
}
