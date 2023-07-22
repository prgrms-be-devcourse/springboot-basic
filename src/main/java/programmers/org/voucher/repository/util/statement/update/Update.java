package programmers.org.voucher.repository.util.statement.update;

import programmers.org.voucher.repository.util.constant.Table;

public class Update {

    private Table table;

    public Update(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }
}
