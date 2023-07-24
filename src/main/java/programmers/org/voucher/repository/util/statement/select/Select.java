package programmers.org.voucher.repository.util.statement.select;

import java.util.ArrayList;
import java.util.List;

public class Select {

    private final List<String> columns = new ArrayList<>();

    public Select(String column) {
        columns.add(column);
    }

    public List<String> getColumns() {
        return columns;
    }

    public Select query(String column) {
        this.columns.add(column);
        return this;
    }
}
